/**
 * 
 */
package server;

import java.io.IOException;
import java.rmi.AlreadyBoundException;

import org.jdom2.JDOMException;

import client.ClientRMIRemote;

/**
 * @author Francesco Vetrò
 *
 */
public class ServerRMIRegistration implements ServerRMIRegistrationRemote {

	private Server server;
	
	public ServerRMIRegistration(Server server) {
		this.server = server;
	}
	/* (non-Javadoc)
	 * @see server.ServerRMIRegistrationRemote#register(client.ClientRMIRemote)
	 */
	@Override
	public ServerRMIViewRemote register(ClientRMIRemote client) throws AlreadyBoundException, JDOMException, IOException {
		ServerRMIView view = new ServerRMIView(client);
		server.addClient(view);
		return view;
	}

}
