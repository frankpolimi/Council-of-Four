/**
 * add the specified amount of gold to the player
 */
package bonus;

/**
 * @author Francesco Vetrò
 * 
 */
public class BonusMonete extends BonusTessere {
	
	public BonusMonete(Integer importo) {
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
	public void update(Giocatore player) {
		player.getStatus.setMonete(player.getStatus.getMonete() + this.getImporto());
	}

}
