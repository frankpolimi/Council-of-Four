/**
 * 
 */
package model.bonus;

import model.game.*;

/**
 * @author Francesco Vetro'
 *
 */
public class GetPoliticBonus extends TileBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2498187827242720735L;

	/**
	 * constructor for a politic bonus
	 * @param amount the politics card given to a player
	 * @throws NullPointerException if amount is null
	 * @throws IllegalArgumentException if amount is not greater than zero
	 */
	public GetPoliticBonus(Integer amount) throws NullPointerException, IllegalArgumentException{
		super(amount);
	}

	/* (non-Javadoc)
	 * @see bonus.bonusers.Bonuser#update()
	 */
	@Override
	public void update() {
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
