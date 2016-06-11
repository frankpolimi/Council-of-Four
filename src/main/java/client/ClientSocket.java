package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.*;
import model.game.*;
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
	public void startClient() throws IOException 
	{
		Socket socket = new Socket(ip, port);
		System.out.println("Connection Established");
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		System.out.println("Aspetto gioco");
		ObjectOutputStream socketOut=new ObjectOutputStream(socket.getOutputStream());
		
		Scanner stdin = new Scanner(System.in);
		System.out.println("Insert your name: ");
		String name = stdin.nextLine();
		try {
			socketOut.reset();
			socketOut.writeUnshared(name);
			socketOut.flush();
		} catch (IOException e1) {
			System.err.println("You didn't insert your name in time. You are disconnected");
			System.exit(0);
		}
		System.out.println("Waiting for other players");
		ObjectInputStream socketIn=new ObjectInputStream(socket.getInputStream());
		try{
			Game game=(Game)socketIn.readUnshared();
			this.game=game;
			int id=(Integer)socketIn.readUnshared();
			this.ID=id;
			//System.out.println("gioco "+game);
		}catch(ClassNotFoundException e){
			e.printStackTrace();
		}
		System.out.println("ID: "+this.ID);
		memoryContainer.setGameRef(game);
		
		executor.submit(new ClientOutHandlerSocket(socketOut, 
				 memoryContainer, ID));
		executor.submit(new ClientInHandlerSocket(socketIn, memoryContainer, ID));
		
	}
}
