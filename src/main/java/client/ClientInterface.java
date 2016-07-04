package client;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.Scanner;

import org.jdom2.JDOMException;

public interface ClientInterface {
	public void runClient(String name) throws IOException, NotBoundException, JDOMException, AlreadyBoundException;
	public default void startClient() throws IOException, NotBoundException, JDOMException, AlreadyBoundException{
		@SuppressWarnings("resource")
		Scanner stdin = new Scanner(System.in);	
		System.out.println("Insert your name:");
		String name = stdin.nextLine();
		this.runClient(name);
		/*CLIChatHandler chatHandler=new CLIChatHandler(this.getConnectionHandler(), (ClientView)this.getClientView(), name);
		((ClientView)this.getClientView()).setChatHandler(chatHandler);*/
	}
	public ClientViewInterface getClientView();
	public ConnectionHandler getConnectionHandler();
}
