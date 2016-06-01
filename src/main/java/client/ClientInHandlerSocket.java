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
	private int iD;
	
	public ClientInHandlerSocket(ObjectInputStream socketIn, Game game, 
			LocalStorage container, int iD) 
	{
		this.socketIn = socketIn;
		this.memoryContainer=container;
		this.gameLocalCopy = game;
		this.iD = iD;
	}

	@Override
	public void run() 
	{
		while (true) 
		{
			Change line;
			Object x = new Object();
			try {
				 x = socketIn.readObject();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}

			if(x.getClass().equals(Integer.class)){
				this.iD = ((Integer)x).intValue();
				System.out.println(this.iD);
			}
			else if(x.getClass().equals(String.class))
				System.out.println((String)x);
			else if(x.getClass().equals(Change.class)){
				line = (Change)x;
				if(line.getClass().equals(BonusChange.class) || 
						line.getClass().equals(PermitsChange.class)){
					System.out.println(line.toString());
					memoryContainer = new LocalStorage(line);
				}
				else if(line.getClass().equals(ModelChange.class)){
					this.gameLocalCopy = ((ModelChange)line).getGame();
					System.out.println(gameLocalCopy);
				}
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
