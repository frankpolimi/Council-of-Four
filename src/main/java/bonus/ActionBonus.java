/**
 * 
 */
package bonus;

import java.util.ArrayList;
import java.util.List;

import cg2.game.Game;

/**
 * @author Francesco Vetr�
 */
public class ActionBonus extends Bonus {
	
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
