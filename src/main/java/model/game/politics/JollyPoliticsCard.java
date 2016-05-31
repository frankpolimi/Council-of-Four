package model.game.politics;
import model.game.council.Councillor;
/**
 * @author Vitaliy Pakholko
 */
public class JollyPoliticsCard extends PoliticsCard 
{	
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
	
	
}
