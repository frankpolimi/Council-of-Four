/**
 * 
 */
package bonus;

/**
 * @author Francesco Vetr�
 * first type of bonuses that works on the player
 */

public abstract class BonusTessere extends Bonus {

	private Integer ammontare;
	
	/**
	 * constructor specifing the amount of bonus
	 * @param importo
	 */
	public BonusTessere(Integer ammontare){
		this.ammontare = new Integer(ammontare);
	}

	/**
	 * @return the importo
	 */
	public Integer getAmmontare() {
		return ammontare;
	}

	/**
	 * @param importo the importo to set
	 */
	public void setAmmontare(Integer ammontare) {
		this.ammontare = ammontare;
	}
}
