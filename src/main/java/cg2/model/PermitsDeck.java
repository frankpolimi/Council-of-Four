package cg2.model;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import cg2.game.Game;
/**
 * 
 * @author Emanuele Ricciardelli, Vitaliy Pakholko
 *
 */

public class PermitsDeck {
	private List<BuildingPermit> buildingPermitsDeck;
	private ArrayBlockingQueue<BuildingPermit> faceUpPermits;

	
	public PermitsDeck(List<BuildingPermit> buildingPermitsDeck) {
		this.buildingPermitsDeck = buildingPermitsDeck;
		//aggiungere shuffle dell'array
		this.faceUpPermits = new ArrayBlockingQueue<>(2);
		//posizionare le prime due scoperte
	}

	public boolean givePermit(Game game, BuildingPermit permit)
	{
		if(faceUpPermits.contains(permit))
		{
			game.getCurrentPlayer().addBuildingPermit(permit);
			permit.applyBonus(game);
			if(faceUpPermits.remove(permit))
				if(faceUpPermits.add(buildingPermitsDeck.remove(0)))
					return true;
				else throw new IllegalStateException("Cannot add the top deck permit");
		}
		System.out.println("No such permit in this deck's face up permits");
		return false;
		
	}
	
	
	/**
	 * @author Vitaliy Pakholko
	 */
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
		else throw new IllegalStateException("Not enough permits to change them, the deck is probably empty");
	}
	
	/**
	 * @author Vitaliy Pakholko
	 */
	public ArrayBlockingQueue<BuildingPermit> getFaceUpPermits() 
	{
		return faceUpPermits;
	}
	
	/**
	 * @author Vitaliy Pakholko
	 */
	public void setFaceUpPermits(ArrayBlockingQueue<BuildingPermit> faceUpPermits) 
	{
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
