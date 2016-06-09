/**
 * 
 */
package server;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import client.ClientRMIRemote;
import controller.Change;
import view.Request;
import view.View;

/**
 * @author Francesco Vetrò
 *
 */
public class ServerRMIView extends View implements ServerRMIViewRemote{

	private ClientRMIRemote client;
	private int ID;
	
	public ServerRMIView(ClientRMIRemote client) throws RemoteException{
		UnicastRemoteObject.exportObject(this, 1099);
		this.client = client;
	}

	@Override
	public void sendRequest(Request request) throws RemoteException {
		this.notifyObservers(request);
		//TODO mettere try catch in caso di azione non fattibile
	}

	@Override
	public void update(Change c){
		try {
			client.printChange(c);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public void update(String s){
		try {
			client.printString(s);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setID(int iD){
		this.ID = iD;
		try {
			client.printInt(iD);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	public ClientRMIRemote getClient(){
		return client;
	}
}
