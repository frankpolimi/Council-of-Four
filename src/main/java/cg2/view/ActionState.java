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
					System.out.println(view.getPeeker().getPlayerPermit(
							view.getPlayerID()));
			else if(field.getClass().equals(City.class))
				System.out.println(view.getPeeker().getCities());
			else if(field.getClass().equals(List.class))
				System.out.println(view.getPeeker().getPlayerPolitic(
						view.getPlayerID()));
			else if(field.getClass().equals(Councillor.class))
				System.out.println(view.getPeeker().getAvailableCouncillor());
		}
	}
	
	private void displayPermits(Set<Region> regions) {
		for(Region r : regions){
			int i = 0;
			System.out.println(r.getName());
			for(BuildingPermit b : r.getPermitsDeck().getFaceUpPermits()){
				System.out.println(i+" - "+b.toString());
				i++;
			}
		}
	}

	private void displayCouncil(Set<Region> regions, Council king) {
		for(Region r : regions){
			System.out.println(r.getName());
			System.out.println(r.getCouncil().toString());
		}
		System.out.println("King's Council");
		System.out.println(king.getCouncillors().toString());
		
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
