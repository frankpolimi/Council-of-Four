/**
 * 
 */
package server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import client.ClientRMIRemote;
import client.RMIConnectionHandler;
import controller.Change;
import view.QuitRequest;
import view.Request;
import view.View;

/**
 * @author Francesco Vetrò
 *
 */
public class ServerRMIView extends View implements ServerRMIViewRemote{

	private RMIConnectionHandler client;
	private int ID;
	
	public ServerRMIView(ClientRMIRemote client) throws RemoteException{
		UnicastRemoteObject.exportObject(this, 1099);
		this.client = new RMIConnectionHandler(client);
	}

	@Override
	public void receiveRequest(Request request) throws RemoteException {
		try{
			this.notifyObservers(request);
		}catch (IllegalArgumentException | IllegalStateException e1){
			try {
				this.client.sendToClient(e1.getMessage());
			} catch (IOException e) {
				System.out.println("The client"+this.ID+" has been disconnected and removed in the game model");
				this.notifyObservers(new QuitRequest(this.ID));
			}
		}
	}

	@Override
	public void update(Change c){
		try {
			this.client.sendToClient(c);
		} catch (RemoteException e) {
			try {
				this.client.closeConnection();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			System.out.println("The client"+this.ID+" has been disconnected and removed in the game model");
			this.notifyObservers(new QuitRequest(this.ID));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void update(String s) throws RemoteException{
		try {
			this.client.sendToClient(s);
		} catch (RemoteException e) {
			System.out.println("The client"+this.ID+" has been disconnected and removed in the game model");
			throw new RemoteException();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setID(int iD){
		this.ID = iD;
		try {
			this.client.sendToClient(new Integer(iD));
		} catch (RemoteException e) {
			System.out.println("The client"+this.ID+" has been disconnected and removed in the game model");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ClientRMIRemote getClient(){
		return client.getRmiView();
	}
	
	@Override
	public String getName() {
		try {
			return client.getRmiView().getName();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "";
	}

	@Override
	public void sendString(String string) throws RemoteException {
		this.update(string);
	}
}
