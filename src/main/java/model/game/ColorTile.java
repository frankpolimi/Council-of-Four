package model.game;

import java.awt.Color;
import java.io.Serializable;

public class ColorTile extends PointsTile implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 5183007354615074816L;
	private final Color cityColor;
	
	public ColorTile(int amount, Color color) {
		super(amount);
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
		
		return "ColorTile [cityColor=" + cityColor + ", amounts="+ super.getVPs()+"]";
	}

	
	
	

}
