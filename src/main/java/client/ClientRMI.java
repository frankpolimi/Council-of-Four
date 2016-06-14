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
import org.jdom2.JDOMException;
import model.actions.SkipAction;
import model.game.Game;
import server.ServerRMIRegistrationRemote;
import view.ActionRequest;
import view.ClientView;
import view.EndState;
import view.MarketBuyingState;
import view.MarketSellingState;
import view.QuitRequest;
import view.Request;
import view.StartState;

/**
 * @author Francesco Vetrò
 *
 */
public class ClientRMI extends UnicastRemoteObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2606594429637762781L;

	private final String host;
	private final int rmiPort;
	
	private RMIConnectionHandler handler;
	private Request request;
	private Registry registry;
	
	private String name = "game";
	
	private Game game;
	
	
	public ClientRMI(String host, int rmiPort) throws NotBoundException, JDOMException, IOException{
		super();
		this.host = host;
		this.rmiPort = rmiPort;
	}
	
	public void startClient() throws NotBoundException, JDOMException, IOException, AlreadyBoundException{
		registry = LocateRegistry.getRegistry(host, rmiPort);
		
		ServerRMIRegistrationRemote serverRegistration = (ServerRMIRegistrationRemote) 
				registry.lookup(name);
		
		Scanner stdin = new Scanner(System.in);
		
		System.out.println("Insert your name:");
		String nome = stdin.nextLine();
		
		ClientRMIRemote viewtmp = new ClientRMIView(nome, serverRegistration);
		handler = new RMIConnectionHandler(viewtmp);
		
		boolean isUpdated;
		
		while(handler.getRmiView().getMemoryContainer().getGameRef()==null);
		
		while(true){
			synchronized (handler.getRmiView().getMemoryContainer()) {
				game = handler.getRmiView().getMemoryContainer().getGameRef();
			}
			isUpdated = handler.getRmiView().getMemoryContainer().isUpdated();
			
			if(game.getGameState()!=null&&
					isUpdated&&
					game.getCurrentPlayer().getPlayerID()==handler.getRmiView().getID()){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				ClientView view = new ClientView(game, handler.getRmiView().getMemoryContainer(), handler.getRmiView().getID());
				request = view.start();
				if(request != null){
					try {
						if(!handler.getRmiView().getMemoryContainer().getGameRef().getGameState().getClass().equals(EndState.class)){
							handler.sendToServer(request);
							isUpdated = false;
						}
					} catch (IOException e) {
						if(e.getMessage().equals("Socket closed"))
							System.err.println("THE GAME IS FINISHED, BYE BYE");
						break;
					} catch (IllegalArgumentException | IllegalStateException c){
						System.out.println("Error in performing action: "+c.getMessage());
					}
					if(request.getClass().equals(QuitRequest.class)){
						try {
							handler.closeConnection();
							System.err.println("THE GAME IS FINISHED, BYE BYE");
							break;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}		
	}
}
