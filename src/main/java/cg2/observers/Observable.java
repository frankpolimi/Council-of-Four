/**
 * 
 */
package cg2.observers;
import java.util.*;

/**
 * @author Emanuele Ricciardelli
 *
 */
public abstract class Observable {
	private List<Observer> observers;

	public Observable(){
		observers=new ArrayList<Observer>();
	}
	
	public void registerObserver(Observer o){
		observers.add(o);
	}
	
	public void unregisterObserver(Observer o){
		this.observers.remove(o);
	}
	
	public void notifyObservers(){
		for(Observer o: this.observers){
			o.update();
		}
	}
	
	public <C> void notifyObservers(C c){
		for(Observer o: this.observers){
			o.update(c);
		}
	}
}
	