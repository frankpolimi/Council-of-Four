package server;

import java.io.IOException;
import java.net.SocketException;
import java.rmi.NoSuchObjectException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import org.jdom2.JDOMException;

import controller.Controller;
import model.game.Game;
import model.game.Player;
import view.View;

import java.util.*;

public class StartingGameTimerTask extends TimerTask {

	private List<Player> oneRoomLobby;
	private Map<Player, View> playersView;
	private Server server;
	
	
	/**
	 * constructor for a timer task that allows the server
	 * to recognize when a game is ready to be played 
	 * @param server the server which is applied the timer
	 * @param oneRoomLobby the players that will participate to the game
	 * @param playersView the correspondence of the player participating in the game
	 * 						with their own view
	 */
	public StartingGameTimerTask(Server server,List<Player> oneRoomLobby, Map<Player, View> playersView) {
		super();
		this.oneRoomLobby = oneRoomLobby;
		this.playersView = playersView;
		this.server=server;
	}

	/**
	 * this method will perform different controls on the status 
	 * of the server in order to start the game.
	 * first of all will check if at least two players are connected
	 * if so, this method will send an empty string to check if 
	 * the client is still connected (like a ping to test if connection is 
	 * available)
	 * if SocketException or RemoteException are caught due to failing of
	 * test said before, the player is removed and so the view is unregistered
	 * from the game.
	 * in the end the method will check if there are still at least two players left
	 * so that the game can start. if response is positive, the server will setup a new
	 * game and will be waiting for new players to join
	 * as last operation the timer is reseted 
	 */
	@Override
	public synchronized void run() {

		if(oneRoomLobby.size()>=2){
			int i=0;
			while(i<oneRoomLobby.size()){
				Player player=oneRoomLobby.get(i);
				View x = playersView.get(player);
				try{
					if(x.getClass().equals(ServerSocketView.class))
						((ServerSocketView)x).getHandler().sendToClient("");
					else
						((ServerRMIViewRemote)x).sendString("");
					i++;
				}catch(SocketException e){
					oneRoomLobby.remove(player);
					playersView.remove(player);
					
				}catch(RemoteException e){
					try {
						server.getGame().unregisterObserver(x);
						x.unregisterObserver(server.getController());
						UnicastRemoteObject.unexportObject(((ServerRMIViewRemote)x), true);
					} catch (NoSuchObjectException e1) {
						e1.printStackTrace();
					}
					oneRoomLobby.remove(player);
					playersView.remove(player);
				}catch(IOException io){
				}
				
			}
			
			if(oneRoomLobby.size()>=2){
				try {
					server.resetGame();
				} catch (JDOMException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				oneRoomLobby.clear();
				playersView.clear();
			}
			server.resetTimer();
		}
		
	}

}
