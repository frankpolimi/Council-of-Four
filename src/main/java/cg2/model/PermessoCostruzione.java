package cg2.model;
import java.util.*;

/**
 * 
 * @author Emanuele Ricciardelli
 *
 */
public class PermessoCostruzione {
	private final Regione region;
	private final Set<Character> firstChars;
	private final Set<BonusTessere> bonusList;
	private final Set<Citta> buildingAvaliableCities;
	
	public PermessoCostruzione(Regione region, Set<Citta> ba, Set<BonusTessere> bonus){
		this.region=region;
		buildingAvaliableCities=ba;
		bonusList=bonus;
		firstChars=new HashSet<Character>();
		for(Citta c: buildingAvaliableCities){
			firstChars.add(c.getFirstChar());
		}	
	}

	/**
	 * @return the region
	 */
	public Regione getRegion() {
		return region;
	}

	/**
	 * @return the firstChars
	 */
	public Set<Character> getFirstChars() {
		return firstChars;
	}

	/**
	 * @return the bonusList
	 */
	public Set<BonusTessere> getBonusList() {
		return bonusList;
	}

	/**
	 * @return the buildingAvaliableCities
	 */
	public Set<Citta> getBuildingAvaliableCities() {
		return buildingAvaliableCities;
	}
	
	//mancano HashCode, toString
}
