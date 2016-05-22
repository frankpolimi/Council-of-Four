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

import actions.Action;
import cg2.model.BuildingPermit;
import council.Council;
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
	private Action action;
	private final PeekModel peeker;
	
	public ActionState(Class<?> actionClass, PeekModel peeker) {
		this.actionClass = actionClass;
		this.fields = this.actionClass.getClass().getDeclaredFields();
		this.peeker = peeker;
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
	 * TODO move into ActionState
	 * display the required parameters
	 * the input should be a string separated by a blank
	 * TODO should display the status of the various parameters in game
	 * e.g. 
	 * required input is a council
	 * this method will display the status of all 4 councils 
	 */
	@Override
	public void display() {
		System.out.println("For the action the required input is: ");
		for(int i = 0; i < fields.length; i++){
			Class<?> field = fields[i].getType();
			if(field.getClass().equals(Council.class))
				peeker.getCouncils();
			else if(field.getClass().equals(BuildingPermit.class))
				peeker.getPermits();
		}
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
