package view;

public class ChatRequest extends Request {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2629109355926571986L;
	private final String message;
	private final String name;
	
	public ChatRequest(String message,int ID, String name) {
		super(ID);
		this.message=message;
		this.name=name;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	
	

}
