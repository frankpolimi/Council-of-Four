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
 * @author Francesco Vetro'
 *
 * interface that allows to register to something
 */
public interface ServerRMIRegistrationRemote extends Remote{
	
	/**
	 * method that allows the client's registration
	 * @param client the client that will be registered
	 * @return an interface of the point where the client is connected
	 * 			and will communicate
	 * @throws RemoteException if remote invocation of a method fails
	 * @throws AlreadyBoundException if already exists in the register
	 * 									the client
	 * @throws JDOMException if configuration fails
	 * @throws IOException if configuration or registration fails
	 */
	public ServerRMIViewRemote register(ClientRMIRemote client) 
			throws RemoteException, AlreadyBoundException, JDOMException, IOException;

}
