package cg2.model;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import cg2.player.Player;
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
	
	public void givePermit(Player player, BuildingPermit permit)
	{
		if(faceUpPermits.contains(permit))
			player.getStatus().addBuildingPermit(permit);
		System.out.println("No such permit in this deck's face up permits");
		
	}
	
	public void shuffleFaceUpPermits()
	{
		//TODO method after the deck is changed to an array list
	}
	
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
