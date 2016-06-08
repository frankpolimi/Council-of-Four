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
	}

	//TODO aggiungere la change univoca
	@Override
	public void update(Change c){
		try {
			client.printChange(c);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	//TODO cambia per scrivere le stringhe
	public void update(String s){
		try {
			client.printString(s);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	//TODO cambia per scrivere l'id unico
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
