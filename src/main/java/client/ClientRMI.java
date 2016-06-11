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
import server.ServerRMIViewRemote;
import view.ActionRequest;
import view.ClientView;
import view.EndState;
import view.LocalStorage;
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

	private final static String HOST = "127.0.0.1";
	private final static int RMIPORT = 1099;
	
	ClientRMIView rmiView;
	Request request;
	Registry registry;
	
	private String name = "game";
	
	private Game game;
	
	
	public ClientRMI() throws NotBoundException, JDOMException, IOException{
		super();
	}
	
	public void startClient() throws NotBoundException, JDOMException, IOException, AlreadyBoundException{
		registry = LocateRegistry.getRegistry(HOST, RMIPORT);
		
		ServerRMIRegistrationRemote serverRegistration = (ServerRMIRegistrationRemote) 
				registry.lookup(name);
		
		Scanner stdin = new Scanner(System.in);
		
		System.out.println("Insert your name:");
		String nome = stdin.nextLine();
		
		rmiView = new ClientRMIView(nome, serverRegistration);
		
		boolean isUpdated;
		
		while(true){
			synchronized (rmiView.getMemoryContainer()) {
				game = rmiView.getMemoryContainer().getGameRef();
			}
			isUpdated = rmiView.getMemoryContainer().isUpdated();
			
			if(game.getGameState()!=null&&isUpdated&&game.getCurrentPlayer().getPlayerID()==rmiView.getID()){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				
				String inputLine = this.start(stdin);
				if(inputLine.equals("")){
					try {
						if(!rmiView.getMemoryContainer().getGameRef().getGameState().getClass().equals(EndState.class)){
							rmiView.sendRequestToServerView(request);
							isUpdated = false;
						}
					} catch (IOException e) {
						if(e.getMessage().equals("Socket closed"))
						System.err.println("THE GAME IS FINISHED, BYE BYE");
						break;
					} catch (IllegalArgumentException | IllegalStateException c){
						System.out.println("Error in performing action: "+c.getMessage());
					}
				}else if(inputLine.equals("quit")){
					System.out.println("Are you sure? Type 'YES' is you agree, otherwise type anything else");
					Scanner scanner=new Scanner(System.in);
					if(scanner.nextLine().equalsIgnoreCase("yes")){
						System.err.println("You have been disconnected");
						try {
							rmiView.sendRequestToServerView(new QuitRequest(rmiView.getID()));
							registry.unbind(name);
							break;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					scanner.close();
				}
			}
		}		
	}

	private String start(Scanner stdin) throws RemoteException, NotBoundException {
		int actionType;
		ClientView view = new ClientView(game, rmiView.getMemoryContainer(), rmiView.getID());
		if(game.isLastTurn())
			System.err.println("THIS IS YOUR LAST TURN");
		if(this.game.getGameState().getClass().equals(StartState.class)){
			if(!rmiView.getMemoryContainer().getBonus().isEmpty())
				request = view.bonus(stdin);
			else if(!rmiView.getMemoryContainer().getPermits().isEmpty())
				request = view.permit(stdin);
			else{	
				game.getGameState().display();
				actionType= view.selector(1, 4, stdin);
				switch (actionType) {
				case 1:
					request = view.mainAction(stdin);
					break;
				case 2:
					request = view.quickAction(stdin);
					break;
				case 3:
					request = new ActionRequest(new SkipAction(), rmiView.getID());
					break;
				case 4:
					return "quit";
				}
				if(request == null)
					return "impossible";
				return "";
			}
		}
		else if(game.getGameState().getClass().equals(MarketSellingState.class)){
			game.getGameState().display();
			actionType = view.selector(1, 3, stdin);
			switch(actionType){
			case 1:
				request = view.addProduct(stdin);
				break;
			case 2:
				request = new ActionRequest(new SkipAction(), rmiView.getID());
				break;
			case 3:
				return "quit";
			}
			return "";
		}
		else if(game.getGameState().getClass().equals(MarketBuyingState.class)){
			game.getGameState().display();
			actionType = view.selector(1, 3, stdin);
			switch(actionType){
			case 1:
				request = view.buyProducts(stdin);
				break;
			case 2: 
				request = new ActionRequest(new SkipAction(), rmiView.getID());
				break;
			case 3:
				return "quit";
			}
			return "";
		}else if(game.getGameState().getClass().equals(EndState.class)){
			game.getGameState().display();
			try {
				registry.unbind(name);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return "";
		}
		return "not ready";
	}

}
