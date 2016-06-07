package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import view.Request;

public interface ServerRMIGameViewRemote extends Remote{

	public void sendRequest(Request request) throws RemoteException;
	
}
