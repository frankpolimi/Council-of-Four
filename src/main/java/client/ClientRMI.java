/**
 * 
 */
package client;

import java.io.IOException;
import java.io.Serializable;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.jdom2.JDOMException;
import model.game.Game;
import server.ServerRMIRegistrationRemote;
import view.EndState;
import view.QuitRequest;
import view.Request;

/**
 * @author Francesco Vetrò
 *
 */
public class ClientRMI extends UnicastRemoteObject implements Serializable, ClientInterface{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2606594429637762781L;

	private final String host;
	private final int rmiPort;
	
	private RMIConnectionHandler handler;
	private Request request;
	private Registry registry;
	
	private ClientViewInterface view;
	
	private String gameName;
	
	private Game game;
	
	
	public ClientRMI(String host, int rmiPort, ClientViewInterface view) throws NotBoundException, JDOMException, IOException{
		super();
		this.host = host;
		this.rmiPort = rmiPort;
		this.view = view;
		this.gameName= "game";
	}
	
	@Override
	public void runClient(String name) throws NotBoundException, JDOMException, IOException, AlreadyBoundException{
		registry = LocateRegistry.getRegistry(host, rmiPort);
		
		ServerRMIRegistrationRemote serverRegistration = (ServerRMIRegistrationRemote) 
				registry.lookup(this.gameName);
		ClientRMIRemote viewtmp = new ClientRMIView(name, serverRegistration, view);
		handler = new RMIConnectionHandler(viewtmp);
		this.game=viewtmp.getMemoryContainer().getGameRef();
		System.out.println(this.game);
		this.view.setGame(this.game);
		this.view.setId(viewtmp.getID());
		this.view.setMemoryContainer(viewtmp.getMemoryContainer());
		ExecutorService executors=Executors.newFixedThreadPool(1);
		executors.submit(new ClientOutHandlerSocket(handler, viewtmp.getMemoryContainer(), view));
	}
	
	@Override
	public void startClient() throws NotBoundException, JDOMException, IOException, AlreadyBoundException{
		Scanner stdin = new Scanner(System.in);	
		System.out.println("Insert your name:");
		String name = stdin.nextLine();
		stdin.close();
		this.runClient(name);
		
	}
	
	@Override
	public ClientViewInterface getClientView(){
		return this.view;
	}
	
}
