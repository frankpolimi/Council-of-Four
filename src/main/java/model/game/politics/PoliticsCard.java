package model.game.politics;

import java.io.Serializable;

/**
 * @author Vitaliy Pakholko
 */
public abstract class PoliticsCard  implements CouncillorPayment, Serializable
{
	private static final long serialVersionUID = -7103641605489942648L;
	
	private String imagePath;
	
	/**
	 * constructor for a politic card
	 */
	public PoliticsCard(){}
	
	/**
	 * constructor for a politic card
	 * @param imagePath the path for the image
	 */
	public PoliticsCard(String imagePath) {
		this.imagePath=imagePath;
	}
	
	/**
	 * acquire the image's path of the card
	 * @return the image's path of the card
	 */
	public String getImagePath() {
		return imagePath;
	}
	
}
