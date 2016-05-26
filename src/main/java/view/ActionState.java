/**
 * 
 */
package view;

import java.lang.reflect.Field;
import java.util.List;

import model.actions.AcquirePermit;
import model.actions.BuildEmporiumByKing;
import model.game.BuildingPermit;
import model.game.PermitsDeck;
import model.game.council.Council;
import model.game.council.Councillor;
import model.game.politics.PoliticsCard;
import model.game.topology.City;
import model.game.topology.Region;
/**
 * @author Francesco Vetr�
 *
 */
public class ActionState implements State {
	
	/**
	 * class of the action to be built
	 */
	private Class<?> actionClass;
	/**
	 * the fields of the action
	 */
	private Field[] fields;
	private final View view;
	
	public ActionState(Class<?> actionClass, View view) {
		this.actionClass = actionClass;
		this.fields = this.actionClass.getDeclaredFields();
		this.view = view;
	}

	/* (non-Javadoc)
	 * @see cg2.view.State#display()
	 */
	/**
	 * display the required parameters
	 * the input should be a string separated by a ';'
	 * every field that can be given as an input has its own code
	 * c* -> council number *
	 * bp* -> building permits from game number *
	 * bpo* -> building permits owned by the player number *
	 * * -> the initial of the city
	 * pc* -> politics card number *
	 * ac* -> available councillor number *
	 * r* -> permits deck of the region number r (this must be parsed differently)
	 */
	@Override
	public void display() {
		System.out.println("For the action the required input is: ");	
		for(int i = 0; i < fields.length; i++){
			Class<?> field = fields[i].getType();
			//Council field
			if(field.equals(Council.class) || field.getSuperclass().equals(Council.class))
				if(actionClass.equals(BuildEmporiumByKing.class))
					this.displayKing(view.getPeeker().getKingCouncil());
				else if(actionClass.equals(AcquirePermit.class))
					this.displayRegionalCouncil(view.getPeeker().getRegion());
				else
					this.displayCouncil(view.getPeeker().getRegion(), view.getPeeker().getKingCouncil());
			//BuildingPermit field
			else if(field.equals(BuildingPermit.class))
				if(actionClass.equals(AcquirePermit.class))
					this.displayPermitsGame(view.getPeeker().getRegion());
				else
					this.displayPermits(view.getPeeker().getPlayerPermit(
							view.getPlayerID()));
			//City field
			else if(field.equals(City.class))
				this.displayCities(view.getPeeker().getCities());
			//PoliticsCard field TODO fix
			else if(field.equals(List.class))
				this.displayPolitics(view.getPeeker().getPlayerPolitic(
						view.getPlayerID()));
			//Councillor field
			else if(field.equals(Councillor.class))
				this.displayCouncillor(view.getPeeker().getAvailableCouncillor());
			//Permitsdeck field
			else if(field.equals(PermitsDeck.class))
				this.displayDeck(view.getPeeker().getRegion());
		}
	}
	
	/**
	 * display the regional councils
	 * notation cx stands for council number x and must be used for input
	 * @param region the regions in the game
	 */
	private void displayRegionalCouncil(List<Region> region) {
		int i = 1;
		for(Region r : region){
			System.out.println(r.getName());
			System.out.println("c"+i+" - ");
			for(Councillor c : r.getCouncil().getCouncillors())
				System.out.print(c.toString());
			System.out.println();
			i++;
		}		
	}

	/**
	 * display only the king's council with the notation kc
	 * @param kingCouncil
	 */
	private void displayKing(Council kingCouncil) {
		System.out.println("King's Council");
		System.out.println("kc"+" - ");
		for(Councillor c : kingCouncil.getCouncillors())
			System.out.print(c.toString());
	}

	/**
	 * display the r* for identifying the region to change permits
	 * @param decks the decks held by each region
	 */
	private void displayDeck(List<Region> regions) {
		int i = 1;
		for(Region r : regions){
			System.out.println("r"+i+" - "+r.getName());
			System.out.println(r.getPermitsDeck().getFaceUpPermits().toString());
			i++;
		}
	}

	/**
	 * display the councillor's color with code ac*
	 * @param availableCouncillor the color of councillor not in a council 
	 * 								in the game
	 */
	private void displayCouncillor(List<Councillor> availableCouncillor) {
		int i = 1;
		for(Councillor c : availableCouncillor){
			System.out.println("ac"+i+" - "+c.toString());
			i++;
		}
	}
	
	/**
	 * display the politics card of the player with the code po*
	 * @param playerPolitic the politics card owned by the player
	 */
	private void displayPolitics(List<PoliticsCard> playerPolitic) {
		int i = 1;
		for(PoliticsCard pc : playerPolitic){
			System.out.println("po"+i+" - "+pc.toString());
			i++;
		}
		
	}

	/**
	 * display the initial of the city as code and the whole city
	 * (no group by region)
	 * @param cities the cities in game
	 */
	private void displayCities(List<City> cities) {
		for(City c : cities)
			System.out.println(c.getFirstChar() + " - " +c.toString());
	}

	/**
	 * display the permits owned by the player with the code bpo*
	 * @param playerPermit only the unused permits owned by the player
	 */
	private void displayPermits(List<BuildingPermit> playerPermit) {
		int i = 1;
		for(BuildingPermit b : playerPermit){
			System.out.println("pe"+i+" - "+b.toString());
			i++;
		}
		
	}

	/**
	 * display the face up building permits as bp*.
	 * this method is used for acquiring a permit
	 * @param regions the regions from the game
	 */
	private void displayPermitsGame(List<Region> regions) {
		int i = 1;
		for(Region r : regions){
			System.out.println(r.getName());
			for(BuildingPermit b : r.getPermitsDeck().getFaceUpPermits()){
				System.out.println("bp"+i+" - "+b.toString());
				i++;
			}
		}
	}

	/**
	 * display the councils in the game
	 * @param list the regions in the game which holds the council
	 * 				  it's used for the name of the region
	 * @param king the king's council
	 */
	private void displayCouncil(List<Region> list, Council king) {
		this.displayRegionalCouncil(list);
		this.displayKing(king);
		
	}
}
