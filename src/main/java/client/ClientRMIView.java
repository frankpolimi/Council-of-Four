/**
 * 
 */
package client;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import controller.BonusChange;
import controller.Change;
import controller.ErrorChange;
import controller.ModelChange;
import controller.PermitsChange;
import controller.StateChange;
import view.LocalStorage;
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
	private LocalStorage memoryContainer;
	private int ID;
	private String name;
	
	public ClientRMIView(String name) throws RemoteException{
		super();
		memoryContainer = new LocalStorage();
		this.name = name;
	}
	
	/* (non-Javadoc)
	 * @see client.RMIClientRemote#printChange(controller.Change)
	 */
	@Override
	public void printChange(Change change) throws RemoteException {
		if(change.getClass().equals(BonusChange.class) || 
				change.getClass().equals(PermitsChange.class)){
			System.out.println(change.toString());
			memoryContainer = new LocalStorage(change, memoryContainer.getGameRef());
		}
		else if(change.getClass().equals(ModelChange.class)){
			memoryContainer.setGameRef(((ModelChange)change).getGame());;
			System.out.println(memoryContainer.getGameRef());
		}
		else if(change.getClass().equals(StateChange.class)){
			State y = ((StateChange)change).getStateChanged();
			this.memoryContainer.getGameRef().setGameState(y);
		}else if(change.getClass().equals(ErrorChange.class)){
			ErrorChange error=(ErrorChange)change;
			System.err.println("WARNING!!");
			System.err.println(error.getMessage());
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
	public void printInt(int value) throws RemoteException {
		this.ID = value;
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
	
	protected LocalStorage getMemoryContainer() {
		return this.memoryContainer;
	}

}
