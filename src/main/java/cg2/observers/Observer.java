package cg2.observers;

/**
 * 
 * @author Emanuele Ricciardelli
 *
 */
public interface Observer<C>  {
	public void update();
	public default void update(C change){
		//TODO
	}
	public void update(String communication);
}
