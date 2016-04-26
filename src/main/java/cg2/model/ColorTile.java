package cg2.model;

import java.awt.Color;

public class ColorTile extends PointsTile {
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

	
	
	

}
