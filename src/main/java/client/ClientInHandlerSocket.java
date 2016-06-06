package client;

import java.io.IOException;
import java.io.ObjectInputStream;

import controller.BonusChange;
import controller.Change;
import controller.ErrorChange;
import controller.ModelChange;
import controller.PermitsChange;
import controller.StateChange;
import model.game.*;
import view.LocalStorage;
import view.State;

public class ClientInHandlerSocket implements Runnable 
{
	private ObjectInputStream socketIn;
	private Game gameLocalCopy;
	private LocalStorage memoryContainer;
	private int iD;
	
	public ClientInHandlerSocket(ObjectInputStream socketIn,
			LocalStorage container, int iD) 
	{
		this.socketIn = socketIn;
		this.memoryContainer=container;
		this.gameLocalCopy = memoryContainer.getGameRef();
		this.iD = iD;
	}

	@Override
	public void run() 
	{
		while (true) 
		{
			Object x = new Object();
			try {
				 x = socketIn.readUnshared();
			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
			if(x.getClass().equals(Integer.class)){
				this.iD = ((Integer)x).intValue();
				System.out.println(this.iD);
			}
			else if(x.getClass().equals(String.class)){
				System.out.println((String)x);
				this.memoryContainer.setUpdated(true);
			}
			else if(x.getClass().equals(BonusChange.class) || 
					x.getClass().equals(PermitsChange.class)){
				System.out.println(x.toString());
				memoryContainer = new LocalStorage((Change)x, this.gameLocalCopy);
			}
			else if(x.getClass().equals(ModelChange.class)){
				this.gameLocalCopy = ((ModelChange)x).getGame();
				System.out.println(this.gameLocalCopy);
			}
			else if(x.getClass().equals(StateChange.class)){
				State y = ((StateChange)x).getStateChanged();
				this.gameLocalCopy.setGameState(y);
			}else if(x.getClass().equals(ErrorChange.class)){
				ErrorChange error=(ErrorChange)x;
				System.err.println("WARNING!!");
				System.err.println(error.getMessage());
			}
			
			synchronized(memoryContainer){
				memoryContainer.setGameRef(this.gameLocalCopy);
				memoryContainer.setUpdated(true);
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
