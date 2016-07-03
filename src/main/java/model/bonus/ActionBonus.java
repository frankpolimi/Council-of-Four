/**
 * 
 */
package model.bonus;

import java.util.List;

import model.game.Game;

/**
 * @author Francesco Vetro'
 *
 */
public class ActionBonus extends Bonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1087850494213111007L;

	
	/**
	 * constructor for a special bonus
	 * @param repeat the times of a special bonus can be performed
	 * @throws NullPointerException if repeat is null
	 * @throws IllegalArgumentException if repeat is not greater than zero
	 */
	public ActionBonus(Integer repeat) throws NullPointerException, IllegalArgumentException{
		super(repeat);
	}

	@Override
	public void update(Game game) {
		/*
		 * method empty
		 * forced to write because of hierarchy and interface
		 */
	}

	@Override
	public void update() {
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ActionBonus [amount=" + amount + "]";
	}

	/**
	 * method that checks the bonuses from the list given as parameter 
	 * and removes them if it contains a bonus that increment the 
	 * amount of nobility points
	 * @param list the list of bonuses to check
	 * @return the list of bonuses that are not nobility bonus
	 */
	public boolean checkNoNobility(List<Bonus> list) {
		for(Bonus b: list)
			if(b.getClass().equals(NobilityBonus.class))
				return false;
		return true;
	}

	
}
