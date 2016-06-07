/**
 * 
 */
package server;

import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;

import client.RMIClientRemote;

/**
 * @author Francesco Vetrò
 *
 */
public interface ServerRMIRegistrationViewRemote extends Remote {
	
	public ServerRMIGameViewRemote register(RMIClientRemote client) throws
		RemoteException, AlreadyBoundException;
}
