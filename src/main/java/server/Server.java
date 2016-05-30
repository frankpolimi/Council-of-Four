package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
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
	
	public Server() throws RemoteException
	{
		oneRoomLobby = new ArrayList<>();
		
		//game=new Game(); qua dobbiamo costruire la partita in qualche modo (file)
		//controller=new Controller(viewSocket, viewRMI, game);
	}
	
	public void start() throws AlreadyBoundException, IOException
	{
		this.startSocket();
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
				//ServerSocketView view=new ServerSocketView(socket);
				//this.addClient(view);
				//executor.submit(view);
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
		} catch (JDOMException | IOException e) {
			e.printStackTrace();
		}
		this.serialID++;
	}
	
	
		
	
}
