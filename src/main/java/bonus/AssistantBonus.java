/**
 * add the specified amount of assistants to the player
 */
package bonus;

import cg2.player.*;

/**
 * @author Francesco Vetr�
 *
 */
public class AssistantBonus extends TileBonus {

	public AssistantBonus(Integer aiutanti) {
		super(aiutanti);
	}

	/**
	 * update the amount of assistants of the player as parameter
	 * by the amount specified
	 * @param player
	 */
	@Override
	public <T> void update(T playerOrGame) {
		((Player) playerOrGame).getStatus().setAssistants(
				((Player) playerOrGame).getStatus().getAssistants() + this.getAmount());
		
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
