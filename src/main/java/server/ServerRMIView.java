/**
 * 
 */
package server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

import org.jdom2.JDOMException;

import client.ClientRMIRemote;
import controller.Change;
import view.Request;
import view.View;

/**
 * @author Francesco Vetrò
 *
 */
public class ServerRMIView extends View implements ServerRMIViewRemote{

	private Server server;
	private Set<ClientRMIRemote> clients;
	
	public ServerRMIView(Server server){
		this.server = server;
		clients = new HashSet<>();
	}

	@Override
	public void sendRequest(Request request) throws RemoteException {
		this.notifyObservers(request);
	}

	//TODO aggiungere la change univoca
	@Override
	public void update(Change c){
		for(ClientRMIRemote client : this.clients)
			try {
				client.printChange(c);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	}
	
	//TODO cambia per scrivere le stringhe
	public void update(String s){
		for(ClientRMIRemote client : this.clients)
			try {
				client.printString(s);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	}
	
	//TODO cambia per scrivere l'id unico
	@Override
	public void setID(int iD){
		for(ClientRMIRemote client : this.clients)
			try {
				client.printInt(iD);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
	}

	/*
	 * (non-Javadoc)
	 * @see server.ServerRMIViewRemote#registerClient(client.ClientRMIRemote)
	 */
	@Override
	public void registerClient(ClientRMIRemote client) throws JDOMException, IOException {
		server.addRMIClient(client);
		this.clients.add(client);
		
	}
}
