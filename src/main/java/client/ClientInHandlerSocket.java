package client;

import java.io.IOException;
import java.io.ObjectInputStream;

import controller.BonusChange;
import controller.Change;
import controller.ErrorChange;
import controller.MarketChange;
import controller.ModelChange;
import controller.PermitsChange;
import model.game.*;
import view.LocalStorage;

public class ClientInHandlerSocket implements Runnable 
{
	private ConnectionHandler handler;
	private Game gameLocalCopy;
	private LocalStorage memoryContainer;
	private int iD;
	
	public ClientInHandlerSocket(ConnectionHandler handler,
			LocalStorage container, int iD) 
	{
		this.handler=handler;
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
				 x = handler.receiveFromServer();
			} catch (ClassNotFoundException | IOException e) {
				System.out.println("THE GAME IS FINISHED - BYE BYE");
				break;
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
			}else if(x.getClass().equals(ErrorChange.class)){
				ErrorChange error=(ErrorChange)x;
				System.err.println("WARNING!!");
				System.err.println(error.getMessage());
			}else if(x.getClass().equals(MarketChange.class)){
				MarketChange market=(MarketChange)x;
				this.gameLocalCopy.setMarket(market.getMarket());
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
