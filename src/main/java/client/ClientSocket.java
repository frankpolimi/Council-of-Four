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
	private Game game;
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


	/**
	 * @throws IOException
	 */
	public void startClient() throws IOException 
	{
		Socket socket = new Socket(IP, PORT);
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
			e1.printStackTrace();
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
	
	public static void main(String[] args) throws UnknownHostException, IOException{
		ClientSocket client = new ClientSocket();
		client.startClient();
	}
}
