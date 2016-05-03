package cg2.model;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import council.Council;
/**
 * 
 * @author Emanuele Ricciardelli
 *
 */
public class PermitsDeck {
	private Set<BuildingPermit> buildingPermitsDeck;
	private ArrayBlockingQueue<BuildingPermit> faceUpPermits;
	private final Council council;
	
	
	public ArrayBlockingQueue<BuildingPermit> getFaceUpPermits() {
		return faceUpPermits;
	}

	public void setFaceUpPermits(ArrayBlockingQueue<BuildingPermit> faceUpPermits) {
		this.faceUpPermits = faceUpPermits;
	}

	public PermitsDeck(Set<BuildingPermit> deck, Council council){
		buildingPermitsDeck=deck;
		this.council=council;
	}

	public Council getCouncil() {
		return council;
	}

	/**
	 * @return the buildingPermitsDeck
	 */
	public Set<BuildingPermit> getBuildingPermitsDeck() {
		return buildingPermitsDeck;
	}


	/**
	 * @param buildingPermitsDeck the buildingPermitsDeck to set
	 */
	public void setBuildingPermitsDeck(Set<BuildingPermit> buildingPermitsDeck) {
		this.buildingPermitsDeck = buildingPermitsDeck;
	}
	
	public void deckViewer(){
		for(BuildingPermit p:buildingPermitsDeck){
			p.toString();
		}
	}
}
