package cg2.model;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import cg2.player.Player;
/**
 * 
 * @author Emanuele Ricciardelli modified by someone
 *
 */
public class PermitsDeck {
	private List<BuildingPermit> buildingPermitsDeck;
	private ArrayBlockingQueue<BuildingPermit> faceUpPermits;

	
	public PermitsDeck(List<BuildingPermit> buildingPermitsDeck) {
		this.buildingPermitsDeck = buildingPermitsDeck;
		this.faceUpPermits = new ArrayBlockingQueue<>(2);
	}

	public boolean givePermit(Player player, BuildingPermit permit)
	{
		if(faceUpPermits.contains(permit))
		{
			player.getStatus().addBuildingPermit(permit);
			return true;
		}
		System.out.println("No such permit in this deck's face up permits");
		return false;
		
	}
	
	public void changeFaceUpPermits()
	{
		if(faceUpPermits.size()==2)
		{
			faceUpPermits.drainTo(buildingPermitsDeck);
			for(int i=faceUpPermits.remainingCapacity();i>0;i--)
			{
				faceUpPermits.add(buildingPermitsDeck.remove(0));
			}
		}
		else System.out.println("Not enought permits to change");
	}
	
	public ArrayBlockingQueue<BuildingPermit> getFaceUpPermits() {
		return faceUpPermits;
	}

	public void setFaceUpPermits(ArrayBlockingQueue<BuildingPermit> faceUpPermits) {
		this.faceUpPermits = faceUpPermits;
	}

	
	/**
	 * @return the buildingPermitsDeck
	 */
	public List<BuildingPermit> getBuildingPermitsDeck() {
		return buildingPermitsDeck;
	}


	/**
	 * @param buildingPermitsDeck the buildingPermitsDeck to set
	 */
	public void setBuildingPermitsDeck(List<BuildingPermit> buildingPermitsDeck) {
		this.buildingPermitsDeck = buildingPermitsDeck;
	}
	
	public void deckViewer(){
		for(BuildingPermit p:buildingPermitsDeck){
			p.toString();
		}
	}
}
