package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import view.Request;

public interface ServerRMIViewRemote extends Remote{

	public void sendRequest(Request request) throws RemoteException;
	
}
