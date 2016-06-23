package model.observers;

/**
 * @author Emanuele Ricciardelli
 *
 * interface for a observer object
 */
public interface Observer<C>  {
	/**
	 * a generic update
	 */
	public void update();
	
	/**
	 * an update with the change of the observed object
	 * @param change the change that happened in the observed class
	 * @throws IllegalArgumentException if change is correct but parameters
	 * 									are inconsistent
	 * @throws IllegalStateException if change contains errors
	 */
	public void update(C change) throws IllegalArgumentException, IllegalStateException;
	
}
