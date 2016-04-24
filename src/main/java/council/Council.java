
package council;
import java.util.concurrent.ArrayBlockingQueue;

/**
 * @author Vitaliy Pakholko
 */
public abstract class Council 
{
	ArrayBlockingQueue<Councillor> councillors;
	
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
		if (temp==null)throw new NullPointerException();
		councillors.add(councillor);
		return temp;
	}
}
