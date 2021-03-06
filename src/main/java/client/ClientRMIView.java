/**
 * 
 */
package client;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.jdom2.JDOMException;

import controller.BonusChange;
import controller.Change;
import controller.ChatChange;
import controller.ErrorChange;
import controller.MarketChange;
import controller.ModelChange;
import controller.PermitsChange;
import controller.StateChange;
import server.ServerRMIRegistrationRemote;
import server.ServerRMIViewRemote;
import view.LocalStorage;
import view.Request;
import view.State;

/**
 * @author Francesco Vetrò
 *
 */
public class ClientRMIView extends UnicastRemoteObject implements ClientRMIRemote {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2189915106098111955L;
	private ServerRMIViewRemote serverView;
	private LocalStorage memoryContainer;
	private int ID;
	private String name;
	private ClientViewInterface view;
	
	public ClientRMIView(String name, ServerRMIRegistrationRemote serverRegistration, ClientViewInterface view) throws AlreadyBoundException, JDOMException, IOException{
		super();
		this.memoryContainer = new LocalStorage();
		this.name = name;
		this.view = view;
		this.serverView = serverRegistration.register(this);
	}
	
	/* (non-Javadoc)
	 * @see client.RMIClientRemote#printChange(controller.Change)
	 */
	@Override
	public void printChange(Change change) throws RemoteException {
		if(change.getClass().equals(BonusChange.class)){
			synchronized (memoryContainer) {
				this.memoryContainer.setBonus(((BonusChange)change).getBonusList());
				this.memoryContainer.setBonusRepeat(((BonusChange)change).getRepeat());
			}
		}else if(change.getClass().equals(PermitsChange.class)){
			synchronized (memoryContainer) {
				this.memoryContainer.setPermits(((PermitsChange)change).getPermits());
			}
		}
		else if(change.getClass().equals(ModelChange.class)){
			synchronized (memoryContainer) {
				memoryContainer.setGameRef(((ModelChange)change).getGame());
			}
			this.view.updateModel(((ModelChange)change).getGame());
		}
		else if(change.getClass().equals(StateChange.class)){
			State y = ((StateChange)change).getStateChanged();
			synchronized (memoryContainer) {
				this.memoryContainer.getGameRef().setGameState(y);
			}
		}
		else if(change.getClass().equals(ErrorChange.class)){
			ErrorChange error=(ErrorChange)change;
			this.view.stampMessage("WARNING!!");
			this.view.stampMessage(error.getMessage());
		}
		else if(change.getClass().equals(MarketChange.class)){
			MarketChange market=(MarketChange)change;
			synchronized (memoryContainer) {
				this.memoryContainer.getGameRef().setMarket(market.getMarket());
			}
		}else if(change.getClass().equals(ChatChange.class)){
			ChatChange chatChange=(ChatChange)change;
			this.view.updateChat(chatChange.getMessage(),chatChange.getOwnersName(),chatChange.getId());
		}
		this.memoryContainer.setUpdated(true);
	}

	/* (non-Javadoc)
	 * @see client.RMIClientRemote#printString(java.lang.String)
	 */
	@Override
	public void printString(String string) throws RemoteException {
		this.view.stampMessage(string);
	}

	/* (non-Javadoc)
	 * @see client.RMIClientRemote#printInt(int)
	 */
	@Override
	public void printInt(Integer value) throws RemoteException {
		this.ID = value.intValue();
	}
	
	/* (non-Javadoc)
	 * @see client.ClientRMIRemote#getName()
	 */
	@Override
	public String getName() throws RemoteException{
		return this.name;
	}
	
	/* (non-Javadoc)
	 * @see client.ClientRMIRemote#getID()
	 */
	@Override
	public int getID() throws RemoteException{
		return this.ID;
	}
	
	public LocalStorage getMemoryContainer() {
		return this.memoryContainer;
	}

	@Override
	public void sendRequestToServerView(Request request) throws RemoteException {
		serverView.receiveRequest(request);
	}
}
