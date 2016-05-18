package bonus;

import bonus.bonusers.Bonuser;
import cg2.controller.Change;
import cg2.observers.Observable;

public abstract class Bonus extends Observable<Change> implements Bonuser{
	
	/**
	 * @author Francesco Vetrò
	 */
	
	Integer amount;
	
	public Bonus(Integer amount) {
		this.amount = amount;
	} 
	
}
