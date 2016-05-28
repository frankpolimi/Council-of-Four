package model.market;

import java.io.Serializable;

public class Assistant implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8084595231388031075L;
	private final int number;
	
	public Assistant(int number) {
		this.number = number;
	}

	/**
	 * @return the number of assistants
	 */
	public int getNumber() {
		return number;
	}

}
