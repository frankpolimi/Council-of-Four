package view;

import model.actions.SkipAction;
import model.game.Game;
import model.game.Player;

public class QuitRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7350257695776860034L;

	/**
	 * constructor for the request to quit the game
	 * that will be sent to the server
	 * @param ID the player id that has sent the request
	 */
	public QuitRequest(int ID) {
		super(ID);
	}
	
	/**
	 * This method disconnects the Player who have the ID passed in the constructor
	 * @param game is the ref of the game in which apply the disconnection
	 * @throws NullPointerException if the game is null
	 */
	public void disconnect(Game game){
		if(game==null){
			throw new NullPointerException("The game passed cannot be null");
		}
		
		Player user=game.getPlayers().stream().filter(e-> e.getPlayerID()==super.getID()).findFirst().get();
		if(user.equals(game.getCurrentPlayer())){
			SkipAction action=new SkipAction();
			if(game.getMainActionCounter()==1)
				game.decrementMainActionCounter();
			action.takeAction(game);
		}
		game.getPlayers().remove(user);
		game.getShuffledPlayers().remove(user);
		game.getDisconnectedPlayers().add(user);
		game.decrementLastRemainingPlayers();
		
	}

}
