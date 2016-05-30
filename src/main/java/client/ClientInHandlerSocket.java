package client;

import java.io.IOException;
import java.io.ObjectInputStream;

import controller.BonusChange;
import controller.Change;
import controller.ModelChange;
import controller.PermitsChange;
import model.game.*;
import view.LocalStorage;

public class ClientInHandlerSocket implements Runnable 
{
	private ObjectInputStream socketIn;
	private Game gameLocalCopy;
	private LocalStorage memoryContainer;
	public ClientInHandlerSocket(ObjectInputStream socketIn, Game game, LocalStorage container) 
	{
		this.socketIn = socketIn;
		this.memoryContainer=container;
		this.gameLocalCopy = game;
	}

	@Override
	public void run() 
	{
		while (true) 
		{
			
			try {
				Change line;
				Object x = null;
				
				x = socketIn.readObject();
				
				if(x.getClass().equals(String.class))
					System.out.println(((String)x));
				else{
					line = (Change)x;
					if(line.getClass().equals(BonusChange.class) || 
							line.getClass().equals(PermitsChange.class))
						memoryContainer = new LocalStorage(line);
					else if(line.getClass().equals(ModelChange.class))
						this.gameLocalCopy = ((ModelChange)line).getGame();
					System.out.println(line);
				}
				
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
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
