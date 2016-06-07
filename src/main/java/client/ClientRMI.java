/**
 * 
 */
package client;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import controller.BonusChange;
import controller.Change;
import controller.ErrorChange;
import controller.ModelChange;
import controller.PermitsChange;
import controller.StateChange;
import server.ServerRMIGameViewRemote;
import server.ServerRMIRegistrationViewRemote;
import view.State;

/**
 * @author Francesco Vetrò
 *
 */
public class ClientRMI extends UnicastRemoteObject implements Serializable, RMIClientRemote{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2606594429637762781L;
	private final static String HOST = "127.0.0.1";
	private final static int PORT = 1099;
	
	public ClientRMI() throws RemoteException{
		super();
	}
	
	public void connect() throws RemoteException, NotBoundException, MalformedURLException,
								AlreadyBoundException
	{
		System.out.println("RMI registry bindings");
		
		String name = "game";
		
		Registry registry = LocateRegistry.getRegistry(HOST, PORT);
		ServerRMIRegistrationViewRemote game = (ServerRMIRegistrationViewRemote)
				registry.lookup(name);	
		
		ServerRMIGameViewRemote view = game.register(this);
		
		
		while(true){
			/*
			 * il controllo e poi si eseguono le azioni
			 */
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see client.RMIClientRemote#printChange(controller.Change)
	 */
	@Override
	public void printChange(Change change) throws RemoteException {
		if(change.getClass().equals(BonusChange.class) || 
				change.getClass().equals(PermitsChange.class)){
			System.out.println(change.toString());
			//memoryContainer = new LocalStorage(change, this.gameLocalCopy);
		}
		else if(change.getClass().equals(ModelChange.class)){
			//this.gameLocalCopy = ((ModelChange)change).getGame();
			//System.out.println(this.gameLocalCopy);
		}
		else if(change.getClass().equals(StateChange.class)){
			State y = ((StateChange)change).getStateChanged();
			//this.gameLocalCopy.setGameState(y);
		}else if(change.getClass().equals(ErrorChange.class)){
			ErrorChange error=(ErrorChange)change;
			System.err.println("WARNING!!");
			System.err.println(error.getMessage());
		}		
	}

	@Override
	public void printString(String string) throws RemoteException {
		System.out.println(string);
	}

	@Override
	public void printInt(int value) throws RemoteException {
		
	}
}
