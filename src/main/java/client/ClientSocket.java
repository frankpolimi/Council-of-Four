package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.*;
import model.game.*;
import view.LocalStorage;

public class ClientSocket 
{
	private final static int PORT = 50000;
	private final static String IP="127.0.0.1";
	protected Game game;
	private LocalStorage memoryContainer;
	private int ID;
	
	
	public ClientSocket() {
		super();
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


	public void startClient() throws IOException 
	{
		Socket socket = new Socket(IP, PORT);
		System.out.println("Connection Established");
		ExecutorService executor = Executors.newFixedThreadPool(2);
		
		executor.submit(new ClientOutHandlerSocket(new ObjectOutputStream(socket.getOutputStream()), 
				game, memoryContainer, ID));
		executor.submit(new ClientInHandlerSocket(new ObjectInputStream(socket.getInputStream()),
				game, memoryContainer, ID));
		
	}
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		ClientSocket client = new ClientSocket();
		client.startClient();
	}
}
