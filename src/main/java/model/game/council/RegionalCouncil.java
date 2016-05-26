package model.game.council;

import java.util.concurrent.ArrayBlockingQueue;

import model.game.PermitsDeck;

/**
 * @author Vitaliy Pakholko
 */
public class RegionalCouncil extends Council 
{
	private final PermitsDeck permitsDeck;
	public RegionalCouncil(ArrayBlockingQueue<Councillor> councillors, PermitsDeck permitsDeck) {
		super(councillors);
		this.permitsDeck=permitsDeck;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString()+" permitsDeck= "+this.permitsDeck.toString();
	}
	
	/**
	 * @return the permitsDeck
	 */
	public PermitsDeck getPermitsDeck() {
		return permitsDeck;
	}
}
