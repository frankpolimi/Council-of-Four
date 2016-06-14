package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.*;
import model.game.*;
import view.ClientView;
import view.LocalStorage;

public class ClientSocket 
{
	private final int port;
	private final String ip;
	private Game game;
	private LocalStorage memoryContainer;
	private int ID;
	
	
	public ClientSocket(String ip, int port) {
		super();
		this.ip = ip;
		this.port = port;
		this.memoryContainer=new LocalStorage();
	}

	/**
	 * @return the iD
	 */
	public int getID() {
		return ID;
	}

	/**
	 * @param iD the iD to set
	 */
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * @throws IOException
	 */
	public void runClient(String name) throws IOException 
	{
		Socket socket = new Socket(ip, port);
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		SocketConnectionHandler handler = new SocketConnectionHandler(socket);
		
		//ObjectOutputStream socketOut=new ObjectOutputStream(socket.getOutputStream());
		try {
			handler.sendToServer(name);
		} catch (IOException e1) {
			throw new IOException("You didn't insert your name in time. You are disconnected");
		}
		
		//ObjectInputStream socketIn=new ObjectInputStream(socket.getInputStream());
		try{
			Game game=(Game)handler.receiveFromServer();
			this.game=game;
			int id=(Integer)handler.receiveFromServer();
			this.ID=id;
			//System.out.println("gioco "+game);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		
		memoryContainer.setGameRef(game);
		ClientView view=new ClientView(game, memoryContainer, this.ID);
		executor.submit(new ClientOutHandlerSocket(handler, 
				 memoryContainer, view));
		executor.submit(new ClientInHandlerSocket(handler, memoryContainer, view));
		
	}
	
	public void startClient() throws IOException{
		Scanner stdin = new Scanner(System.in);
		System.out.println("Insert your name: ");
		String name = stdin.nextLine();
		try{
		this.runClient(name);
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
		System.out.println("Connection Established");
		System.out.println("Aspetto gioco");
		System.out.println("Waiting for other players");
		System.out.println("ID: "+this.ID);
	}
}
