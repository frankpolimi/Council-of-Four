package politics;
import council.Councillor;
/**
 * @author Vitaliy Pakholko
 */
public class JollyPoliticsCard extends PoliticsCard 
{
	public boolean payCouncillor(Councillor councillor)
	{
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Jolly";
	}
	
	
}
