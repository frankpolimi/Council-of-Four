package model.bonus;

import java.io.Serializable;

import controller.Change;
import model.bonus.bonusers.Bonuser;
import model.observers.Observable;

public abstract class Bonus extends Observable<Change> implements Bonuser, Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3205604308403400820L;
	/**
	 * @author Francesco Vetr�
	 */
	
	Integer amount;
	
	/**
	 * constructor of a bonus given a certain amount
	 * @param amount the amount of the bonus
	 * @throws NullPointerException if the amount is null
	 * @throws IllegalArgumentException if the amount is not greater than zero
	 */
	public Bonus(Integer amount) {
		if(amount == null)
			throw new NullPointerException("The specified amount for the bonus cannot be null");
		if(Integer.signum(amount.intValue()) == -1)
			throw new IllegalArgumentException("The specified amount for the bonus must be greater than zero");
		this.amount = amount;
	} 
	
}
