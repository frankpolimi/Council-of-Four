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
	
	/*
	 *
	 * private final Set<MainAction> mainActions;
	 * private final Set<QuickAction> quickActions;
	 */

	/**
	 * constructor of the controller. will initialize the list of actions
	 * with an instance of the specific actions.
	 * @param view a client's view that want to interact with the game
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
		//qua update notificati senza comandi
	}
	
	@Override
	public void update(Request request) throws IllegalArgumentException, IllegalStateException{
		
		if(game.getGameState().getClass().equals(EndState.class)) return;
		
		if(request.getClass().equals(QuitRequest.class)&&game.getGameState()!=null){
			QuitRequest disconnection=(QuitRequest)request;
			disconnection.disconnect(game);
		}
		
		//EFFETTUARE CONTROLLO SU GIOCATORE CORRENTE!!
		if(request.getID()!=game.getCurrentPlayer().getPlayerID()){
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
		}else if(request instanceof MarketRequest){
			MarketRequest<?> action= (MarketRequest<?>)request;
			if(game.getGameState().getClass().equals(MarketSellingState.class))
				game.getMarket().addProduct(action.getObject());
			else if(game.getGameState().getClass().equals(MarketBuyingState.class))
				game.getMarket().buyElement(game.getCurrentPlayer(), action.getObject());
			game.notifyObserver(request.getID(), new ModelChange(game));
		}else if(request instanceof BonusRequest){
			BonusRequest action=(BonusRequest)request;
			for(Bonus b:action.getBonusList()){
				b.update(game);
			}
		}else if(request instanceof PermitsRequest){
			PermitsRequest action=(PermitsRequest)request;
			current.addBuildingPermit(action.getPermits().get(0));
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
