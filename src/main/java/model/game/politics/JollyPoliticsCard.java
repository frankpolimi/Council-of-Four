package model.game.politics;
import model.game.council.Councillor;
/**
 * @author Vitaliy Pakholko
 */
public class JollyPoliticsCard extends PoliticsCard 
{	
	public JollyPoliticsCard(String imagePath) {
		super(imagePath);
		// TODO Auto-generated constructor stub
	}
	
	public JollyPoliticsCard() {}

	private static final long serialVersionUID = 4275233909860022900L;

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
