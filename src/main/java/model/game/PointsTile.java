package model.game;

import java.io.Serializable;

/**
 * 
 * @author Emanuele Ricciardelli
 * This is the abstract class that represents the abstraction of the VPs on the game.
 */
public abstract class PointsTile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4979607843460407625L;
	
	/**
	 * The first attribute represents the amount won by the player who caught it.
	 * The second one is a link for handling the lists of Tessere Punti Vittoria in the game.
	 */
	private final int VPs;
	private String imagePath;
	
	/**
	 * constructor for the a points tile 
	 * @param amount the amount of victory points that the tile 
	 * 					gives to the player that obtains it
	 * @param imagePath the image of the tile
	 */
	public PointsTile(int amount, String imagePath){
		VPs=amount;
		this.imagePath=imagePath;
	}
	
	/**
	 * constructor for the a points tile 
	 * @param amount the amount of victory points that the tile 
	 * 					gives to the player that obtains it
	 */
	public PointsTile(int amount){
		VPs=amount;
	}
	
	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}


	/**
	 * @return the victory points
	 */
	public int getVPs() {
		return VPs;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VPs=" + VPs +"\n";
	}
	
	
	
	
}
