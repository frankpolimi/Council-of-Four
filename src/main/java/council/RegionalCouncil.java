package council;

import java.util.concurrent.ArrayBlockingQueue;

import cg2.model.PermitsDeck;

/**
 * @author Vitaliy Pakholko
 */
public class RegionalCouncil extends Council 
{

	public RegionalCouncil(ArrayBlockingQueue<Councillor> councillors, PermitsDeck permitsDeck) {
		super(councillors, permitsDeck);
		// TODO Auto-generated constructor stub
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString();
	}
	
}
