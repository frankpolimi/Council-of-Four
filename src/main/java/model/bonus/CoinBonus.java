/**
 * add the specified amount of gold to the player
 */
package model.bonus;

import model.game.Game;
/**
 * @author Francesco Vetrò
 */
public class CoinBonus extends TileBonus {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1390525919209177348L;

	/**
	 * construct a coin bonus with the given amount as parameter
	 * @param importo
	 */
	public CoinBonus(Integer amount) {
		super(amount);
	}

	/* (non-Javadoc)
	 * @see bonus.bonusers.Bonuser#update(java.lang.Object)
	 */
	/**
	 * update the amount of gold of the player as parameter
	 * by the import specified
	 * @param player
	 */
	@Override
	public void update(Game game) {
		game.getCurrentPlayer().setCoins(
				game.getCurrentPlayer().getCoins() + this.getAmount());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CoinBonus: "+super.toString();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

}
