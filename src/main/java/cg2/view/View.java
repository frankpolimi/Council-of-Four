/**
 * 
 */
package cg2.view;

import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import actions.*;
import cg2.observers.*;
import cg2.game.Game;
import cg2.model.BuildingPermit;


/**
 * @author Emanuele
 *
 */
public class View extends Observable implements Observer {
	
	private final Game game;
	
	public View(Game game) {
		super();
		this.game = game;
		game.registerObserver(this);
	}

	/**
	 * gives the input to the controller
	 * @param command
	 */
	public void input(String command){
		this.notifyObservers(command);
	}
	
	/* (non-Javadoc)
	 * @see cg2.observers.Observer#update()
	 */
	@Override
	public void update() {
		//not used at the moment
	}

	/* (non-Javadoc)
	 * @see cg2.observers.Observer#update(java.lang.Object)
	 */
	@Override
	public <C> void update(C change) {
	}
	
	/* (non-Javadoc)
	 * @see cg2.observers.Observer#update(java.lang.String)
	 */
	/**
	 * communication between the view and the controller
	 * with different cases
	 * @param the string with a communication
	 */
	@Override
	public void update(String communication) {
		if(communication.equals("action_phase"))
			this.selectActionType();
		else if(communication.contains("Bonus"))
			this.selectBonus(communication);
		else
			System.err.println("FATAL ERROR IN COMMUNICATION!");
	}

	/**
	 * selection of the two classes of action a player can perform
	 * @param string 
	 */
	private void selectActionType() {
		Scanner in = new Scanner(System.in);
		
		//as a default move the player will always start with a main action
		String selection = "principale";
		
		boolean condition;
		do{
			condition = false;
			System.out.println("Scegli che tipo di mossa vuoi eseguire (comando)");
			if(game.getMainActionNumber() != 0)
				System.out.println("1 - Principale (principale)\n");
			if(game.getQuickActionNumber() != 0)
				System.out.println("2 - Secondaria (secondaria)\n");
			if(game.getMainActionNumber() == 0)
				System.out.println("3 - Salta mossa secondaria (salta)\n");

			selection = in.nextLine();
			switch(selection){
				case "principale":{
					in.close();
					this.selectAction("main_action");
					break;
				}
				case "secondaria":{
					in.close();
					this.selectAction("quick_action");
					break;
				}
				case "salta":{
					in.close();
					break;
				}
				default:{
					System.err.println("Errore nell'inserimento. Riprova");
					condition = true;
				}
			}
		}while(condition);
	}

	/**
	 * select the main action by a number
	 * (1 - firstAction
	 *  2 - secondAction
	 *  and so on)
	 * @param numberOfActions
	 * @return the message main_action and the number of the action to perform
	 */
	private void selectAction(String type) {
		int ins;
		int lenght;
		if(type.equals("main_action"))
			lenght = this.showMainAction();
		else
			lenght = this.showQuickAction();
		Scanner in = new Scanner(System.in);
		do{
			ins = in.nextInt();
		}while(ins > lenght || ins < 0);
		in.close();
		//TODO select action
	}
	
	private int showMainAction(){
		System.out.println("Inserisci il numero dell'azione che vuoi eseguire");
		Iterator<? extends MainAction> x = game.getMainAction().iterator();
		int l = game.getMainAction().size();
		MainAction a;
		for(int i = 1;x.hasNext(); i++){
			a = x.next();
			if(a.getClass().equals(AcquirePermit.class)){
				System.out.println(i+" - "+
						((AcquirePermit)a).toString());
			}
			else if(a.getClass().equals(ElectCouncillor.class))
				System.out.println(i+" - "+
						((ElectCouncillor)a).toString());
			else if(a.getClass().equals(BuildEmproriumByPermit.class))
				System.out.println(i+" - "+
						((BuildEmproriumByPermit)a).toString());
			//TODO complete
		}
		return l;
	}
	
	private int showQuickAction(){
		System.out.println("Inserisci il numero dell'azione che vuoi eseguire");
		Iterator<? extends QuickAction> x = game.getQuickAction().iterator();
		int l = game.getQuickAction().size();
		QuickAction a;
		for(int i = 1;x.hasNext(); i++){
			a = x.next();
			if(a.getClass().equals(ElectCouncillorByAssistant.class))
				System.out.println(i+" - "+
						((ElectCouncillorByAssistant)a).toString());
			else if(a.getClass().equals(EnageAssistant.class))
				System.out.println(i+" - "+
						((EnageAssistant)a).toString());
			else if(a.getClass().equals(ShuffleFaceUpPermits.class))
				System.out.println(i+" - "+
						((ShuffleFaceUpPermits)a).toString());
			//TODO complete
		}
		return l;
	}
	
	private <T> void selectBonus(String type) {
		System.out.println("Inserisci il numero del bonus che vuoi ricevere");
		List<T> tmp = null;
		if(type.equals("ReuseTileBonus")){
			List<T> buildingPermits = (List<T>) game.getPlayers().get(game.getCurrentPlayer()).getStatus().getBuildingPermits();
			tmp = buildingPermits;
			for(T b: tmp)
				System.out.println(tmp.indexOf(b)+" - "+
						((BuildingPermit) b).displayBonus());
			//TODO
		}
		
		Scanner in = new Scanner(System.in);
		int ins;
		do{
			ins = in.nextInt();
		}while(ins < 0 || ins > tmp.size());
		in.close();
		this.update(tmp.get(ins));;
	}
	
	
	

}
