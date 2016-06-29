package client;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;

import org.jdom2.JDOMException;

public interface ClientInterface {
	public void runClient(String name) throws IOException, NotBoundException, JDOMException, AlreadyBoundException;
	public void startClient() throws IOException, NotBoundException, JDOMException, AlreadyBoundException;
	public ClientViewInterface getClientView();
	public ConnectionHandler getConnectionHandler();
}
