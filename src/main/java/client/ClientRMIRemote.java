package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import controller.Change;
import view.LocalStorage;
import view.Request;

public interface ClientRMIRemote extends Remote{
	
	public void printChange(Change change) throws RemoteException;
	public void printString(String string) throws RemoteException;
	public void printInt(Integer value) throws RemoteException;
	
	public void sendRequestToServerView(Request request) throws RemoteException;
	
	public String getName() throws RemoteException;
	public int getID() throws RemoteException;
	public LocalStorage getMemoryContainer() throws RemoteException;
}
