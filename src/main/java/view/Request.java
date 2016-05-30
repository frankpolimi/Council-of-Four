/**
 * 
 */
package view;

import java.io.Serializable;


/**
 * @author francesco
 *
 */
public abstract class Request implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8272737714020599087L;

	private final int ID;
	
	public Request(int ID) {
		this.ID=ID;
	} 
	
	public int getPlayer() {
		return ID;
	}
}
