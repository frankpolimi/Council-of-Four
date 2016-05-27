package server;

import java.io.IOException;
import java.net.*;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.concurrent.*;

import controller.*;
import model.game.*;

public class Server 
{
	private final static String ADDRESS="127.0.0.1";
	private final static int PORT=50000;
	private Controller controller;
	private Game game;
	
	public Server() throws RemoteException
	{
		//game=new Game(); qua dobbiamo costruire la partita in qualche modo (file)
		//controller=new Controller(viewSocket, viewRMI, game);
	}
	
	public void start() throws AlreadyBoundException, IOException
	{
		this.startSocket();
	}
	
	private void startSocket() throws IOException {
	
		ExecutorService executor = Executors.newCachedThreadPool();
		// creates a new Server socket on the specified port
		ServerSocket serverSocket = new ServerSocket(PORT);
		System.out.println("Server socket ready on port: " + PORT);
		System.out.println("Server ready");
		while (true) {
			try {

				Socket socket = serverSocket.accept();
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
	
}
