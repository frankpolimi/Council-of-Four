/**
 * 
 */
package model.bonus;

/**
 * @author Francesco Vetr�
 * first type of bonuses that works on the player
 */

public abstract class TileBonus extends Bonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2968416294698944311L;

	/**
	 * constructor specifing the amount of bonus
	 * @param importo
	 */
	public TileBonus(Integer amount){
		super(amount);
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "amount = " + amount;
	}
}
