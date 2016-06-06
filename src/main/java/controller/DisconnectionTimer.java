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
		game.notifyObserver(game.getCurrentPlayer().getPlayerID(), new ErrorChange("YOUR TIME IS FINISHED, YOU HAVE BEEN DISCONNECTED"));
		Player disconnected=game.getCurrentPlayer();
		SkipAction action=new SkipAction();
		action.takeAction(game);
		game.getPlayers().remove(disconnected);
		game.getDisconnectedPlayers().add(disconnected);
	}

}
