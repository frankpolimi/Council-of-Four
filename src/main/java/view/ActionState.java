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
		//devo inizializzare action
		Action action = null;
		Player current=game.getCurrentPlayer();
		if(type==1){
			switch(select){
			case 1://elect councillor
				System.out.println("Action Chosen: To Elect a councillor");
				System.out.println("These are the avaliable councillors");
				List<Councillor> councillors=game.getAvaliableCouncillor();
				for(int i=0;i<councillors.size();i++){
					System.out.println((i+1)+"- "+councillors.get(i).toString());
				}
				System.out.println("Select the councillor you would like");
				int councillorIndex=this.selector(1,councillors.size());
				Councillor councillorSelected=councillors.get(councillorIndex-1);
				System.out.println("Now select the council");
				List<Council> councils= game.getAllCouncils();
				for(int i=0;i<councils.size();i++){
					System.out.println((i+1)+"- "+councils.get(i).getCouncillors());
				}
				int councilIndex=this.selector(1, councils.size());
				Council council=councils.get(councilIndex-1);
				//action
				action=new ElectCouncillor(councillorSelected, council);
				break;
			case 2://acquire permit
				System.out.println("Action Chosen: To Acquire a building permit");
				System.out.println("Select the four cards that you would to spend for corrupting a council");
				System.out.println("Your Cards:");
				List<PoliticsCard> ownedCards=current.getCardsOwned();
				ArrayList <PoliticsCard> selectedCards=new ArrayList<>();
				for(int i=0;i<ownedCards.size();i++){
					System.out.println((i+1)+"- "+ownedCards.get(i).toString());
				}
				for(int i=0;i<4;i++){
					int cardsIndex=this.selector(1, ownedCards.size());
					PoliticsCard card=ownedCards.get(cardsIndex-1);
					selectedCards.add(card);
				}
				//select council
				List<RegionalCouncil> regionalCouncils=game.getRegionalCouncils();
				for(int i=0;i<regionalCouncils.size();i++){
					System.out.println((i+1)+"- "+regionalCouncils.get(i).getCouncillors());
				}
				councilIndex=this.selector(1, regionalCouncils.size());
				RegionalCouncil councilCorrupted=regionalCouncils.get(councilIndex-1);
				System.out.println("Select the permit to acquire");
				PermitsDeck councilDeck=councilCorrupted.getPermitsDeck();
				Iterator<BuildingPermit> it=councilDeck.getFaceUpPermits().iterator();
				int i=1;
				while(it.hasNext()){
					System.out.println(i+"- "+it.next().toString());
					i++;
				}
				int permitIndex=this.selector(1, 2);
				BuildingPermit chosenPermit=councilDeck.giveAFaceUpPermit(permitIndex-1);
				action=new AcquirePermit(councilCorrupted, selectedCards, chosenPermit);
				break;
			case 3:
				System.out.println("Action Chosen: To Acquire a building permit");
				System.out.println("Select the building permit you want to use for building an emporium");
				System.out.println("Your permits:");
				if (current.getBuildingPermits().isEmpty()){
					System.out.println("You don't have any permit. You couldn't select this action");
					break;
				}
				for(i=0;i<current.getBuildingPermits().size();i++){
					System.out.println((i+1)+"- "+current.getBuildingPermits().get(i).toString());
				}
				permitIndex=this.selector(1, current.getBuildingPermits().size());
				chosenPermit=current.getBuildingPermits().get(permitIndex-1);
				System.out.println("Now type the initial of the city in which you would to build");
				Iterator<City> cityIt=chosenPermit.getBuildingAvaliableCities().iterator();
				while(cityIt.hasNext()){
					City city=cityIt.next();
					System.out.println(city.getName()+" "+city.getFirstChar());
				}
				String initialChosen=scanner.next();
				City cityChosen=game.getMap().getVertexByKey(initialChosen);
				if(cityChosen==null){
					System.out.println("The input contains a not valid value");
				}else{
					action=new BuildEmporiumByPermit(chosenPermit, cityChosen);
				}

				break;
			case 4: 
				System.out.println("Action Chosen: To Build an Emporium under the consense of the king");
				System.out.println("Select the four cards that you would to spend for corrupting the king's council");
				System.out.println("Your Cards:");
				ownedCards=current.getCardsOwned();
				selectedCards=new ArrayList<>();
				for(i=0;i<ownedCards.size();i++){
					System.out.println((i+1)+"- "+ownedCards.get(i).toString());
				}
				for(i=0;i<4;i++){
					int cardsIndex=this.selector(1, ownedCards.size());
					PoliticsCard card=ownedCards.get(cardsIndex-1);
					selectedCards.add(card);
				}
				KingsCouncil kingsCouncil=game.getKingsCouncil();
				System.out.println("The king is now in the city: ");
				System.out.println(game.getKingsPosition().getName());
				System.out.println("In which city would you build? Insert the initial");
				i=1;
				for(City c:game.getMap().vertexSet()){
					System.out.println(c.getName()+" "+c.getFirstChar());
					System.out.println("Bonuses");
					System.out.println(c.getBonus());
					i++;
				}
				initialChosen=scanner.next();
				cityChosen=game.getMap().getVertexByKey(initialChosen);
				if(cityChosen==null){
					System.out.println("The input contains a not valid value");
				}else{
					action=new BuildEmporiumByKing(kingsCouncil, selectedCards, cityChosen);
				}
				break;
			}
		}else{
			switch(select){
			case 1:
				System.out.println("Action Chosen: To engage a new assistant");
				action=new EngageAssistant();
				break;
			case 2:
				System.out.println("Action Chosen: To change face up permits using an assistant");
				System.out.println("Select the deck you would to change");
				System.out.println("Decks Avaliable:");
				//select council
				List<RegionalCouncil> regionalCouncils=game.getRegionalCouncils();
				for(int i=0;i<regionalCouncils.size();i++){
					System.out.println((i+1)+"- "+regionalCouncils.get(i).getPermitsDeck().toString());
				}
				int deckIndex=this.selector(1, regionalCouncils.size());
				PermitsDeck deckChosen=regionalCouncils.get(deckIndex-1).getPermitsDeck();
				action=new ChangeFaceUpPermits(deckChosen);
				break;
			case 3:
				System.out.println("Action Chosen: To Elect a councillor using an assistant");
				System.out.println("These are the avaliable councillors");
				List<Councillor> councillors=game.getAvaliableCouncillor();
				for(int i=0;i<councillors.size();i++){
					System.out.println((i+1)+"- "+councillors.get(i).toString());
				}
				System.out.println("Select the councillor you would like");
				int councillorIndex=this.selector(1,councillors.size());
				Councillor councillorSelected=councillors.get(councillorIndex-1);
				List<Council> councils= game.getAllCouncils();
				for(int i=0;i<councils.size();i++){
					System.out.println((i+1)+"- "+councils.get(i).getCouncillors().toString());
				}
				int councilIndex=this.selector(1, councils.size());
				Council council=councils.get(councilIndex-1);
				action=new ElectCouncillorByAssistant(council, councillorSelected);//modularizzare con la prima delle main action
				break;
			case 4: 
				System.out.println("Action Chosen: To obtain an extra main action");
				action=new ExtraMainAction();
				break;
			}
		}

		//TODO send to server
		//this.notifyObservers(new ActionChange(1, action));

	}
}
