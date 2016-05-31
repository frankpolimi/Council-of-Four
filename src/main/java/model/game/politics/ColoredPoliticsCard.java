package model.game.politics;

import java.awt.Color;

import model.game.council.Councillor;

/**
 * @author Vitaliy Pakholko
 */
public class ColoredPoliticsCard extends PoliticsCard 
{
	
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
		if (councillor.getColor()==this.color)
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
