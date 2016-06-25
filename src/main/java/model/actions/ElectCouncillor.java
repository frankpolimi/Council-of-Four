package model.actions;


import model.game.Game;
import model.game.council.Council;
import model.game.council.Councillor;
/**
 * @author Vitaliy Pakholko
 */
public class ElectCouncillor extends MainAction 
{
	/**
	 * We considered the 
	 */
	private static final long serialVersionUID = -3153051699153428116L;
	private Councillor councillor;
	private Council council;
	
	/**
	 * constructor for an action that allows the player to elect
	 * a councillor in a council (given both as parameters) 
	 * and gaining 4 coins
	 * @param council the council where the councillor will be elected
	 * @param councillor the councillor that will be elected
	 */
	public ElectCouncillor(Councillor councillor, Council council) {
		this.councillor = councillor;
		this.council = council;
	}
	
	/** 
	 * The player gains 4 coins by choosing a councillor to push in a given council
	 * @throws IllegalStateException if the player has no Main actions left
	 */
	@Override
	public boolean takeAction(Game game)
	{	
		if(!this.checkAction(game))
			throw new IllegalStateException("Not enough Main actions");
		if(!game.getAvaliableCouncillor().contains(councillor))
			throw new IllegalArgumentException("No such councillor in the game's pool");
		for(Council c:game.getAllCouncils())
		{
			if(c.equals(council)){
				game.addCouncillor(c.electCouncillor(councillor));
				game.getCurrentPlayer().setCoins(game.getCurrentPlayer().getCoins()+4);
				game.decrementMainActionCounter();
				game.getAvaliableCouncillor().remove(councillor);
				super.takeAction(game);
				return true;	
			}
		}
		throw new IllegalArgumentException("No such council in the game");
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ElectCouncillor: The Player chooses an avaiable Councillor and insersts him in a chosen Council shifting all the councillors already present. The pushed out Councillor returns"
				+ "to the board and the Player gets 4 Coins";
	}

	/**
	 * get the councillor that will be elected
	 * @return the councillor that will be elected
	 */
	public Councillor getCouncillor() {
		return councillor;
	}

	/**
	 * set the councillor that will be elected
	 * @param councillor the councillor that will be elected
	 */
	public void setCouncillor(Councillor councillor) {
		this.councillor = councillor;
	}

	/**
	 * get the council that will be modified
	 * @return the council that will be modified
	 */
	public Council getCouncil() {
		return council;
	}

	/**
	 * set the council that will be modified
	 * @param the council that will be modified
	 */
	public void setCouncil(Council council) {
		this.council = council;
	}

}
