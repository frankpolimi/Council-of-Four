package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import client.ClientRMIRemote;
import view.Request;

public interface ServerRMIViewRemote extends Remote{

	public void sendRequest(Request request) throws RemoteException;
	
	public void registerClient(ClientRMIRemote client) throws RemoteException;
}
