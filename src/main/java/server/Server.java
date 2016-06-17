package server;

import java.io.IOException;
import java.net.*;
import java.rmi.AccessException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

import org.jdom2.*;

import client.ConfigReader;
import client.SocketConnectionHandler;
import controller.*;
import model.game.Game;
import model.game.Player;
import view.View;

public class Server 
{
	private final int SOCKETPORT;
	private final int RMIPORT;
	
	private Timer timer;
	private int serialID = 1;
	private Controller controller;
	private Game game;
	

	private static final String NAME = "game";
	/*
	 * Registry must be static in a single server scenario
	 * because the garbage collector, sometimes collects it
	 * and may cause an IOException when the client try to 
	 * lookup to the server to register to a game.
	 */
	private static Registry registry;
	
	private ServerRMIRegistrationRemote gameRegister;
	private ServerRMIRegistrationRemote gameRemote;
	
	private List<Player> oneRoomLobby;
	private Map<Player, View> playersView;
	
	/**
	 * constructor for a server with both connections: RMI & socket
	 * @param socketPort the port on which the socket will listen for connections
	 * @param RMIPort the port on which the server will retrieve the remote methods of a client
	 * @throws JDOMException when file reading fails
	 * @throws IOException when writing to client fails
	 */
	public Server(int socketPort, int RMIPort) throws JDOMException, IOException
	{
		this.SOCKETPORT = socketPort;
		this.RMIPORT = RMIPort;
		
		game=new Game();
		controller=new Controller(game);
		registry = LocateRegistry.createRegistry(RMIPORT);
		
		oneRoomLobby = new ArrayList<>();
		playersView=new HashMap<>();
	}
	
	/**
	 * method to start both rmi & socket servers
	 * @throws AlreadyBoundException if servers already exists
	 * @throws IOException if connections fails
	 * @throws ClassNotFoundException
	 * @throws JDOMException
	 * @throws AccessException
	 */
	public void start() throws AlreadyBoundException, IOException, 
		ClassNotFoundException, JDOMException, AccessException
	{
		this.startRMI();
		this.startSocket();
	}
	
	private void startRMI() throws AccessException, RemoteException, AlreadyBoundException{
		System.out.println("Constructing RMI server");
		
		gameRegister = new ServerRMIRegistration(this);
		
		gameRemote = (ServerRMIRegistrationRemote) UnicastRemoteObject.exportObject(gameRegister, 0);
		registry.bind(NAME, gameRemote);
	}
	
	private void startSocket() throws IOException, JDOMException, ClassNotFoundException {
	
		ExecutorService executor = Executors.newCachedThreadPool();
		ServerSocket serverSocket = new ServerSocket(SOCKETPORT);
		System.out.println("Server socket ready on port: " + SOCKETPORT);
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				try{
					ServerSocketView view = new ServerSocketView(socket);
					SocketConnectionHandler handler=view.getHandler();
					handler.sendToClient(game);
					this.addClient(view);
					executor.submit(view);
				}catch(IOException e){
					System.out.println("Client has been disconnected");
				}
			} catch (IOException e) {
				break;
			}
		}
	}

	public synchronized void addClient(View view) throws JDOMException, IOException{
		view.setID(serialID);
		Player p = new Player(view.getName(), serialID);
		oneRoomLobby.add(p);
		playersView.put(p, view);
		view.registerObserver(controller);
		game.registerObserver(view);
		System.out.println("CONNECTION ACCEPTED "+serialID+" "+view.getName());
		serialID++;
		this.startTimer();
	}
		
	public List<Player> getLobby(){
		return this.oneRoomLobby;
	}
	
	public static void main(String[] args) throws JDOMException, IOException {
		
		ConfigReader c = new ConfigReader();
		
		Server server = new Server(c.getSocketPort(), c.getRMIPort());

		try {
			server.start();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (AlreadyBoundException e) {
			e.printStackTrace();
		}
	}
	
	public void resetGame() throws JDOMException, IOException{
		game.setPlayers(oneRoomLobby);
		game.getPlayers().stream().forEach(System.out::println);
		this.game=new Game();
		this.controller=new Controller(game);
		this.serialID=1;
	}
	
	public void resetTimer(){
		this.timer.cancel();
		this.timer=null;
	}
	
	private void startTimer(){
		if(oneRoomLobby.size()>=2){
			if(timer==null){
				timer=new Timer();
				System.out.println("START TIMER");
				timer.schedule(new StartingGameTimerTask(this,oneRoomLobby, playersView),20*1000);
			}
		}	
	}

	/**
	 * @return the game
	 */
	public Game getGame() {
		return game;
	}
	
}



