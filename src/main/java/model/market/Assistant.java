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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + number;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Assistant other = (Assistant) obj;
		if (number != other.number)
			return false;
		return true;
	}
	
}
