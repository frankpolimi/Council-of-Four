/**
 * 
 */
package client;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import model.actions.Action;
import model.game.Game;
import view.Request;
import view.View;

/**
 * @author Francesco Vetrò
 *
 */
public class ClientRMI extends View implements RMIClientRemote{
	
	private RMIClientRemote client;
	
	protected ClientRMI(RMIClientRemote client) throws RemoteException{
		UnicastRemoteObject.exportObject(this, 1099);
		
	}

	public void performAction(Request request, Game game) throws RemoteException {
		/**
		 * devo mettere che chiamo la action da dentro una request?
		 */
	}

}
