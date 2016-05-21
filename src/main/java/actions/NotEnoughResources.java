package actions;

public class NotEnoughResources extends Exception {

	private static final long serialVersionUID = 5177951604868537695L;
	private String message;
	
	public NotEnoughResources() {}
	
	public NotEnoughResources(String s) 
	{
		this.message=s;
	}
	
	@Override
	public String toString()
	{
		return this.message;
	}

}
