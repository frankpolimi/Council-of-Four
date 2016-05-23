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
import java.util.List;
import java.util.Set;

import actions.AcquirePermit;
import cg2.model.BuildingPermit;
import cg2.model.City;
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
	private final View view;
	
	public ActionState(Class<?> actionClass, View view) {
		this.actionClass = actionClass;
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
	 * the input should be a string separated by a blank
	 */
	@Override
	public void display() {
		System.out.println("For the action the required input is: ");
		for(int i = 0; i < fields.length; i++){
			Class<?> field = fields[i].getType();
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
		}
	}
	
	private void displayCouncillor(List<Councillor> availableCouncillor) {
		// TODO Auto-generated method stub
		
	}

	private void displayPolitics(List<PoliticsCard> playerPolitic) {
		// TODO Auto-generated method stub
		
	}

	private void displayCities(List<City> cities) {
		// TODO Auto-generated method stub
		
	}

	private void displayPermits(List<BuildingPermit> playerPermit) {
		// TODO Auto-generated method stub
		
	}

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
