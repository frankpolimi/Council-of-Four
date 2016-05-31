package model.game.council;
import java.awt.*;
import java.io.Serializable;

/**
 * @author Vitaliy Pakholko
 */
public class Councillor implements Serializable
{
	private static final long serialVersionUID = 8744419618457004020L;
	private final Color color;

	public Councillor(Color color)
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

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Councillor [color=" + color + "]\n";
	}
	
	
}
