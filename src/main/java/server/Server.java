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
	private Map<Integer, View> playersView;
	
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
		if(oneRoomLobby.size()>=2){
			if(timer==null){
				timer=new Timer();
				System.out.println("START TIMER");
				timer.schedule(new TimerTask() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						if(oneRoomLobby.size()>=2){
							for(int i=0;i<playersView.keySet().size();i++){
								try{
									((ServerSocketView)playersView.get(i)).getSocket().getOutputStream().write(10);;
								}catch(SocketException e){
									for(Player p:oneRoomLobby){
										if(p.getPlayerID()==i){
											oneRoomLobby.remove(p);
										}
									}
									playersView.remove(i);
								}catch(IOException io){
								}	
							}
							
							if(oneRoomLobby.size()>=2){
								game.setPlayers(oneRoomLobby);
								System.out.println("new game");
								try {
									game= new Game();
									controller= new Controller(game);
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
