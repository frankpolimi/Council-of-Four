/**
 * 
 */
package model.observers;
import java.util.*;

/**
 * @author Emanuele Ricciardelli
 * 
 * class implementing the observable 
 * component of the pattern observer-observable
 */
public abstract class Observable<C> {
	private List<Observer<C>> observers;

	/**
	 * constructor for the list of 
	 * observer that will be updated if a change
	 * happens within an observable object
	 */
	public Observable(){
		observers=new ArrayList<>();
	}
	
	/**
	 * register an observer
	 * @param o the observer to register
	 */
	public void registerObserver(Observer<C> o){
		this.observers.add(o);
	}
	
	/**
	 * unregister an observer
	 * @param o the observer to unregister
	 */
	public void unregisterObserver(Observer<C> o){
		this.observers.remove(o);
	}
	
	/**
	 * notify all the observers
	 */
	public void notifyObservers(){
		for(Observer<C> o: this.observers){
			o.update();
		}
	}
	
	/**
	 * method to notify a specific observer
	 * @param specificView the index of the specific observer
	 * @param c the generic change to be notified
	 */
	public void notifyObserver(int specificView, C c){
		this.observers.get(specificView-1).update(c);
	}
	
	/**
	 * notify all observers
	 * @param c the change to notify
	 */
	public void notifyObservers(C c){
		for(Observer<C> o: this.observers){
			o.update(c);
		}
	}
	
}
	