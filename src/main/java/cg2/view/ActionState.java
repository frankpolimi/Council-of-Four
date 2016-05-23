/**
 * 
 */
package cg2.view;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import actions.AcquirePermit;
import cg2.model.BuildingPermit;
import cg2.model.City;
import cg2.model.PermitsDeck;
import council.Council;
import council.Councillor;
import politics.PoliticsCard;
import topology.Region;
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
	PropertyDescriptor[] descriptor;
	private final View view;
	
	public ActionState(Class<?> actionClass, View view) {
		this.actionClass = actionClass;
		BeanInfo info = null;
		try {
			info = Introspector.getBeanInfo(actionClass);
		} catch (IntrospectionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		descriptor = info.getPropertyDescriptors();
		this.fields = this.actionClass.getClass().getDeclaredFields();
		this.view = view;
	}

	/**
	 * method to parse the string and put them in the action field
	 * @throws IntrospectionException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public void createAction(View view, String input) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException
	{
		Object[] parameters=/*this.actionParser(input);*/input.split(" ");
		int counter=parameters.length;
		BeanInfo info= Introspector.getBeanInfo(actionClass);
		PropertyDescriptor[] descriptor=info.getPropertyDescriptors();
		for(PropertyDescriptor p:descriptor)
		{
			p.getWriteMethod().invoke(p, parameters[counter--]);
		}
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
			Class<?> field = descriptor[i].getClass();
			if(field.getClass().equals(Council.class))
				this.displayCouncil(view.getPeeker().getRegion(), view.getPeeker().getKingCouncil());
			else if(field.getClass().equals(BuildingPermit.class))
				if(actionClass.equals(AcquirePermit.class))
					this.displayPermits(view.getPeeker().getRegion());
				else
					this.displayPermits(view.getPeeker().getPlayerPermit(
							view.getPlayerID()));
			else if(field.getClass().equals(City.class))
				this.displayCities(view.getPeeker().getCities());
			else if(field.getClass().equals(List.class))
				this.displayPolitics(view.getPeeker().getPlayerPolitic(
						view.getPlayerID()));
			else if(field.getClass().equals(Councillor.class))
				this.displayCouncillor(view.getPeeker().getAvailableCouncillor());
			else if(field.getClass().equals(PermitsDeck.class))
				this.displayDeck(view.getPeeker().getRegion());
		}
	}
	
	/**
	 * display the r* for identifying the region to change permits
	 * @param decks the decks held by each region
	 */
	private void displayDeck(Set<Region> regions) {
		int i = 0;
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
		List<Councillor> copy = new ArrayList<Councillor>();
		copy.addAll(availableCouncillor);
		int i = 0;
		for(Councillor c : copy){
			System.out.println("ac"+i+" - "+c.toString());
			copy.removeIf(Predicate.isEqual(c.getColor()));
			i++;
		}
	}
	
	/**
	 * display the politics card of the player with the code pc*
	 * @param playerPolitic the politics card owned by the player
	 */
	private void displayPolitics(List<PoliticsCard> playerPolitic) {
		int i = 0;
		for(PoliticsCard pc : playerPolitic){
			System.out.println("pc"+i+" - "+pc.toString());
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
		int i = 0;
		for(BuildingPermit b : playerPermit){
			System.out.println("bpo"+i+" - "+b.toString());
			i++;
		}
		
	}

	/**
	 * display the face up building permits as bp*.
	 * this method is used for acquiring a permit
	 * @param regions the regions from the game
	 */
	private void displayPermits(Set<Region> regions) {
		for(Region r : regions){
			int i = 0;
			System.out.println(r.getName());
			for(BuildingPermit b : r.getPermitsDeck().getFaceUpPermits()){
				System.out.println("bp"+i+" - "+b.toString());
				i++;
			}
		}
	}

	/**
	 * display the councils in the game
	 * notation cx stands for council number x and must be used for input
	 * @param regions the regions in the game which holds the council
	 * 				  it's used for the name of the region
	 * @param king the king's council
	 */
	private void displayCouncil(Set<Region> regions, Council king) {
		int i = 0;
		for(Region r : regions){
			System.out.println(r.getName());
			System.out.println("c"+i+" - "+r.getCouncil().toString());
			i++;
		}
		System.out.println("King's Council");
		System.out.println("c"+i+" - "+king.getCouncillors().toString());
		
	}

	/**
	 * insert method for parsing using string tokenizer &
	 * use the string input from the method doAction
	 * @throws IntrospectionException 
	 */
	
	public void/*Object[]*/ actionParser(String parameters) throws IntrospectionException
	{
		BeanInfo info= Introspector.getBeanInfo(actionClass);
		PropertyDescriptor[] descriptor=info.getPropertyDescriptors();
		for(PropertyDescriptor p:descriptor)
		{
			p.getPropertyType(); //Qua dovrei fare la stessa cosa col peek model? No, e' un suicidio, qua c'e' qualcosa di sbagliato
		}
	}
	

}
