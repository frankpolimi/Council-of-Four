package council;
import java.awt.*;

/**
 * @author Vitaliy Pakholko
 */
public class Councillor
{
	private final Color color;

	public Color getColor() 
	{
		return color;
	}

	public Councillor(Color color)
	{
		this.color = color;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Councillor [color=" + color + "]";
	}
	
	
}
