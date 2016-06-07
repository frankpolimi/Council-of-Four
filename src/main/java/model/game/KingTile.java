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
	 * 
	 */
	public KingTile(int amount) {
		super(amount);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "KingTile\n "+super.toString()+"";
	}
	
	


}
