/**
 * 
 */
package server;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;

import client.RMIClientRemote;

/**
 * @author Francesco Vetrò
 *
 */
public class ServerRMIRegistrationView implements ServerRMIRegistrationViewRemote {

	private Server server;
	
	public ServerRMIRegistrationView(Server server) {
		this.server = server;
	}
	
	@Override
	public ServerRMIGameViewRemote register(RMIClientRemote client) throws
		RemoteException, AlreadyBoundException
	{
		ServerRMIGameView view = new ServerRMIGameView(client);
		server.addRMIClient(view);
		return view;
	}

}
