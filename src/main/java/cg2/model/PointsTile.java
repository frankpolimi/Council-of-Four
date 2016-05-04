package cg2.model;

/**
 * 
 * @author Emanuele Ricciardelli
 * This is the abstract class that represents the abstraction of the VPs on the game.
 */
public abstract class PointsTile {
	/**
	 * The first attribute represents the amount won by the player who caught it.
	 * The second one is a link for handling the lists of Tessere Punti Vittoria in the game.
	 */
	private final int VPs;
	
	/**
	 * 
	 * @param amount
	 */
	public PointsTile(int amount){
		VPs=amount;
		
	}

	/**
	 * @return the puntiVittoria
	 */
	public int getVPs() {
		return VPs;
	}
	
	
	
}
