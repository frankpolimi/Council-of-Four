package model.actions;

import java.util.TimerTask;

import controller.ErrorChange;
import model.game.Game;
import model.game.Player;
import view.EndState;

public class DisconnectionTimer extends TimerTask {

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
		if(game.getMainActionCounter()==1){
			game.decrementMainActionCounter();
		}
		
		SkipAction action=new SkipAction();
		action.takeAction(game);
		game.getPlayers().remove(disconnected);
		game.getDisconnectedPlayers().add(disconnected);
		if(game.getPlayers().size()==1){
			game.notifyObservers(new ErrorChange("Player "+game.getCurrentPlayer().getName()+" - "+game.getCurrentPlayer().getPlayerID()+
					", you are the last online player in this match, so the game is finished and you have won!"));
			game.setGameState(new EndState(game.getCurrentPlayer()));
			game.getTimer().cancel();
		}
		
	}

}
