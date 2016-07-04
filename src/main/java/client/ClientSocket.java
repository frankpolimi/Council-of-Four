package client;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;
import java.util.logging.Level;

import model.game.*;
import view.LocalStorage;

public class ClientSocket implements ClientInterface
{
	private final int port;
	private final String ip;
	private Game game;
	private LocalStorage memoryContainer;
	private int ID;
	private ClientViewInterface clientView;
	private ConnectionHandler handler;
	
	
	public ClientSocket(String ip, int port, ClientViewInterface view) {
		super();
		this.ip = ip;
		this.port = port;
		this.memoryContainer=new LocalStorage();
		this.clientView=view;
		
	}
	
	@Override
	public ClientViewInterface getClientView() {
		return this.clientView;
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
	@Override
	public void runClient(String name) throws IOException 
	{
		Socket socket = new Socket(ip, port);
		
		ExecutorService executor = Executors.newFixedThreadPool(2);
		this.handler = new SocketConnectionHandler(socket);
		
		try {
			handler.sendToServer(name);
		} catch (IOException e1) {
			throw new IOException("You didn't insert your name in time. You are disconnected");
		}
		
		try{
			Game game=(Game)handler.receiveFromServer();
			this.game=game;
			int id=(Integer)handler.receiveFromServer();
			this.ID=id;
		}catch(ClassNotFoundException e){
			Client.logger.log(Level.ALL, e.getMessage());
		}
		
		memoryContainer.setGameRef(game);
		this.clientView.setGame(game);
		this.clientView.setId(this.ID);
		this.clientView.setMemoryContainer(memoryContainer);
		executor.submit(new ClientOutHandlerSocket(handler, 
				 memoryContainer, clientView));
		executor.submit(new ClientInHandlerSocket(handler, memoryContainer, clientView));
		
	}

	@Override
	public ConnectionHandler getConnectionHandler() {
		return handler;
	}
}
