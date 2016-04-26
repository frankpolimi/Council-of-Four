package cg2.model;
import java.util.*;
/**
 * 
 * @author Emanuele Ricciardelli
 *
 */
public class LicenseDeck {
	private Set<BuildingLicense> buildingPermitsDeck;
	
	
	public LicenseDeck(Set<BuildingLicense> deck){
		buildingPermitsDeck=deck;
	}


	/**
	 * @return the buildingPermitsDeck
	 */
	public Set<BuildingLicense> getBuildingPermitsDeck() {
		return buildingPermitsDeck;
	}


	/**
	 * @param buildingPermitsDeck the buildingPermitsDeck to set
	 */
	public void setBuildingPermitsDeck(Set<BuildingLicense> buildingPermitsDeck) {
		this.buildingPermitsDeck = buildingPermitsDeck;
	}
	
	public void deckViewer(){
		for(BuildingLicense p:buildingPermitsDeck){
			p.toString();
		}
	}
}
