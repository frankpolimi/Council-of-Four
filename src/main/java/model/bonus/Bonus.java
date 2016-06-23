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
	private static int IDGenerator=0;
	private int bonusID=IDGenerator;
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
		IDGenerator++;
	}

	/**
	 * @return the bonusID
	 */
	public int getBonusID() {
		return bonusID;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((amount == null) ? 0 : amount.hashCode());
		result = prime * result + bonusID;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Bonus other = (Bonus) obj;
		if (amount == null) {
			if (other.amount != null)
				return false;
		} else if (!amount.equals(other.amount))
			return false;
		if (bonusID != other.bonusID)
			return false;
		return true;
	} 

	
	
}
