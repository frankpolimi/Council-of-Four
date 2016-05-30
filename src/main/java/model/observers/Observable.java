/**
 * 
 */
package model.observers;
import java.util.*;

/**
 * @author Emanuele Ricciardelli
 *
 */
public abstract class Observable<C> {
	private List<Observer<C>> observers;

	public Observable(){
		observers=new ArrayList<>();
	}
	
	public void registerObserver(Observer<C> o){
		this.observers.add(o);
	}
	
	public void unregisterObserver(Observer<C> o){
		this.observers.remove(o);
	}
	
	public void notifyObservers(){
		for(Observer<C> o: this.observers){
			o.update();
		}
	}
	
	public void notifyObserver(int specificView, C c){
		this.observers.get(specificView).update(c);
	}
	
	public void notifyObservers(C c){
		for(Observer<C> o: this.observers){
			o.update(c);
		}
	}
	
}
	