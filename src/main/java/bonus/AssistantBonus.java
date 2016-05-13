/**
 * add the specified amount of assistants to the player
 */
package bonus;

import cg2.player.*;

/**
 * @author Francesco Vetrò
 *
 */
public class AssistantBonus extends TileBonus {

	public AssistantBonus(Integer amount) {
		super(amount);
	}

	/**
	 * update the amount of assistants of the player as parameter
	 * by the amount specified
	 * @param player
	 */
	@Override
	public <T> void update(T playerOrGame) {
		((Player) playerOrGame).setAssistants(
				((Player) playerOrGame).getAssistants() + this.getAmount());
		
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
