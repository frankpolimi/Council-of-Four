/**
 * 
 */
package bonus;

/**
 * @author Francesco Vetr�
 * first type of bonuses that works on the player
 */

public abstract class TileBonus extends Bonus {

	private Integer amount;
	
	/**
	 * constructor specifing the amount of bonus
	 * @param importo
	 */
	public TileBonus(Integer amount){
		this.amount = new Integer(amount);
	}

	/**
	 * @return the importo
	 */
	public Integer getAmount() {
		return amount;
	}

	/**
	 * @param importo the importo to set
	 */
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
}
