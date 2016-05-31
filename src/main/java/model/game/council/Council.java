
package model.game.council;
import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;


/**
 * @author Vitaliy Pakholko
 */
public abstract class Council implements Serializable
{
	private static final long serialVersionUID = 8503784660222509923L;
	private final ArrayBlockingQueue<Councillor> councillors;
	
	public Council(ArrayBlockingQueue<Councillor> councillors) 
	{
		if (councillors!=null)
			this.councillors = councillors;	
		else throw new NullPointerException();
	}
	
	/**
	 * Adds given councillor in tail position and returns the one removed from head position
	 * @param councillor will be added to the tail of the council
	 * @return the councillor removed from the head position
	 * @throws IllegalStateException if the application failed to remove head element
	 * @throws NullPointerException if the queue was empty when attempting to remove head element
	 */
	public Councillor electCouncillor(Councillor councillor)
	{
		Councillor temp=councillors.poll();
		if (temp==null)
			throw new NullPointerException();
		councillors.add(councillor);
		return temp;
	}

	public ArrayBlockingQueue<Councillor> getCouncillors() 
	{
		if(this.councillors!=null)
			return councillors;
		else throw new NullPointerException();
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Council [councillors=" + councillors + "]\n";
	}
}
