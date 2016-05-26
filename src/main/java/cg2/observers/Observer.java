package cg2.observers;

/**
 * 
 * @author Emanuele Ricciardelli
 *
 */
public interface Observer<C>  {
	public void update();
	public void update(C change);
	public void update(String communication);
}
