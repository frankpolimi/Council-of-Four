/**
 * 
 */
package view;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

import model.actions.AcquirePermit;
import model.actions.Action;
import model.actions.BuildEmporiumByKing;
import model.actions.BuildEmporiumByPermit;
import model.actions.ChangeFaceUpPermits;
import model.actions.ElectCouncillor;
import model.actions.ElectCouncillorByAssistant;
import model.actions.EngageAssistant;
import model.actions.ExtraMainAction;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.PermitsDeck;
import model.game.Player;
import model.game.council.Council;
import model.game.council.Councillor;
import model.game.council.KingsCouncil;
import model.game.council.RegionalCouncil;
import model.game.politics.PoliticsCard;
import model.game.topology.City;
/**
 * @author Francesco Vetr�
 *
 */
public class ActionState implements State {
	
	private final int type;
	private final int select;
	private Game game;
	private Scanner scanner = new Scanner(System.in);
	
	public ActionState(int type, int sel, Game game) {
		this.type = type;
		this.select = sel;
		this.game = game;
	}

	/* (non-Javadoc)
	 * @see cg2.view.State#display()
	 */
	/**
	 * 
	 */
	@Override
	public void display() {
		

		//TODO send to server
		//this.notifyObservers(new ActionChange(1, action));

	}
}
