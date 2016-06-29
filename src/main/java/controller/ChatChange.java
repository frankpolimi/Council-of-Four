package controller;

import model.game.Player;

public class ChatChange extends Change {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7083922426560549897L;
	private final String message;
	private final String ownersName;
	private final int id;
	
	public ChatChange(String message, String string, int id) {
		this.message=message;
		this.ownersName=string;
		this.id=id;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @return the ownersName
	 */
	public String getOwnersName() {
		return ownersName;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	
	
	

}
