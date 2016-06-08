/**
 * 
 */
package server;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import org.jdom2.JDOMException;

import client.ClientRMIRemote;

/**
 * @author Francesco Vetrò
 *
 */
public interface ServerRMIRegistrationRemote extends Remote{
	
	public ServerRMIViewRemote register(ClientRMIRemote client) 
			throws RemoteException, AlreadyBoundException, JDOMException, IOException;

}
