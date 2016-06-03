package model.game;
import java.io.Serializable;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
/**
 * 
 * @author Emanuele Ricciardelli, Vitaliy Pakholko
 *
 */

public class PermitsDeck implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -6786269689447908912L;
	private List<BuildingPermit> buildingPermitsDeck;
	private ArrayBlockingQueue<BuildingPermit> faceUpPermits;

	
	public PermitsDeck(List<BuildingPermit> buildingPermitsDeck) {
		this.buildingPermitsDeck = buildingPermitsDeck;
		Collections.shuffle(this.buildingPermitsDeck);
		this.faceUpPermits = new ArrayBlockingQueue<>(2);
		
	}
	
	/**
	 * @author Vitaliy Pakholko
	 */
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
		throw new IllegalArgumentException("No such permit in this deck's face up permits");		
	}
	
	
	/**
	 * @author Vitaliy Pakholko
	 */
	public void changeFaceUpPermits()
	{
		if(faceUpPermits.size()==2)
		{
			faceUpPermits.drainTo(buildingPermitsDeck);
			
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
	
	/**
	 * @author Vitaliy Pakholko
	 */
	public BuildingPermit popPermit()
	{
		if(this.buildingPermitsDeck==null||this.buildingPermitsDeck.size()<=0)
		{
			throw new NullPointerException(); 
		}
		else
			return this.buildingPermitsDeck.remove(0);
	}
	
	/**
	 * @author Vitaliy Pakholko
	 */
	public void faceUpInit()
	{
		System.out.println("Sto inizializzando");
		if(this.buildingPermitsDeck==null||this.buildingPermitsDeck.size()<=faceUpPermits.size()-1)
		{
			throw new NullPointerException(); 
		}
		else for(int i=faceUpPermits.remainingCapacity();i>0;i--)
		{
			System.out.println("i="+i);
			faceUpPermits.add(buildingPermitsDeck.remove(0));
		}	
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PermitsDeck [buildingPermitsDeck=" + buildingPermitsDeck + "\n\n, faceUpPermits=" + faceUpPermits + "]\n";
	}
	
	public BuildingPermit giveAFaceUpPermit(int index){
		//switching between the first and the second card.
		BuildingPermit extracted;
		if(index==2){
			extracted=this.faceUpPermits.remove();
			this.faceUpPermits.add(extracted);
		}
		
		return this.faceUpPermits.element();
			
	}
	
	
}
