/**
 * 
 */
package model.game;

/**
 * @author Emanuele Ricciardelli
 *
 */
public class KingTile extends PointsTile{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8621077581684383479L;

	/**
	 * constructor for a kingTile
	 * @param amount the amount of points given by this tile
	 * @throws IllegalArgumentException if the amount is below or equals to zero
	 */
	public KingTile(int amount, String imagePath){
		super(amount, imagePath);
		if(amount<=0){
			throw new IllegalArgumentException("The amount cannot be negative or zero");
		}
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "KingTile\n "+super.toString()+"";
	}
	
	


}
