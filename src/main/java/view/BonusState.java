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
	 * @see cg2.view.State#doAction(cg2.view.State, java.lang.String)
	 */
	@Override
	public void doAction(View view, String input) {
		//view.setState(new StartState());
	}

	/* (non-Javadoc)
	 * @see cg2.view.State#display()
	 */
	@Override
	public void display() {
		System.out.println("Insert the bonus' number you desire to acquire");
		for(Bonus b : bonusList)
			System.out.println(bonusList.indexOf(b)+" - "+b.toString());
	}

	public List<Bonus> getBonus() {
		return bonusList;
	}

}
