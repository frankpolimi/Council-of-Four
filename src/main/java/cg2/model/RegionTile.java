/**
 * 
 */
package cg2.model;

import topology.Region;

/**
 * @author Emanuele Ricciardelli
 *
 */
public class RegionTile extends PointsTile {
	
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
		return "RegionTile [region=" + region + ", amount: "+super.getVPs()+"]";
	}	
	
	

}
