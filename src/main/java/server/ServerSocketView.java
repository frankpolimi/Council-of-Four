package server;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import view.View;

public class ServerSocketView extends View implements Runnable
{
	private Socket socket;
	private Scanner socketIn;
	private PrintWriter socketOut;
	
	public ServerSocketView(Socket socket) throws IOException
	{
		this.socket = socket;
		socketIn = new Scanner(socket.getInputStream());
		socketOut = new PrintWriter(socket.getOutputStream());
	}

	@Override
	public void run()
	{
		/* 
		 * Qua come vogliamo fare? ho tolto try and catch perche' 
		 * unreachable dato che non breakkiamo mai il while, 
		 * dato che il quit gia' uccide il client 
		 * ma non tocca il server per ora
		 */
		while (true)
		{
			String line = socketIn.nextLine(); //Qua arrivano richieste
			this.notifyObservers(/*Qualcosa*/);
		}
	}
}
