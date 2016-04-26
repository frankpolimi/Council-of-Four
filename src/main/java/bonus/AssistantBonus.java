/**
 * add the specified amount of assistants to the player
 */
package bonus;

/**
 * @author Francesco Vetrò
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
		((Player) playerOrGame).getStatus().setHelper(
				((Player) playerOrGame).getStatus().getHelper() + this.getAmount());
		
	}

}
