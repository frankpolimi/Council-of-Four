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
	private static int IDGenerator=0;
	private final int deckID=IDGenerator;
	
	/**
	 * constructor for a deck of building permits
	 * @param buildingPermitsDeck
	 * @throws NullPointerException if the deck given as parameter is null
	 */
	public PermitsDeck(List<BuildingPermit> buildingPermitsDeck) {
		if(buildingPermitsDeck == null)
			throw new NullPointerException();
		this.buildingPermitsDeck = buildingPermitsDeck;
		Collections.shuffle(this.buildingPermitsDeck);
		this.faceUpPermits = new ArrayBlockingQueue<>(2);
		IDGenerator++;
		
	}
	
	/**
	 * @author Vitaliy Pakholko
	 * method that gives a permit to the current player
	 * @param game the game that is played
	 * @param permit the permit that is required by the player
	 * @throws IllegalStateException if the deck is empty and cannot
	 * 				add a permit to the face up permits or when a permit
	 * 				fails to be removed from the face up permits
	 * @throws IllegalArgumentException if the permit given as parameter
	 * 				isn't contained in the face up permits
	 */
	public boolean givePermit(Game game, BuildingPermit permit)
	{
		if(this.faceUpPermits.contains(permit))
		{
			BuildingPermit supportPermit;
			game.getCurrentPlayer().addBuildingPermit(permit);
			permit.applyBonus(game);
			if(faceUpPermits.remove(permit))
			{
				try
				{
					supportPermit=buildingPermitsDeck.remove(0);
				}
				catch(IndexOutOfBoundsException e)
				{
					supportPermit=null;
					throw new IllegalStateException("Cannot add the top deck permit");
				}
				try
				{
				faceUpPermits.add(supportPermit);
					return true;
				}catch(NullPointerException e)
				{
					throw new IllegalStateException("Cannot add the top deck permit");
				}
			}
			else throw new IllegalStateException("Cannot remove the permit from face ups");
		}
		throw new IllegalArgumentException("No such permit in this deck's face up permits");		
	}
	
	
	/**
	 * @author Vitaliy Pakholko
	 * this method allows to change the face up permits
	 * and substitute them with the first two elements 
	 * of the permits deck
	 * @throws IllegalStateException if the amount of face up 
	 * 				permits is below 2 
	 */
	public void changeFaceUpPermits()
	{
		if(faceUpPermits.size()==2)
		{
			this.faceUpPermits.drainTo(this.buildingPermitsDeck);
			this.faceUpPermits.add(this.buildingPermitsDeck.remove(0));
			this.faceUpPermits.add(this.buildingPermitsDeck.remove(0));
			
		}
		else throw new IllegalStateException("Not enough permits to change them, the deck is probably empty");
	}
	
	/**
	 * @author Vitaliy Pakholko
	 * get the face up permits
	 * @return the face up permits of the deck
	 */
	public ArrayBlockingQueue<BuildingPermit> getFaceUpPermits() 
	{
		return faceUpPermits;
	}
	
	/**
	 * @author Vitaliy Pakholko
	 * set the face up permits of the deck
	 * @param faceUpPermits the two face up permits
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
	 * get the first permit of the deck
	 * @return the first permit of the deck
	 * @throws NullPointerException if the deck is null
	 * 			or is empty
	 */
	public BuildingPermit popPermit()
	{
		if(this.buildingPermitsDeck==null||this.buildingPermitsDeck.isEmpty())
		{
			throw new NullPointerException(); 
		}
		else
			return this.buildingPermitsDeck.remove(0);
	}
	
	/**
	 * @author Vitaliy Pakholko
	 * this method set up the face up permits of the deck
	 * @throws NullPointerException if the deck is null
	 * 			or the deck contains not enough elements
	 * 			to correctly initialize all the faceup permits
	 */
	public void faceUpInit()
	{
		if(this.buildingPermitsDeck==null||this.buildingPermitsDeck.size()<=this.faceUpPermits.size()-1)
		{
			throw new NullPointerException(); 
		}
		else for(int i=this.faceUpPermits.remainingCapacity();i>0;i--)
		{
			faceUpPermits.add(this.buildingPermitsDeck.remove(0));
		}	
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PermitsDeck [buildingPermitsDeck=" + buildingPermitsDeck + "\n\n, faceUpPermits=" + faceUpPermits + "]\n";
	}
	
	/**
	 * give the permit at the given index
	 * @param index the position of the permit in the face up permits list
	 * @return the building permit desired at the given position
	 */
	public BuildingPermit giveAFaceUpPermit(int index){
		//switching between the first and the second card.
		BuildingPermit extracted;
		if(index==2){
			extracted=this.faceUpPermits.remove();
			this.faceUpPermits.add(extracted);
		}
		
		return this.faceUpPermits.element();
			
	}
	
	/**
	 * @return the deck id
	 */
	public int getDeckID() {
		return deckID;
	}

	public boolean equals(PermitsDeck deck) 
	{
		if(deck == null)
			return false;
		if(this.deckID==deck.getDeckID())
			return true;
		return false;
	}
}
		
	
	
	
	
