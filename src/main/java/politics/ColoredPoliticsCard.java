package politics;

import java.awt.Color;

import council.Councillor;

/**
 * @author Vitaliy Pakholko
 */
public class ColoredPoliticsCard extends PoliticsCard 
{
	
	Color color;

	public ColoredPoliticsCard(Color color) {
		this.color = color;
	}

	public Color getColor() 
	{
		return color;
	}
	
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
