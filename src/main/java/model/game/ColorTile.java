package model.game;

import java.awt.Color;
import java.io.Serializable;

public class ColorTile extends PointsTile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5183007354615074816L;
	private final Color cityColor;
	
	/**
	 * This method is the ColorTile constructor
	 * @param amount indicates how many points the owner earns.
	 * @param color indicates the color of the set of cities which unblock this achievement. 
	 * @param imagePath is the path to the image of the tile
	 * @throws NullPointerException if color is null
	 * @throws IllegalArgumentException if amount is negative or zero
	 */
	public ColorTile(int amount, Color color, String imagePath) {
		super(amount, imagePath);
		if(amount<=0){
			throw new IllegalArgumentException("The amount cannot be negative or zero");
		}
		
		if(color==null){
			throw new NullPointerException("The color cannot be null");
		}
		cityColor=color;
	}
	
	/**
	 * This method is the ColorTile constructor
	 * @param amount indicates how many points the owner earns.
	 * @param color indicates the color of the set of cities which unblock this achievement. 
	 * @throws NullPointerException if color is null
	 * @throws IllegalArgumentException if amount is negative or zero
	 */
	public ColorTile(int amount, Color color) {
		super(amount);
		if(amount<=0){
			throw new IllegalArgumentException("The amount cannot be negative or zero");
		}
		
		if(color==null){
			throw new NullPointerException("The color cannot be null");
		}
		cityColor=color;
	}

	/**
	 * @return the cityColor
	 */
	public Color getCityColor() {
		return cityColor;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		
		return "ColorTile "+ cityColor + ", amounts="+ super.getVPs()+"\n";
	}

	
	
	

}
