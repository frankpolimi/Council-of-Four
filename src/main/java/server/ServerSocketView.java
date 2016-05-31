package server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import controller.Change;
import view.Request;
import view.View;

public class ServerSocketView extends View implements Runnable
{
	private Socket socket;
	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;
	
	/**
	 * constructor for the socket
	 * @param socket the socket that the server prepares
	 * @throws IOException if is an error in the input or output stream
	 */
	public ServerSocketView(Socket socket) throws IOException
	{
		this.socket = socket;
		socketIn = new ObjectInputStream(socket.getInputStream());
		socketOut = new ObjectOutputStream(socket.getOutputStream());
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
				Request line = (Request)socketIn.readObject();
				this.notifyObservers(line);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
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
			this.socketOut.writeObject(change);
			this.socketOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	@Override
	public void setID(int serialID){
		try {
			this.socketOut.writeObject(new Integer(serialID));
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


	
	
}
