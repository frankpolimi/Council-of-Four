/**
 * add the specified amount of assistants to the player
 */
package model.bonus;

import model.game.Game;

/**
 * @author Francesco Vetrò
 *
 */
public class AssistantBonus extends TileBonus {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7236019783850791384L;

	public AssistantBonus(Integer amount) {
		super(amount);
	}

	/**
	 * update the amount of assistants of the player as parameter
	 * by the amount specified
	 * @param player
	 */
	@Override
	public void update(Game game) {
		game.getCurrentPlayer().setAssistants(
				game.getCurrentPlayer().getAssistants() + this.getAmount());
		
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AssistantBonus: "+super.toString();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}
	
	

}
