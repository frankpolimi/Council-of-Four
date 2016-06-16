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
	private String imagePath;

	public Councillor(Color color, String imagePath)
	{
		if(color!=null&&imagePath!=null){
			this.color = color;
			this.imagePath=imagePath;
		}else
			throw new NullPointerException();
	}
	
	public Councillor(Color color)
	{
		if(color!=null){
			this.color = color;
		
		}else
			throw new NullPointerException();
	}
	
	
	/**
	 * @return the imagePath
	 */
	public String getImagePath() {
		return imagePath;
	}



	public Color getColor() 
	{
		return color;
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
		Councillor other = (Councillor) obj;
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Councillor [color=" + color + "]\n";
	}
	
	
}
