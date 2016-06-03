package server;

import java.io.IOException;
import java.net.*;
import java.rmi.AlreadyBoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.*;

import org.jdom2.JDOMException;

import controller.*;
import model.game.Game;
import model.game.Player;
import view.View;

public class Server 
{
	private final static int PORT=50000;
	private Timer timer;
	private int serialID = 1;
	private Controller controller;
	private Game game;
	private List<Player> oneRoomLobby;
	private Map<Player, View> playersView;
	
	public Server() throws JDOMException, IOException
	{
		game=new Game();
		controller=new Controller(game);
		oneRoomLobby = new ArrayList<>();
		playersView=new HashMap<>();
	}
	
	public void start() throws AlreadyBoundException, IOException
	{
	}
	
	private void startSocket() throws IOException, JDOMException, ClassNotFoundException {
	
		ExecutorService executor = Executors.newCachedThreadPool();
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server socket ready on port: " + PORT);
		while (true) {
			try {
				Socket socket = serverSocket.accept();
				ServerSocketView view = new ServerSocketView(socket);
				System.out.println("CONNECTION ACCEPTED "+serialID+" "+view.getName());
				view.getSocketOut().reset();
				view.getSocketOut().writeUnshared(game);
				view.getSocketOut().flush();
				this.addClient(view, new Player(view.getName(), serialID));
				serialID++;
				executor.submit(view);
			} catch (IOException e) {
				break;
			}
		}
	}
	
	public void addClient(ServerSocketView view, Player player) throws JDOMException, IOException
	{
		view.registerObserver(controller);
		game.registerObserver(view);
		view.setID(this.serialID);
		oneRoomLobby.add(player);
		playersView.put(player, view);
		if(oneRoomLobby.size()>=2){
			if(timer==null){
				timer=new Timer();
				System.out.println("START TIMER");
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(oneRoomLobby.size()>=2){
							int i=0;
							while(i<oneRoomLobby.size()){
								Player player=oneRoomLobby.get(i);
								try{
									((ServerSocketView)playersView.get(player)).getSocketOut().writeObject("");
									i++;
								}catch(SocketException e){
									oneRoomLobby.remove(player);
									playersView.remove(player);
									
								}catch(IOException io){
								}	
							}
							
							if(oneRoomLobby.size()>=2){
								game.setPlayers(oneRoomLobby);
								System.out.println("Stato del gioco: "+game.getGameState());
								game.getPlayers().stream().forEach(System.out::println);
								System.out.println("NOTIFICA");
								System.out.println("new game");
								try {
									serialID=1;
									game= new Game();
									controller= new Controller(game);
									oneRoomLobby.clear();
									playersView.clear();
								} catch (JDOMException | IOException e) {
									e.printStackTrace();
								}
							}
							timer.cancel();
							timer=null;
						}
					}
				}, 20*1000);
			}
		}	
	}
	
	public List<Player> getLobby(){
		return this.oneRoomLobby;
	}
	
	public static void main(String[] args) throws JDOMException, IOException {
		
			Server server = new Server();
			//Timer timer;
			try {
				server.start();
				server.startSocket();
			} catch (IOException e) {
				e.printStackTrace();
			}
			catch (AlreadyBoundException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			
	}
		
}
