package server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import view.Request;
import view.View;

public class ServerSocketView extends View implements Runnable
{
	private Socket socket;
	private ObjectInputStream socketIn;
	private ObjectOutputStream socketOut;
	
	public ServerSocketView(Socket socket) throws IOException
	{
		this.socket = socket;
		socketIn = new ObjectInputStream(socket.getInputStream());
		socketOut = new ObjectOutputStream(socket.getOutputStream());
	}

	@Override
	public void run()
	{
		while (true)
		{
			try {
				Request line = (Request)socketIn.readObject();
				this.notifyObservers(line);
			} catch (ClassNotFoundException | IOException e) {
				//e.printStackTrace();
			}
			
		}
		
	}
}
