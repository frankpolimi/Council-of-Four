package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.rmi.AlreadyBoundException;
import java.util.ArrayList;
import java.util.List;
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
	private int serialID = 1;
	private Controller controller;
	private Game game;
	private List<Player> oneRoomLobby;
	private List<View> serverViewsOfPlayers;
	
	public Server()
	{
		oneRoomLobby = new ArrayList<>();
		serverViewsOfPlayers = new ArrayList<>();
	}
	
	public void start() throws AlreadyBoundException, IOException
	{
		/**
		 * NON VA BENE!! game non dovrebbe prendere giocatori
		 * quando poi parte la partita si settano e allora si fa l'init
		 * del gioco per 2 o più giocatori
		 */
		try {
			this.game = new Game(oneRoomLobby);
			this.controller = new Controller(game);
			for(View v : serverViewsOfPlayers)
				v.registerObserver(controller);
		} catch (JDOMException e) {
			e.printStackTrace();
		}
		System.out.println("PRONTI");
	}
	
	private void startSocket() throws IOException {
	
		ExecutorService executor = Executors.newCachedThreadPool();
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server socket ready on port: " + PORT);
		while (true) {
			try {
				
				Socket socket = serverSocket.accept();
				ServerSocketView view = new ServerSocketView(socket);
				ObjectOutputStream x = new ObjectOutputStream(socket.getOutputStream());
				ObjectInputStream y = new ObjectInputStream(socket.getInputStream());
				x.writeObject("Inserisci il tuo nome");
				x.flush();
				
				try {
					String name = (String)y.readObject();
					this.addClient(view, name);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
				x.close();
				y.close();
				executor.submit(view);
			} catch (IOException e) {
				break;
			}
		}
		executor.shutdown();
		serverSocket.close();
	}
	
	public void addClient(View view, String name)
	{
		view.setID(this.serialID);
		try {
			oneRoomLobby.add(new Player(name, serialID));
			serverViewsOfPlayers.add(view);
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		this.serialID++;
	}
	
	public List<Player> getLobby(){
		return this.oneRoomLobby;
	}
	
	public static void main(String[] args) {
		
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
			}
			/*
			if(server.oneRoomLobby.size() == 2){
				timer = new Timer();
				timer.schedule(new TimerTask() {
					
					@Override
					public void run() {
						try {
							server.start();
						} catch (AlreadyBoundException | IOException e) {
							e.printStackTrace();
						}
					}
				}, 60*1000);//60 seconds * 1000 milliseconds
			}
			*/ 
	}
		
}
