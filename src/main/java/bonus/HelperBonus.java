/**
 * add the specified amount of assistants to the player
 */
package bonus;

/**
 * @author Francesco Vetrò
 *
 */
public class HelperBonus extends TileBonus {
	
	public HelperBonus(Integer aiutanti) {
		super(aiutanti);
	}

	/**
	 * update the amount of assistants of the player as parameter
	 * by the amount specified
	 * @param player
	 */
	@Override
	public <T> void update(T playerorgame) {
		((Giocatore) playerorgame).getStatus().setHelper(
				((Giocatore) playerorgame).getStatus().getHelper() + this.getAmount());
		
	}

}
