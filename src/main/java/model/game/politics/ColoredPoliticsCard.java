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
	
	
	
}
