/**
 * 
 */
package client;

import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import org.jdom2.JDOMException;

import controller.BonusChange;
import controller.Change;
import controller.ErrorChange;
import controller.ModelChange;
import controller.PermitsChange;
import controller.StateChange;
import server.ServerRMIViewRemote;
import view.LocalStorage;
import view.State;

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
	
	private String name = "game";
	
	public ClientRMI() throws RemoteException{
		super();
	}
	
	public void startClient() throws NotBoundException, JDOMException, IOException{
		Registry registry = LocateRegistry.getRegistry(HOST, RMIPORT);
		
		ServerRMIViewRemote serverView = (ServerRMIViewRemote) registry.lookup(name);
		
		Scanner stdIn = new Scanner(System.in);
		
		System.out.println("Insert your name:");
		String nome = stdIn.nextLine();
		
		ClientRMIView rmiView = new ClientRMIView(nome);
		serverView.registerClient(rmiView);
		/*
		 * da qui faccio partire la viewCLI per la selezione 
		 * degli input e l'invio della request
		 */
		
	}
}
