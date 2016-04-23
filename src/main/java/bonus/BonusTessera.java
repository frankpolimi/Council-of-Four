/**
 * 
 */
package bonus;

/**
 * @author Francesco Vetrò
 * first type of bonuses that works on the player
 */

public abstract class BonusTessera extends Bonus {

	private Integer amount;
	
	/**
	 * constructor specifing the amount of bonus
	 * @param importo
	 */
	public BonusTessera(Integer amount){
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
