/**
 * 
 */
package model.bonus;

import model.game.*;

/**
 * @author Francesco Vetrò
 *
 */
public class GetPoliticBonus extends TileBonus {

	/**
	 * constructor with the amount of cards to be given 
	 * @param amount
	 */
	public GetPoliticBonus(Integer amount) {
		super(amount);
	}

	/* (non-Javadoc)
	 * @see bonus.bonusers.Bonuser#update()
	 */
	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see bonus.bonusers.Bonuser#update(java.lang.Object)
	 */
	/**
	 * @param the instance of the game
	 * (this is not good at all, but it's awful to implements)
	 */
	@Override
	public void update(Game game) {
		Player p = game.getCurrentPlayer();
		for(int i=0;i<super.getAmount();i++)
			game.getPoliticsDeck().drawCard(p);
	}
	
	@Override
	public String toString(){
		return "PoliticBonus"+super.toString();
	}

}
