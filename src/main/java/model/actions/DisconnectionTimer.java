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
	
	/**
	 * constructor for a timer that will 
	 * be operating over a given game
	 * the timer has the function to prevent the game 
	 * from starving because of one player not performing
	 * an action. if this timer expires, the player whose turn is
	 * will be disconnected from the game, but its points will be
	 * processed at the the end of the game 
	 * @param game the specific game that the timer
	 * 			must keep time of
	 */
	public DisconnectionTimer(Game game) {
		this.game=game;
	}
	
	/**
	 * once the timer expires, it disconnects the player whose
	 * turn is in the game
	 */
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
