package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import view.Request;

public interface ServerRMIViewRemote extends Remote{

	public void receiveRequest(Request request) throws RemoteException;
	
	public void sendString(String string) throws RemoteException;
	
}
