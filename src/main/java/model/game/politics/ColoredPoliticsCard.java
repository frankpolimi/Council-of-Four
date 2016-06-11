package model.game.politics;

import java.awt.Color;

import model.game.council.Councillor;

/**
 * @author Vitaliy Pakholko
 */
public class ColoredPoliticsCard extends PoliticsCard 
{
	
	private static final long serialVersionUID = 8208852678516588048L;
	private final Color color;

	public ColoredPoliticsCard(Color color) 
	{
		if(color!=null)
			this.color = color;
		else
			throw new NullPointerException();
	}

	public Color getColor() 
	{
		return color;
	}
	
	@Override
	public boolean payCouncillor(Councillor councillor)
	{
		if (councillor.getColor().equals(this.color))
			return true;
		return false;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return color.toString();
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
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
		ColoredPoliticsCard other = (ColoredPoliticsCard) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		return true;
	}

}
