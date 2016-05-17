/**
 * 
 */
package cg2.observers;
import java.util.*;

/**
 * @author Emanuele Ricciardelli
 *
 */
public abstract class Observable<C> {
	private List<Observer> observers;

	public Observable(){
		observers=new ArrayList<>();
	}
	
	public void registerObserver(Observer o){
		this.observers.add(o);
	}
	
	public void unregisterObserver(Observer o){
		this.observers.remove(o);
	}
	
	public void notifyObservers(){
		for(Observer o: this.observers){
			o.update();
		}
	}
	
	public void notifyObserver(int specificView, C c){
		this.observers.get(specificView).update(c);
	}
	
	public void notifyObservers(C c){
		for(Observer o: this.observers){
			o.update(c);
		}
	}
	
	public void notifyObservers(String string){
		for(Observer o: this.observers)
			o.update(string);
	}
}
	