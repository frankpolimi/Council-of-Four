package model.game.council;

import java.util.concurrent.ArrayBlockingQueue;


/**
 * @author Vitaliy Pakholko
 * This class exixts as a separate class from generic council because we thought that eventual future expansions of the game could
 * expand the king's council's functionalities and therefore we reserved it a class
 */
public class KingsCouncil extends Council
{
	private static final long serialVersionUID = 273965081908435798L;

	/**
	 * constructor for a king's council
	 * @param councillors the councillors that are part of the king's council
	 */
	public KingsCouncil(ArrayBlockingQueue<Councillor> councillors) 
	{
		super(councillors);
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return super.toString();
	}
	
	public boolean equals(Council c){
		return super.equals(c);
	}
}
