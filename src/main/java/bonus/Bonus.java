package bonus;

import bonus.bonusers.Bonuser;
import cg2.controller.Change;
import cg2.observers.Observable;

public abstract class Bonus extends Observable implements Bonuser{
	
	/**
	 * @author Francesco Vetr�
	 */
	
	Integer amount;
	
	public Bonus(Integer amount) {
		this.amount = amount;
	} 
	
}
