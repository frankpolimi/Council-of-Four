package cg2.observers;

/**
 * 
 * @author Emanuele Ricciardelli
 *
 */
public interface Observer {
	public void update();
	public <C> void update(C change);
}
