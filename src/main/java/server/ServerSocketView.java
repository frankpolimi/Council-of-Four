package server;

import java.io.*;
import java.net.Socket;

import controller.Change;
import view.Request;
import view.View;

public class ServerSocketView extends View implements Runnable
{
	private final Socket socket;
	private final ObjectInputStream socketIn;
	private final ObjectOutputStream socketOut;
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
		this.socket = socket;
		this.ID = 0;
		socketOut = new ObjectOutputStream(socket.getOutputStream());
		socketIn = new ObjectInputStream(socket.getInputStream());
		name = (String)socketIn.readUnshared();
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
				Request line = (Request)socketIn.readUnshared();
				this.notifyObservers(line);
			} catch (ClassNotFoundException e) {
				System.out.println(e.getMessage()+"  "+e.getCause());
			} catch (IOException f){
				System.out.println("The client has been disconnected");
				break;
			}
			catch(IllegalArgumentException | IllegalStateException e1){
				try {
					this.socketOut.reset();
					this.socketOut.writeObject(e1.getMessage());
					this.socketOut.flush();
				} catch (IOException e) {
					e.printStackTrace();
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
			this.socketOut.reset();
			this.socketOut.writeUnshared(change);
			this.socketOut.flush();
		} catch (IOException e) {
			System.out.println("The client has been disconnected");
		}
		
	}
	
	@Override
	public void setID(int serialID){
		this.ID = serialID;
		try {
			this.socketOut.writeUnshared(new Integer(serialID));
			this.socketOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return the socket
	 */
	public Socket getSocket() {
		return socket;
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
	 * @return the socketIn
	 */
	public ObjectInputStream getSocketIn() {
		return socketIn;
	}

	/**
	 * @return the socketOut
	 */
	public ObjectOutputStream getSocketOut() {
		return socketOut;
	}

	

	
	
}
