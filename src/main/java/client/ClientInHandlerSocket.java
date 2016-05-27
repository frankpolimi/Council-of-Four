package client;

import java.util.Scanner;
import model.game.*;
import view.LocalStorage;

public class ClientInHandlerSocket implements Runnable 
{
	private Scanner socketIn;
	private Game gameLocalCopy;
	private LocalStorage memoryContainer; //Ora è object ma poi decidi te cosa mettere

	public ClientInHandlerSocket(Scanner socketIn, LocalStorage container) 
	{
		super();
		this.socketIn = socketIn;
		this.memoryContainer=container;
	}

	@Override
	public void run() 
	{
		while (true) 
		{
			String line = socketIn.nextLine(); 
			// Invece di uno scanner di stringhe abbiamo uno scanner di certi Object
			//Hai il contenitore, ci ficchi la roba dentro e poi vedi cosa farne
			System.out.println(line);
		}
	}

	public Game getGameLocalCopy() 
	{
		return gameLocalCopy;
	}

	public void setGameLocalCopy(Game gameLocalCopy) 
	{
		this.gameLocalCopy = gameLocalCopy;
	}
	
}
