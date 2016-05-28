/**
 * 
 */
package view;

import java.util.List;
import model.bonus.Bonus;

/**
 * @author Francesco Vetr�
 *
 */
public class BonusState implements State {
	
	private List<Bonus> bonusList;

	public BonusState(List<Bonus> bonusList) {
		this.bonusList = bonusList;
	}

	/* (non-Javadoc)
	 * @see cg2.view.State#display()
	 */
	@Override
	public void display() {
		
	}
	
	public List<Bonus> getBonus() {
		return bonusList;
	}

}
