/**
 * 
 */
package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import client.RMIClientRemote;
import controller.Change;
import model.game.Game;
import view.Request;
import view.View;

/**
 * @author Francesco Vetrò
 *
 */
public class ServerRMIView extends View implements ServerRMIViewRemote{

	private RMIClientRemote client;
	
	public ServerRMIView(RMIClientRemote client) throws RemoteException{
		UnicastRemoteObject.exportObject(this, 1099);
		this.client = client;
	}
	
	@Override
	public void sendRequest(Request request) throws RemoteException {
		this.notifyObservers(request);
	}

	@Override
	public void update(Change c){
		try {
			this.client.printChange(c);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void update(String s){
		try {
			this.client.printString(s);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setID(int iD){
		try {
			this.client.printInt(iD);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
}
