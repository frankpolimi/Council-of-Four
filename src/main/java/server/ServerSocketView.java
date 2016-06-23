package server;

import java.io.*;
import java.net.Socket;
import java.net.SocketTimeoutException;

import client.SocketConnectionHandler;
import controller.Change;
import controller.ErrorChange;
import view.QuitRequest;
import view.Request;
import view.View;

/**
 * @author Emanuele Ricciardelli
 */
public class ServerSocketView extends View implements Runnable
{
	//private final Socket socket;
	//private final ObjectInputStream socketIn;
	//private final ObjectOutputStream socketOut;
	private SocketConnectionHandler handler;
	private String name;
	private int ID;
	
	/**
	 * constructor for the socket
	 * @param socket the socket that the server prepares
	 * @throws IOException if is an error in the input or output stream
	 * @throws ClassNotFoundException 
	 */
	public ServerSocketView(Socket socket) throws IOException, ClassNotFoundException
	{
			this.handler=new SocketConnectionHandler(socket);
			this.name=handler.getName();
	}

	/**
	 * method that receive requests from the socket and
	 * notify it to the controller 
	 */
	@Override
	public void run()
	{
		while (true)
		{
			try {
				Request line = (Request)handler.receiveFromClient();
				this.notifyObservers(line);
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage()+"  "+e.getCause());
			} catch (IOException f){
				System.out.println("The client"+this.ID+" has been disconnected and removed in the game model");
				this.notifyObservers(new QuitRequest(this.ID));
				break;
			}
			catch(IllegalArgumentException | IllegalStateException e1){
				try {
					handler.sendToClient(new ErrorChange(e1.getMessage()));
				} catch (IOException e) {
					
				}
			}
		}
	}
	
	/**
	 * this method receives changes from the model 
	 * and will send to the client via socket
	 */
	@Override
	public void update(Change change){
		
		try {
			handler.sendToClient(change);
		} catch (IOException e) {
			
		}
		
	}
	
	/**
	 * method to set the ID of the player related to this view
	 * will catch a IOException if sending to client fails
	 * @param serialID the id of the game player associated to this view
	 */
	@Override
	public void setID(int serialID){
		this.ID = serialID;
		try {
			handler.sendToClient(new Integer(this.ID));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the connection handler for the specific
	 * 			kind of connection
	 */
	public SocketConnectionHandler getHandler() {
		return handler;
	}
	

	
	
}
