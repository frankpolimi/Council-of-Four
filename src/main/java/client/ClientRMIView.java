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
	
	public ClientRMIView(String name, ServerRMIRegistrationRemote serverRegistration) throws AlreadyBoundException, JDOMException, IOException{
		super();
		this.memoryContainer = new LocalStorage();
		this.name = name;
		this.serverView = serverRegistration.register(this);
	}
	
	/* (non-Javadoc)
	 * @see client.RMIClientRemote#printChange(controller.Change)
	 */
	@Override
	public void printChange(Change change) throws RemoteException {
		if(change.getClass().equals(BonusChange.class) || 
				change.getClass().equals(PermitsChange.class)){
			System.out.println(change.toString());
			synchronized (memoryContainer) {
				memoryContainer = new LocalStorage(change, memoryContainer.getGameRef());
			}
		}
		else if(change.getClass().equals(ModelChange.class)){
			synchronized (memoryContainer) {
				memoryContainer.setGameRef(((ModelChange)change).getGame());
			}
			memoryContainer.setUpdated(true);
			System.out.println(memoryContainer.getGameRef());
		}
		else if(change.getClass().equals(StateChange.class)){
			State y = ((StateChange)change).getStateChanged();
			synchronized (memoryContainer) {
				this.memoryContainer.getGameRef().setGameState(y);
			}
		}
		else if(change.getClass().equals(ErrorChange.class)){
			ErrorChange error=(ErrorChange)change;
			System.err.println("WARNING!!");
			System.err.println(error.getMessage());
		}
		else if(change.getClass().equals(MarketChange.class)){
			MarketChange market=(MarketChange)change;
			synchronized (memoryContainer) {
				this.memoryContainer.getGameRef().setMarket(market.getMarket());
			}
		}
	}

	/* (non-Javadoc)
	 * @see client.RMIClientRemote#printString(java.lang.String)
	 */
	@Override
	public void printString(String string) throws RemoteException {
		System.out.println(string);
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
