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
	 * @param amount
	 */
	public RegionTile(int amount, Region region) {
		super(amount);
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
