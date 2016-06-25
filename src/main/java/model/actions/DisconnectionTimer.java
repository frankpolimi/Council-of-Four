package model.actions;

import java.io.Serializable;
import java.util.TimerTask;

import controller.ErrorChange;
import model.game.Game;
import model.game.Player;
import view.QuitRequest;

public class DisconnectionTimer extends TimerTask implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2534566738093383572L;
	private final Game game;
	
	public DisconnectionTimer(Game game) {
		this.game=game;
	}
	
	@Override
	public void run() {
		System.out.println("Disconnection Timer TIMEOUT");
		Player disconnected=game.getCurrentPlayer();
		game.notifyObservers(new ErrorChange("The player "+disconnected.getName()+" - "+disconnected.getPlayerID()+
				" has been disconnected because of inactivity"));
		QuitRequest quit=new QuitRequest(disconnected.getPlayerID());
		quit.disconnect(game);
	}

}
