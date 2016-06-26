/**
 * 
 */
package model.bonus;

import java.util.ArrayList;
import java.util.List;

import controller.BonusChange;
import model.game.Emporium;
import model.game.Game;
import model.game.topology.City;
import view.View;

/**
 * @author Francesco Vetro'
 *
 */
public class CityBonus extends ActionBonus {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4800174510551680358L;

	/**
	 * constructor for a bonus that gives the player the bonus of a city
	 * where he has built an emporium
	 * @param repeat the amount of city's bonus that a player can obtain again
	 * @throws NullPointerException if repeat is null
	 * @throws IllegalArgumentException if repeat is not greater than zero
	 */
	public CityBonus (Integer repeat) throws NullPointerException, IllegalArgumentException{
		super(repeat);
	}
	
	public void setView(View view) {
		this.registerObserver(view);
	}

	/* (non-Javadoc)
	 * @see bonus.ActionBonus#update(java.lang.Object)
	 */
	/**
	 * get updated by the action that perform the changes in the model
	 * and will update the specific view of the player that must perform the input
	 * @param playerOrGame the player that will obtain the bonus
	 */
	@Override
	public void update(Game game) {
		BonusChange change=new BonusChange(this.amount);
		for(Emporium e : game.getCurrentPlayer().getEmporium()){
			if(super.checkNoNobility(e.getCity().getBonus())){
				change.addCityBonus(e.getCity());
			}
		}
		game.notifyObserver(game.getCurrentPlayer().getPlayerID(), change);
	}
	
	/* (non-Javadoc)
	 * @see bonus.ActionBonus#update(java.lang.Object)
	 */
	@Override
	public void update(){
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CityBonus" + super.toString();
	}
	
	

}
