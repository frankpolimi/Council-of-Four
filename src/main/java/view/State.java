/**
 * 
 */
package view;

import java.io.Serializable;

/*
 *  @author Francesco Vetrò
 *  
 *  class that will represent a state in which a
 *  generic FSM is
 */
public interface State extends Serializable{

	/**
	 * method to visualize the 
	 * state of the FSM
	 */
	public void display();
	
}
