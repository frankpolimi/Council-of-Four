package client;

import java.io.IOException;

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
	private ClientViewInterface clientView;
	
	public ClientInHandlerSocket(ConnectionHandler handler,
			LocalStorage container, ClientViewInterface clientView) 
	{
		this.handler=handler;
		this.memoryContainer=container;
		this.gameLocalCopy = memoryContainer.getGameRef();
		this.clientView=clientView;
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
			/*
			if(x.getClass().equals(Integer.class)){
				this.clientView.setId(((Integer)x).intValue());
				System.out.println(this.clientView.getId());
			}*/
			if(x.getClass().equals(String.class)){
				this.clientView.stampMessage((String)x);
				this.memoryContainer.setUpdated(true);
			}
			else if(x.getClass().equals(BonusChange.class) || 
					x.getClass().equals(PermitsChange.class)){
				this.clientView.stampMessage(x.toString());
				memoryContainer = new LocalStorage((Change)x, this.gameLocalCopy);
				this.clientView.setMemoryContainer(memoryContainer);
			}
			else if(x.getClass().equals(ModelChange.class)){
				this.gameLocalCopy = ((ModelChange)x).getGame();
				this.clientView.updateModel(this.gameLocalCopy);
			}else if(x.getClass().equals(ErrorChange.class)){
				ErrorChange error=(ErrorChange)x;
				this.clientView.stampMessage("WARNING!!"+error.getMessage());
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
