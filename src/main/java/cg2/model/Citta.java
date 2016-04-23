/**
 * 
 */
package cg2.model;

import java.awt.Color;
import java.util.*;

/**
 * @author Emanuele Ricciardelli
 *	
 */
public class Citta {
	private final String name;
	private final char firstChar;
	private final Color cityColor;
	private final Set<Citta> linkedCities;
	private final BonusTessere bonus;
	//i collegamenti saranno letti da file, perciò ci sarà una fase di creazione
	//del vettore collegamenti da parte dell'inizializzatore della partita.
	public Citta(String name, Color color, Set<Citta> link, BonusTessere bonus){
		this.name=name;
		firstChar=name.charAt(0);
		linkedCities = link;
		cityColor=color;
		this.bonus=bonus;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the firstChar
	 */
	public char getFirstChar() {
		return firstChar;
	}

	/**
	 * @return the cityColor
	 */
	public Color getCityColor() {
		return cityColor;
	}

	/**
	 * @return the linkedCities
	 */
	public Set<Citta> getLinkedCities() {
		return linkedCities;
	}

	/**
	 * @return the bonus
	 */
	public BonusTessere getBonus() {
		return bonus;
	}
	
	
}
