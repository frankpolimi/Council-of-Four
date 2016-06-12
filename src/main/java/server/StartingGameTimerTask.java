package server;

import java.io.IOException;
import java.net.SocketException;

import org.jdom2.JDOMException;

import controller.Controller;
import model.game.Game;
import model.game.Player;
import view.View;

import java.util.*;

public class StartingGameTimerTask extends TimerTask {

	private List<Player> oneRoomLobby;
	private Map<Player, View> playersView;
	private Game game;
	private Controller controller;
	private Timer timer;
	
	

	public StartingGameTimerTask(List<Player> oneRoomLobby, Map<Player, View> playersView, Game game,
			Controller controller, Timer timer) {
		super();
		this.oneRoomLobby = oneRoomLobby;
		this.playersView = playersView;
		this.game = game;
		this.controller = controller;
		this.timer = timer;
	}



	@Override
	public void run() {

		if(oneRoomLobby.size()>=2){
			int i=0;
			while(i<oneRoomLobby.size()){
				Player player=oneRoomLobby.get(i);
				try{
					View x = playersView.get(player);
					if(x.getClass().equals(ServerSocketView.class))
						((ServerSocketView)x).getSocketOut().writeObject("");
					else
						((ServerRMIViewRemote)x).sendString("");
					i++;
				}catch(SocketException e){
					oneRoomLobby.remove(player);
					playersView.remove(player);
					
				}catch(IOException io){
				}	
			}
			
			if(oneRoomLobby.size()>=2){
				game.setPlayers(oneRoomLobby);
				game.getPlayers().stream().forEach(System.out::println);
				try {
					game= new Game();
					controller= new Controller(game);
					oneRoomLobby.clear();
					playersView.clear();
				} catch (JDOMException | IOException e) {
					e.printStackTrace();
				}
			}
			timer.cancel();
			timer=null;
		}
		
	}

}
