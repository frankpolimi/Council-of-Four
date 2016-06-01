/**
 * 
 */
package model.game;

import java.awt.Color;
import java.io.Serializable;

import model.game.topology.City;


/**
 * @author Emanuele Ricciardelli
 *
 */
public class Emporium implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4245715478199858323L;
	private final City city;
	private final Color color;
	
	/**
	 * 
	 * @param c is the city in which the emporium is built
	 * @param color is the color of the emporium.
	 * @throws NullPointerException if color or city is null
	 */
	
	//VALUTARE SE � IL CASO DI TOGLIERE IL REF DI PLAYER PER NON CREARE AMBIGUIT�!!!
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
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((city == null) ? 0 : city.hashCode());
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		return result;
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
