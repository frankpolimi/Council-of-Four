/**
 * 
 */
package view;

import java.io.Serializable;
import java.util.List;
import model.bonus.Bonus;

/**
 * @author Francesco Vetr�
 *
 */
public class BonusState implements State, Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1762986718994224075L;
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
