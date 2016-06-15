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
	 * constructor for a basic bonus
	 * @param amount the amount of the bonus
	 * @throws NullPointerException if amount is null
	 * @throws IllegalArgumentException if amount is not greater than zero
	 */
	public TileBonus(Integer amount) throws NullPointerException, IllegalArgumentException{
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
