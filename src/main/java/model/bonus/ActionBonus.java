/**
 * 
 */
package model.bonus;

import java.util.ArrayList;
import java.util.List;

import model.game.Game;

/**
 * @author Francesco Vetrò
 */
public class ActionBonus extends Bonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1087850494213111007L;

	public ActionBonus(Integer repeat) {
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
		// TODO Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ActionBonus [amount=" + amount + "]";
	}

	public List<? extends Bonus> checkNoNobility(List<Bonus> list) {
		List<Bonus> correct = new ArrayList<Bonus>();
		for(Bonus b: list)
			if(!(b.getClass().equals(NobilityBonus.class)))
				correct.add(b);
		return correct;
	}

	
}
