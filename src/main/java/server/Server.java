package server;

import java.io.File;
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
import org.jdom2.input.SAXBuilder;

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
	
	/*
	 * the name will be different in multi-game.
	 * pubsub RMI will be used with following structure
	 * topic: game1, game2, ..., gameN
	 * sub-topic for each game: ClientRMIView1, ..., ClientRMIViewM
	 */
	private static final String NAME = "game";
	private final Registry registry;
	
	private List<Player> oneRoomLobby;
	private Map<Player, View> playersView;
	
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
	
	public void start() throws AlreadyBoundException, IOException, 
		ClassNotFoundException, JDOMException, AccessException
	{
		this.startRMI();
		this.startSocket();
	}
	
	private void startRMI() throws AccessException, RemoteException, AlreadyBoundException{
		System.out.println("Constructing RMI server");
		ServerRMIRegistrationRemote game = new ServerRMIRegistration(this);
		
		ServerRMIRegistrationRemote gameRemote = 
				(ServerRMIRegistrationRemote) UnicastRemoteObject
				.exportObject(game, 0);
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
					System.out.println("CONNECTION ACCEPTED "+serialID+" "+view.getName());
					view.getSocketOut().reset();
					view.getSocketOut().writeUnshared(game);
					view.getSocketOut().flush();
					this.addSocketClient(view, new Player(view.getName(), serialID));
					serialID++;
					executor.submit(view);
				}catch(IOException e){
					System.out.println("Client has been disconnected");
				}
			} catch (IOException e) {
				break;
			}
		}
	}
	
	public synchronized void addRMIClient(ServerRMIView view) throws JDOMException, IOException{
		view.setID(serialID);
		Player p = new Player(view.getClient().getName(), serialID);
		oneRoomLobby.add(p);
		playersView.put(p, view);
		view.registerObserver(controller);
		game.registerObserver(view);
		System.out.println("CONNECTION ACCEPTED "+serialID+" "+view.getClient().getName());
		view.getClient().printChange(new ModelChange(game));
		serialID++;
		if(oneRoomLobby.size()>=2){
			if(timer==null){
				timer=new Timer();
				System.out.println("START TIMER");
				timer.schedule(new StartingGameTimerTask(oneRoomLobby, playersView, game, controller, timer) ,20*1000);
				if(oneRoomLobby.isEmpty()) 
					serialID=1;
			}
		}	
	}
	
	public synchronized void addSocketClient(ServerSocketView view, Player player) throws JDOMException, IOException
	{
		view.setID(this.serialID);
		view.registerObserver(controller);
		game.registerObserver(view);
		oneRoomLobby.add(player);
		playersView.put(player, view);
		if(oneRoomLobby.size()>=2){
			if(timer==null){
				timer=new Timer();
				System.out.println("START TIMER");
				timer.schedule(new StartingGameTimerTask(oneRoomLobby, playersView, game, controller, timer) ,20*1000);
				if(oneRoomLobby.isEmpty()) 
					serialID=1;
			}
		}	
	}
	
	public List<Player> getLobby(){
		return this.oneRoomLobby;
	}
	
	public static void main(String[] args) throws JDOMException, IOException {
		
		SAXBuilder builder=new SAXBuilder();
		Document document= builder.build(new File("src/main/resources/configIP_PORT.xml"));
		Element root = document.getRootElement();
		
		String ip = ((String)root.getChild("IP").getAttributeValue("value"));
		int socket = Integer.parseInt(root.getChild("PORT").getChild("SOCKETPORT")
				.getAttributeValue("value"));
		int rmi = Integer.parseInt(root.getChild("PORT").getChild("RMIPORT")
				.getAttributeValue("value"));
		
		Server server = new Server(socket, rmi);

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

}
