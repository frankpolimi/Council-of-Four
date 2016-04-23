package cg2.model;
import java.util.*;
/**
 * 
 * @author Emanuele Ricciardelli
 *
 */
public class MazzoPermesso {
	private Set<PermessoCostruzione> buildingPermitsDeck;
	
	
	public MazzoPermesso(Set<PermessoCostruzione> deck){
		buildingPermitsDeck=deck;
	}


	/**
	 * @return the buildingPermitsDeck
	 */
	public Set<PermessoCostruzione> getBuildingPermitsDeck() {
		return buildingPermitsDeck;
	}


	/**
	 * @param buildingPermitsDeck the buildingPermitsDeck to set
	 */
	public void setBuildingPermitsDeck(Set<PermessoCostruzione> buildingPermitsDeck) {
		this.buildingPermitsDeck = buildingPermitsDeck;
	}
	
	public void deckViewer(){
		for(PermessoCostruzione p:buildingPermitsDeck){
			p.toString();
		}
	}
}
