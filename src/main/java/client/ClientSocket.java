package client;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import model.game.*;
import view.LocalStorage;

public class ClientSocket 
{
	private final static int PORT = 50000;
	private final static String IP="127.0.0.1";
	protected final Game game;
	private LocalStorage memoryContainer;
	
	
	public ClientSocket(Game game) {
		super();
		this.game = game;
		this.memoryContainer=new LocalStorage();
	}



	public void startClient() throws IOException 
	{
		Socket socket = new Socket(IP, PORT);
		System.out.println("Connection Established");
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.submit(new ClientInHandlerSocket(new ObjectInputStream(socket.getInputStream()), memoryContainer));
		executor.submit(new ClientOutHandlerSocket(new ObjectOutputStream(socket.getOutputStream()),memoryContainer));
		try {
			executor.wait();
		} catch (InterruptedException e) {
			executor.shutdown();
			e.printStackTrace();
		}
	}
}
