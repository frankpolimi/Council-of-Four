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
	 * @author Francesco Vetrò
	 */
	
	Integer amount;
	
	public Bonus(Integer amount) {
		this.amount = amount;
	} 
	
}
