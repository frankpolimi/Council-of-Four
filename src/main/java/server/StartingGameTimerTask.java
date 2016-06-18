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
	
	

	public StartingGameTimerTask(Server server,List<Player> oneRoomLobby, Map<Player, View> playersView) {
		super();
		this.oneRoomLobby = oneRoomLobby;
		this.playersView = playersView;
		this.server=server;
	}



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
