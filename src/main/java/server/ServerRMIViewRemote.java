package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import view.Request;

/**
 * @author Francesco Vetro'
 *
 * class that allows a client to communicate with a server
 * via RMI connection 
 */
public interface ServerRMIViewRemote extends Remote{
	
	/**
	 * the server will read the request sent by the client
	 * @param request the message sent by the client
	 * @throws RemoteException if remote invocation fails
	 */
	public void receiveRequest(Request request) throws RemoteException;
	
	/**
	 * the server will communicate with the client via a string
	 * @param string the string sent to the client
	 * @throws RemoteException if remote invocation fails
	 */
	public void sendString(String string) throws RemoteException;
	
}
