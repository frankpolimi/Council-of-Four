/**
 * 
 */
package controller;

import model.game.*;
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
	public void update(Request request) {
		//EFFETTUARE CONTROLLO SU GIOCATORE CORRENTE!!
		if(request.getID()!=game.getCurrentPlayer().getPlayerID()){
			throw new IllegalArgumentException("It's not your turn!");
		}
		
		//TURNO AZIONE
		Player current=game.getCurrentPlayer();
		if(request.getClass().equals(ActionRequest.class)){
			ActionRequest action = (ActionRequest)request;
			try{
				action.getAction().takeAction(game);
			}catch(IllegalArgumentException | IllegalStateException e1){
				game.notifyObserver(current.getPlayerID(), new ErrorChange(e1));
			}
				
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
