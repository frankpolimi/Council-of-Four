/**
 * 
 */
package cg2.model;

import java.awt.Color;


/**
 * @author Emanuele Ricciardelli
 *
 */
public class Emporium {
	
	private final City city;
	private final Color color;
	
	/**
	 * 
	 * @param c is the city in which the emporium is built
	 * @param color is the color of the emporium.
	 * @throws NullPointerException if color or city is null
	 */
	
	//VALUTARE SE è IL CASO DI TOGLIERE IL REF DI PLAYER PER NON CREARE AMBIGUITà!!!
	public Emporium(City c, Color color){
		if(c==null||color==null){
			throw new NullPointerException("one either Color or city is null");
		}
		
		city = c;
		this.color=color;
	}

	
	/**
	 * @return the color
	 */
	public Color getColor() {
		return color;
	}


	/**
	 * @return the city
	 */
	public City getCity(){
		return city;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return city.toString();
	}




	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Emporium other = (Emporium) obj;
		if (city == null) {
			if (other.city != null)
				return false;
		} else if (!city.equals(other.city))
			return false;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		return true;
	}
	
	
	
	
	
}
