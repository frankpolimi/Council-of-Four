/**
 * 
 */
package cg2.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import actions.*;
import bonus.Bonus;
import cg2.game.Game;
import cg2.model.BuildingPermit;
import cg2.observers.Observer;
import cg2.player.Player;
import cg2.view.MarketState;
import cg2.view.StartState;
import cg2.view.View;

/**
 * @author Emanuele Ricciardelli
 *
 */
public class Controller implements Observer<Change>{
	
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
	public void update(Change change){
		//TURNO AZIONE
		boolean log=true;
		Player current=game.getCurrentPlayer();
		
		if(change instanceof ActionChange){
			ActionChange action = (ActionChange)change;//CASTING SBAGLIATO!
			log=action.getAction().takeAction(game);
		}else if(change instanceof MarketChange){
			MarketChange action= (MarketChange)change;
			try {
				game.getMarket().addProduct(action.getMarketObject());
			} catch (NotEnoughResources e) {
				log=false;
			}
			//log
		}else if(change instanceof BonusChange){
			BonusChange action=(BonusChange)change;
			for(Bonus b:action.getBonusList()){
				b.update(game);
			}
		}else{
			PermitsChange action=(PermitsChange)change;
			current.addBuildingPermit(action.getPermits().get(0));
		}
		
		
		//IL RAGIONAMENTO è SEMPRE A TURNI: SE SIAMO IN FASE NORMALE SI APPLICA L'AZIONE, SE NO SI INSERISCE L'OGGETTO NEL MARKET.
		if(!log){
			throw new IllegalStateException("The action is not valid");
		}
		
		
		if(current.getRemainingEmporiums()==0){
			this.finalRound=true;
		}

		if(game.getMainActionCounter()==0&&game.getQuickActionCounter()==0){
				Player nextPlayer;
				int currentIndex=game.getPlayers().indexOf(current);
				
				if(currentIndex+1==game.getPlayers().size()){
					nextPlayer=game.getPlayers().get(0);
					game.setCurrentPlayer(nextPlayer);
					if(!this.finalRound){
						if(change instanceof ActionChange)
							game.notifyObservers(new StateChange(new MarketState()));
						else if(change instanceof MarketChange)
							game.notifyObservers(new StateChange(new StartState()));
					}
				}else{
					nextPlayer=game.getPlayers().get(currentIndex+1);
					game.setCurrentPlayer(nextPlayer);
				}
				
				if(this.finalRound){
					game.getPlayers().remove(current);
					this.theLastPlayers.add(current);
				}
								
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
			game.notifyObservers(new ModelChange(game));
		
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

	@Override
	public void update(String communication) {
		if(communication.equals("salta")){
			game.decrementQuickActionCounter(); //Messo decrement perche' non sara' mai maggiore di 1 e quindi fare set 0 e' la stessa cosa
			System.out.println(game.getCurrentPlayer().getName()
					+" non puoi piï¿½ eseguire azioni secondarie per questo turno");
		}
		else if(communication.equals("main action")){
			
		}
		else if(communication.contains("quick action")){
			
		}
	}
}
