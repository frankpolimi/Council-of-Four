/**
 * add the specified amount of gold to the player
 */
package bonus;

import cg2.player.*;

/**
 * @author Francesco Vetrò
 */
public class CoinBonus extends TileBonus {
	/**
	 * construct a coin bonus with the given amount as parameter
	 * @param importo
	 */
	public CoinBonus(Integer importo) {
		super(importo);
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
	public <T> void update(T playerOrGame) {
		((Player) playerOrGame).getStatus().setCoins(
				((Player) playerOrGame).getStatus().getCoins() + this.getAmount());
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CoinBonus: "+super.toString();
	}

}
