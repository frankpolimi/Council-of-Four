/**
 * 
 */
package server;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import client.ClientRMIRemote;
import client.RMIConnectionHandler;
import controller.Change;
import controller.ErrorChange;
import view.QuitRequest;
import view.Request;
import view.View;

/**
 * @author Francesco Vetrò
 *
 */
public class ServerRMIView extends View implements ServerRMIViewRemote{

	private RMIConnectionHandler client;
	private int ID;
	
	/**
	 * constructor for a RMI view on the server
	 * that communicates with one client
	 * @param client the client RMI that will be associated and
	 * 				will communicate with this view
	 * @throws RemoteException if exports fails
	 */
	public ServerRMIView(ClientRMIRemote client) throws RemoteException{
		UnicastRemoteObject.exportObject(this, 1099);
		this.client = new RMIConnectionHandler(client);
	}

	/**
	 * method for all RMI views on the server
	 * that is called by the client to send a request that the
	 * controller of the game will analyze and eventually perform
	 * this method is delegated to receive the errors occurred
	 * during the application of the action. the errors will be sent 
	 * by the delegated method
	 * @param request the request that will be given to the controller
	 * @throws RemoteException if the call to this method fails
	 */
	@Override
	public void receiveRequest(Request request) throws RemoteException {
		try{
			this.notifyObservers(request);
		}catch (IllegalArgumentException | IllegalStateException e1){
			try {
				this.client.sendToClient(new ErrorChange(e1.getMessage()));
			} catch (IOException e) {
				System.out.println("The client"+this.ID+" has been disconnected and removed in the game model");
				this.notifyObservers(new QuitRequest(this.ID));
			}
		}
	}

	/**
	 * method from View class. 
	 * this method will invoke (since operates via RMI connection)
	 * the method of the client that is delegated to receive a change
	 * from the game. if unable to send will catch a RemoteException 
	 * and will disconnect the player from the game
	 * @param c the change coming from the game which this view
	 * 			is registered to
	 */
	@Override
	public void update(Change c){
		try {
			this.client.sendToClient(c);
		} catch (RemoteException e) {
			try {
				this.client.closeConnection();
			} catch (IOException e1) {
				//e1.printStackTrace();
			}
			System.out.println("The client"+this.ID+" has been disconnected and removed in the game model");
			this.notifyObservers(new QuitRequest(this.ID));
		} catch (IOException e) {
			//e.printStackTrace();
		}
	}
	
	/**
	 * method from View class. 
	 * this method will invoke (since operates via RMI connection)
	 * the method of the client that is delegated to receive a string
	 * from the game. if unable to send will catch a RemoteException 
	 * and will disconnect the player from the game
	 * @param s the string coming from the game which this view is
	 * 			registered to
	 * @throws RemoteException if remote invocation fails
	 */
	public void update(String s) throws RemoteException{
		try {
			this.client.sendToClient(s);
		} catch (RemoteException e) {
			System.out.println("The client"+this.ID+" has been disconnected and removed in the game model");
			throw new RemoteException();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * method to set the ID of the player related to this view
	 * will catch a RemoteException if unable to invoke the remote method
	 * @param iD the id of the game player associated to this view
	 */
	@Override
	public void setID(int iD){
		this.ID = iD;
		try {
			this.client.sendToClient(new Integer(iD));
		} catch (RemoteException e) {
			System.out.println("The client"+this.ID+" has been disconnected and removed in the game model");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @return the view on client's side
	 * 			connected to this view
	 */
	public ClientRMIRemote getClient(){
		return client.getRmiView();
	}
	
	/**
	 * retrieve the name of the client connected
	 * to insert it in the player inside the game
	 * will catch a RemoteException if the remote
	 * invocation fails
	 */
	@Override
	public String getName() {
		try {
			return client.getRmiView().getName();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return "";
	}

	/**
	 * generic method from interface common to 
	 * all views
	 */
	@Override
	public void sendString(String string) throws RemoteException {
		this.update(string);
	}
}
