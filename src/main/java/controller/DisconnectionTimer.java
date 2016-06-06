package controller;

import java.util.TimerTask;

import model.actions.SkipAction;
import model.game.Game;
import model.game.Player;

public class DisconnectionTimer extends TimerTask {

	private final Game game;
	
	public DisconnectionTimer(Game game) {
		this.game=game;
	}
	
	@Override
	public void run() {
		Player disconnected=game.getCurrentPlayer();
		game.notifyObservers(new ErrorChange("The player "+disconnected.getName()+" - "+disconnected.getPlayerID()+
				"has been disconnected because of inactivity"));
		SkipAction action=new SkipAction();
		action.takeAction(game);
		game.getPlayers().remove(disconnected);
		game.getDisconnectedPlayers().add(disconnected);
	}

}
