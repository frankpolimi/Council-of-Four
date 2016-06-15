/**
 * 
 */
package model.game;

import model.game.topology.Region;

/**
 * @author Emanuele Ricciardelli
 *
 */
public class RegionTile extends PointsTile {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9204116877827941026L;
	private final Region region;
	
	/**
	 * constructor for a region tile 
	 * @param amount the amount of victory points given by the tile
	 * @param region the region which the tile refers to. onche the player complete
	 * 				the construction of an emporium on all the cities of a region, 
	 * 				this tile will be given as a copy to the player
	 * @throws IllegalArgumentException if the amount of points is below or equals to zero
	 * @throws NullPointerException if the region is null
	 */
	public RegionTile(int amount, Region region, String imagePath) {
		super(amount, imagePath);
		if(amount<=0)
			throw new IllegalArgumentException("The amount cannot be negative or zero");
		if(region == null)
			throw new NullPointerException("The region cannot be null");
		this.region=region;
	}
	
	public RegionTile(int amount, Region region) {
		super(amount);
		if(amount<=0)
			throw new IllegalArgumentException("The amount cannot be negative or zero");
		if(region == null)
			throw new NullPointerException("The region cannot be null");
		this.region=region;
	}
	
	/**
	 * @return the region
	 */
	public Region getRegion() {
		return region;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RegionTile" + region.getName() + " amount: "+super.getVPs()+"\n";
	}	
	
	

}
