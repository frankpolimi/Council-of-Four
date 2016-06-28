/**
 * 
 */
package controller;

import model.game.*;

import java.util.Timer;

import model.actions.DisconnectionTimer;
import model.actions.SkipAction;
import model.bonus.*;
import model.observers.*;
import view.*;

/**
 * @author Emanuele Ricciardelli
 *
 */
public class Controller implements Observer<Request>{
	
	private final Game game;

	/**
	 * constructor of the controller with the game
	 * where the controller will be operating on
	 * @param game the instance of one specific game 
	 */
	public Controller(Game game) {
		super();
		this.game = game;
	}
	
	/* (non-Javadoc)
	 * @see cg2.observers.Observer#update()
	 */
	@Override
	public void update() {
		//
	}
	
	/**
	 * this method allows the controller to operate and modify the game
	 * that is under his control 
	 * @param request a message coming from the client that must be evaluated
	 * 					and eventually executed
	 */
	@Override
	public void update(Request request) throws IllegalArgumentException, IllegalStateException{
		
		if(game.getGameState().getClass().equals(EndState.class)) return;
		
		if(request.getClass().equals(QuitRequest.class)&&game.getGameState()!=null){
			QuitRequest disconnection=(QuitRequest)request;
			disconnection.disconnect(game);
		}
		
		//EFFETTUARE CONTROLLO SU GIOCATORE CORRENTE!!
		if(request.getID()!=game.getCurrentPlayer().getPlayerID() && !request.getClass().equals(QuitRequest.class)){
			throw new IllegalArgumentException("It's not your turn!");
		}else{
			game.getTimer().cancel();
			game.getTimer().purge();
			game.setTimer(new Timer());
			game.getTimer().schedule(new DisconnectionTimer(game), Game.DISCONNECTION_TIME);
		}
		
		
		
		//TURNO AZIONE
		Player current=game.getCurrentPlayer();
		if(request.getClass().equals(ActionRequest.class)){
			ActionRequest action = (ActionRequest)request;
			action.getAction().takeAction(game);
		}else if(request.getClass().equals(MarketRequest.class)){
			MarketRequest<?> action= (MarketRequest<?>)request;
			if(game.getGameState().getClass().equals(MarketSellingState.class))
				game.getMarket().addProduct(action.getObject());
			else if(game.getGameState().getClass().equals(MarketBuyingState.class))
				game.getMarket().buyElement(game.getCurrentPlayer(), action.getObject());
		}else if(request.getClass().equals(BonusRequest.class)){
			BonusRequest action=(BonusRequest)request;
			for(Bonus b:action.getBonusList()){
				b.update(game);
			}
			game.notifyObservers(new ModelChange(game));
		}else if(request.getClass().equals(PermitsRequest.class)){
			PermitsRequest action=(PermitsRequest)request;
			//current.addBuildingPermit(action.getPermits().get(0));
			PermitsDeck deck=game.getAllPermitsDecks().stream().filter(e->e.equals(action.getPermitsDeck())).findFirst().get();
			deck.givePermit(game, action.getPermit());
			game.notifyObservers(new ModelChange(game));
		}
		
	
		
		if(current.getRemainingEmporiums()==0){
			game.setLastTurnTrue();
		}			
		
		if(game.getMainActionCounter()==0&&game.getQuickActionCounter()==0){
			SkipAction performForced=new SkipAction();
			performForced.takeAction(game);		
		}
		
		if(game.getLastTurnRemainingPlayers()==0){
			try {
				game.endOfTheGame();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
