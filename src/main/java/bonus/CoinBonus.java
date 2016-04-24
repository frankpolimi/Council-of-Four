/**
 * add the specified amount of gold to the player
 */
package bonus;

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
	public <T> void update(T playerorgame) {
		((Giocatore) playerorgame).getStatus().setCoin(
				((Giocatore) playerorgame).getStatus().getCoin() + this.getAmount());
	}

}
