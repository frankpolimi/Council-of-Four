package client;

import java.io.IOException;
import java.io.ObjectInputStream;

import controller.BonusChange;
import controller.Change;
import controller.ModelChange;
import controller.PermitsChange;
import controller.StateChange;
import model.game.*;
import view.EndState;
import view.LocalStorage;
import view.MarketBuyingState;
import view.MarketSellingState;
import view.StartState;
import view.State;

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
			else if(x.getClass().equals(BonusChange.class) || 
					x.getClass().equals(PermitsChange.class)){
				System.out.println(x.toString());
				memoryContainer = new LocalStorage((Change)x);
			}
			else if(x.getClass().equals(ModelChange.class)){
				this.gameLocalCopy = ((ModelChange)x).getGame();
				System.out.println(gameLocalCopy);
			}
			else if(x.getClass().equals(StateChange.class)){
				State y = ((StateChange)x).getStateChanged();
				if(y.getClass().equals(StartState.class)) 
					this.gameLocalCopy.setGameState((StartState)y);
				else if(y.getClass().equals(MarketBuyingState.class))
					this.gameLocalCopy.setGameState((MarketBuyingState)y);
				else if(y.getClass().equals(MarketSellingState.class))
					this.gameLocalCopy.setGameState((MarketSellingState)y);
				else if(y.getClass().equals(EndState.class))
					this.gameLocalCopy.setGameState((EndState)y);
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
