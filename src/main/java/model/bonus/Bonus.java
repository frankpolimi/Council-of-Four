package model.bonus;

import controller.Change;
import model.bonus.bonusers.Bonuser;
import model.observers.Observable;

public abstract class Bonus extends Observable<Change> implements Bonuser{
	
	/**
	 * @author Francesco Vetrò
	 */
	
	Integer amount;
	
	public Bonus(Integer amount) {
		this.amount = amount;
	} 
	
}
