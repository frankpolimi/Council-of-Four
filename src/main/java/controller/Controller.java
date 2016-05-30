/**
 * 
 */
package controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

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
	private boolean finalRound;
	private List<Player> theLastPlayers;
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
	public Controller(View view, Game game) {
		super();
		this.game = game;
		view.registerObserver(this);
		this.finalRound=false;
		this.theLastPlayers=new ArrayList<>();
		/*
		this.mainActions = new HashSet<MainAction>();
		this.quickActions = new HashSet<QuickAction>();
		this.initMainAction();
		this.initQuickAction();
		*/
	}

	/*
	private void initMainAction(){
		this.mainActions.add(new AcquirePermit());
		this.mainActions.add(new BuildEmporiumByKing());
		this.mainActions.add(new ElectCouncillor());
		this.mainActions.add(new BuildEmproriumByPermit());
	}
	*/
	
	/*
	private void initQuickAction() {
		this.quickActions.add(new EngageAssistant());
		this.quickActions.add(new ChangeFaceUpPermits());
		this.quickActions.add(new ElectCouncillorByAssistant());
		this.quickActions.add(new ExtraMainAction());
	}
	*/
	
	/* (non-Javadoc)
	 * @see cg2.observers.Observer#update()
	 */
	@Override
	public void update() {
		//qua update notificati senza comandi
	}
	
	@Override
	public void update(Request request){
		//EFFETTUARE CONTROLLO SU GIOCATORE CORRENTE!!
		if(request.getID()!=game.getCurrentPlayer().getPlayerID()){
			throw new IllegalArgumentException("It's not your turn!");
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
			game.setGameState(new EndState());
		}
				
		if(game.getMainActionCounter()==0&&game.getQuickActionCounter()==0){
				SkipAction performForced=new SkipAction();
				performForced.takeAction(game);
				/*Player nextPlayer;
				int currentIndex=game.getPlayers().indexOf(current);
				
				if(currentIndex+1==game.getPlayers().size()){
					nextPlayer=game.getPlayers().get(0);
					game.setCurrentPlayer(nextPlayer);
					if(!this.finalRound){
						if(request instanceof ActionRequest)
							game.notifyObservers();
						else if(request instanceof MarketRequest)
							game.notifyObservers(new StateChange(new MarketState()));
					}
				}else{
					nextPlayer=game.getPlayers().get(currentIndex+1);
					game.setCurrentPlayer(nextPlayer);
				}*/
				
				
								
			}

			//gestione fine turno non fatta dopo modifiche.
			if(game.getGameState().getClass().equals(EndState.class)){
				game.getPlayers().remove(current);
				this.theLastPlayers.add(current);
			}
			
			
			if(game.getPlayers().size()==0){
				try {
					game.endOfTheGame(this.theLastPlayers);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (NoSuchMethodException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SecurityException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
		//game.notifyObservers(new ModelChange(game));//voglio metterlo nelle azioni
		
	}

	/* (non-Javadoc)
	 * @see cg2.observers.Observer#update(java.lang.Object)
	 */
	/*
	@Override
	public void update(C change) {
		if(change.getClass().equals(BuildingPermit.class)){
			BuildingPermit p = ((BuildingPermit)change);
			if(this.checkInPlayer(p))
				p.applyBonus(game);
			else
				game.getCurrentPlayer().addBuildingPermit(p);
		}
		else if(change.getClass().equals(City.class))
			((City)change).applyBonus(game);
	}*/

	private boolean checkInPlayer(BuildingPermit p) {
		List<BuildingPermit> buildingPermits = new ArrayList<BuildingPermit>();
		buildingPermits.addAll(
				game.getCurrentPlayer().getBuildingPermits());
		buildingPermits.addAll(
				game.getCurrentPlayer().getUsedBuildingPermits());
		return buildingPermits.contains(p);
	}

	


}
