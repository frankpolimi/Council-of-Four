package client;

import java.io.IOException;
import java.io.ObjectInputStream;

import controller.BonusChange;
import controller.Change;
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

			synchronized (memoryContainer) {
				this.gameLocalCopy=memoryContainer.getGameRef();
			}
			
			if(x.getClass().equals(Integer.class)){
				this.iD = ((Integer)x).intValue();
				System.out.println(this.iD);
			}
			else if(x.getClass().equals(String.class))
				System.out.println((String)x);
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
			}
			
			synchronized(memoryContainer){
				memoryContainer.setGameRef(this.gameLocalCopy);
				
			}
			
			synchronized(this){
				this.notifyAll();
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
