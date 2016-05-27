package client;

import java.util.Scanner;

public class ClientInHandlerSocket implements Runnable 
{
	private Scanner socketIn;

	public ClientInHandlerSocket(Scanner socketIn) 
	{
		super();
		this.socketIn = socketIn;
	}

	@Override
	public void run() 
	{
		while (true) 
		{
			String line = socketIn.nextLine(); // Invece di uno scanner di stringhe abbiamo uno scanner di certi Object
			System.out.println(line);
		}
	}

}
