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
		System.out.println("Select the bonus you want to acquire");
		for(Bonus b : bonusList)
			System.out.println((bonusList.indexOf(b)+1)+" - " +b.toString());
		int selection=this.selector(1, bonusList.size());
		BonusRequest request = new BonusRequest();
		request.addBonus(bonusList.get(selection-1));
		/*
		 * TODO send to view server
		 * via socket/RMI
		 */
	}
	
	public List<Bonus> getBonus() {
		return bonusList;
	}

}
