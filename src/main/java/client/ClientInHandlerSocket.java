package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import controller.Change;
import model.game.*;
import view.LocalStorage;

public class ClientInHandlerSocket implements Runnable 
{
	private ObjectInputStream socketIn;
	private Game gameLocalCopy;
	private LocalStorage memoryContainer;
	public ClientInHandlerSocket(ObjectInputStream socketIn, LocalStorage container) 
	{
		this.socketIn = socketIn;
		this.memoryContainer=container;
	}

	@Override
	public void run() 
	{
		while (true) 
		{
			
			try {
				Change line;
				line = (Change)socketIn.readObject();
				System.out.println(line);
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			// Invece di uno scanner di stringhe abbiamo uno scanner di certi Object
			//Hai il contenitore, ci ficchi la roba dentro e poi vedi cosa farne
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
