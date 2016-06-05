package model.game.council;

import java.util.concurrent.ArrayBlockingQueue;

import model.game.PermitsDeck;

/**
 * @author Vitaliy Pakholko
 */
public class RegionalCouncil extends Council 
{
	private static final long serialVersionUID = -8292033152540671109L;
	private final PermitsDeck permitsDeck;
	public RegionalCouncil(ArrayBlockingQueue<Councillor> councillors, PermitsDeck permitsDeck) {
		super(councillors);
		if(permitsDeck!=null)
			this.permitsDeck=permitsDeck;
		else throw new NullPointerException();
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
	public PermitsDeck getPermitsDeck() 
	{
		if(this.permitsDeck!=null)
			return permitsDeck;
		else throw new NullPointerException();
	}
	
	public boolean equals(Council c){
		return super.equals(c);
	}
}
