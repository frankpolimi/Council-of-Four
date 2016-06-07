/**
 * 
 */
package server;

import java.nio.channels.ClosedByInterruptException;
import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

import client.RMIClientRemote;

/**
 * @author Francesco Vetrò
 *
 */
public class ServerRMIRegistrationView implements ServerRMIRegistrationViewRemote {

	private Server server;
	private Set<ServerRMIView> clients;
	
	public ServerRMIRegistrationView(Server server) {
		this.server = server;
		clients = new HashSet<>();
	}
	
	@Override
	public ServerRMIViewRemote register(RMIClientRemote client) throws
		RemoteException, AlreadyBoundException
	{
		ServerRMIView view = new ServerRMIView(client);
		clients.add(view);
		server.addRMIClient(view);
		return view;
	}

}
