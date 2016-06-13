package client;

import java.io.IOException;

import controller.Change;
import view.Request;

public class RMIConnectionHandler implements ConnectionHandler {
	
	private ClientRMIRemote rmiView;

	public RMIConnectionHandler(ClientRMIRemote rmiView) {
		this.rmiView = rmiView;
	}

	@Override
	public void sendToServer(Object o) throws IOException {
		rmiView.sendRequestToServerView((Request) o);
	}

	@Override
	public void sendToClient(Object o) throws IOException {
		if(o.getClass().getSuperclass().equals(Change.class))
			rmiView.printChange((Change)o);
		else if(o.getClass().equals(String.class))
			rmiView.printString((String)o);
		else if(o.getClass().equals(Integer.class))
			rmiView.printInt((Integer)o);
	}

	@Override
	public Object receiveFromServer() throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Object receiveFromClient() throws ClassNotFoundException, IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void closeConnection() throws IOException {
		ClientRMIView.unexportObject(rmiView, false);
	}

	/**
	 * @return the rmiView
	 */
	public ClientRMIRemote getRmiView() {
		return rmiView;
	}
}
