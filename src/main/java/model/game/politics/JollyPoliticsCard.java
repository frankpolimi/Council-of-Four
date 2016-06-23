package model.game.politics;
import model.game.council.Councillor;
/**
 * @author Vitaliy Pakholko
 */
public class JollyPoliticsCard extends PoliticsCard 
{	
	
	private static final long serialVersionUID = 4275233909860022900L;
	
	/**
	 * constructor for a jolly card
	 */
	public JollyPoliticsCard() {}
	
	/**
	 * constructor for a jolly card 
	 * @param imagePath the path for the image
	 */
	public JollyPoliticsCard(String imagePath) {
		super(imagePath);
	}

	/**
	 * method to check if this card can pay the councillor
	 * given as parameter
	 * @param councillor the councillor that this card
	 * 						tries to corrupt 
	 * @return always true because a jolly card can
	 * 			pay any given councillor
	 */
	@Override
	public boolean payCouncillor(Councillor councillor)
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Jolly politcs card";
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		return true;
	}
	
}
