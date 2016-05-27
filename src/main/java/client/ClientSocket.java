package client;

import java.io.*;
import java.net.*;
import java.util.Scanner;
import java.util.concurrent.*;
import model.game.*;

public class ClientSocket 
{
	private final static int PORT = 50000;
	private final static String IP="127.0.0.1";
	private final Game game;
	private Object memoryContainer; // vedi in handler
	
	
	
	public ClientSocket(Game game) {
		super();
		this.game = game;
		this.memoryContainer=new Object();
	}



	public void startClient() throws IOException 
	{
		Socket socket = new Socket(IP, PORT);
		System.out.println("Connection Established");
		ExecutorService executor = Executors.newFixedThreadPool(2);
		executor.submit(new ClientInHandlerSocket(new Scanner(socket.getInputStream()), memoryContainer));
		executor.submit(new ClientOutHandlerSocket(new PrintWriter(socket.getOutputStream()),memoryContainer));
	}
}
