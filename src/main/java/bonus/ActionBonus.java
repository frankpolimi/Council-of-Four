/**
 * 
 */
package bonus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * @author Francesco Vetrò
 */
public class ActionBonus extends Bonus {
	
	public ActionBonus(Integer repeat) {
		super(repeat);
	}

	@Override
	public <T> void update(T playerOrGame) {
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

	public List<? extends Bonus> checkNoNobility(Set<TileBonus> bonus) {
		Iterator<TileBonus> x = bonus.iterator();
		List<TileBonus> correct = new ArrayList<TileBonus>();
		TileBonus b = null;
		while(x.hasNext())
			b = x.next();
			if(!(b.getClass().equals(NobilityBonus.class)))
				correct.add(b);
		return correct;
	}

	
}
