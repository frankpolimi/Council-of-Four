package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;
import model.actions.*;
import model.bonus.Bonus;
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
import view.ActionRequest;
import view.BonusRequest;
import view.LocalStorage;
import view.PermitsRequest;
import view.Request;

public class ClientOutHandlerSocket implements Runnable 
{
	private Request request; 
	private ObjectOutputStream socketOut;
	private LocalStorage memoryContainer;
	private Game game;

	public ClientOutHandlerSocket(ObjectOutputStream socketOut, LocalStorage container) {
		super();
		this.socketOut = socketOut;
		this.memoryContainer=container;
	}

	@Override
	public void run() 
	{
		Scanner stdin = new Scanner(System.in);
		while (true) 
		{
			String inputLine = this.start(stdin);
			if(!inputLine.equalsIgnoreCase("quit")){
				stdin.close();
				this.notify();
			}
			try {
				socketOut.writeObject(request);
				socketOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * first level of input where the player can choose what to do
	 * exceptional cases when the input required is a bonus or a permit
	 * that are sent by the server as a change due to a certain score on
	 * on the nobility lane
	 * @param stdin the standard input to input the selection
	 * @return 
	 */
	private String start(Scanner stdin) {
		int actionType;
		if(!memoryContainer.getBonus().isEmpty())
			this.bonus(stdin);
		else if(!memoryContainer.getPermits().isEmpty())
			this.permit(stdin);
		else{	
			System.out.println("Select the action type to perform");
			System.out.println("1. main action");
			System.out.println("2. quick action");
			System.out.println("3. pass to the next player");
			System.out.println("4. perform market action");
			System.out.println("5. quit");
			actionType=this.selector(1, 5, stdin);		
			switch (actionType) {
			case 1:
				this.mainAction(stdin);
				break;
			case 2:
				this.quickAction(stdin);
				break;
			case 3:
				request = new ActionRequest(new SkipQuickAction());
				break;
			case 4:
				this.market(stdin);
				break;
			case 5:
				return "quit";
			default:
				break;
			}
		}
		return "";
	}

	/**
	 * display the main actions and then guides the player through the 
	 * selection & the configuration of the action to send to the controller
	 * @param stdin the standard input for the different selections
	 */
	private void mainAction(Scanner stdin) {
		System.out.println("Select now the main action to perform");
		System.out.println("1. To Elect a councillor");
		System.out.println("2. To Acquire a Building Permit");
		System.out.println("3. To Build an Emporium by permit");
		System.out.println("4. To Build an Emporium under the consense of the king");
		int select = this.selector(1, 4, stdin);
	
		Action action = null;
		Player current=game.getCurrentPlayer();
		switch(select){
		case 1:
			//elect councillor
			System.out.println("Action Chosen: To Elect a councillor");
			System.out.println("These are the avaliable councillors");
			List<Councillor> councillors=game.getAvaliableCouncillor();
			for(int i=0;i<councillors.size();i++){
				System.out.println((i+1)+"- "+councillors.get(i).toString());
			}
			System.out.println("Select the councillor you would like");
			int councillorIndex=this.selector(1,councillors.size(), stdin);
			Councillor councillorSelected=councillors.get(councillorIndex-1);
			System.out.println("Now select the council");
			List<Council> councils= game.getAllCouncils();
			for(int i=0;i<councils.size();i++){
				System.out.println((i+1)+"- "+councils.get(i).getCouncillors());
			}
			int councilIndex=this.selector(1, councils.size(), stdin);
			Council council=councils.get(councilIndex-1);
			//action
			action=new ElectCouncillor(councillorSelected, council);
			break;
		case 2:
			//acquire permit
			System.out.println("Action Chosen: To Acquire a building permit");
			System.out.println("Select the four cards that you would to spend for corrupting a council");
			System.out.println("Your Cards:");
			List<PoliticsCard> ownedCards=current.getCardsOwned();
			ArrayList <PoliticsCard> selectedCards=new ArrayList<>();
			for(int i=0;i<ownedCards.size();i++){
				System.out.println((i+1)+"- "+ownedCards.get(i).toString());
			}
			for(int i=0;i<4;i++){
				int cardsIndex=this.selector(1, ownedCards.size(), stdin);
				PoliticsCard card=ownedCards.get(cardsIndex-1);
				selectedCards.add(card);
			}
			//select council
			List<RegionalCouncil> regionalCouncils=game.getRegionalCouncils();
			for(int i=0;i<regionalCouncils.size();i++){
				System.out.println((i+1)+"- "+regionalCouncils.get(i).getCouncillors());
			}
			councilIndex=this.selector(1, regionalCouncils.size(), stdin);
			RegionalCouncil councilCorrupted=regionalCouncils.get(councilIndex-1);
			System.out.println("Select the permit to acquire");
			PermitsDeck councilDeck=councilCorrupted.getPermitsDeck();
			Iterator<BuildingPermit> it=councilDeck.getFaceUpPermits().iterator();
			int i=1;
			while(it.hasNext()){
				System.out.println(i+"- "+it.next().toString());
				i++;
			}
			int permitIndex=this.selector(1, 2, stdin);
			BuildingPermit chosenPermit=councilDeck.giveAFaceUpPermit(permitIndex-1);
			action=new AcquirePermit(councilCorrupted, selectedCards, chosenPermit);
			break;
		case 3:
			//build emporium by permit
			System.out.println("Action Chosen: To Build an Emporium using an owned Permit");
			System.out.println("Select the building permit you want to use for building an emporium");
			System.out.println("Your permits:");
			if (current.getBuildingPermits().isEmpty()){
				System.out.println("You don't have any permit. You couldn't select this action");
				break;
			}
			for(i=0;i<current.getBuildingPermits().size();i++){
				System.out.println((i+1)+"- "+current.getBuildingPermits().get(i).toString());
			}
			permitIndex=this.selector(1, current.getBuildingPermits().size(), stdin);
			chosenPermit=current.getBuildingPermits().get(permitIndex-1);
			System.out.println("Now type the initial of the city in which you would to build");
			Iterator<City> cityIt=chosenPermit.getBuildingAvaliableCities().iterator();
			while(cityIt.hasNext()){
				City city=cityIt.next();
				System.out.println(city.getName()+" "+city.getFirstChar());
			}
			String initialChosen=stdin.next();
			City cityChosen=game.getMap().getVertexByKey(initialChosen);
			if(cityChosen==null){
				System.out.println("The input contains a not valid value");
			}else{
				action=new BuildEmporiumByPermit(chosenPermit, cityChosen);
			}

			break;
		case 4: 
			//build emporium by king
			System.out.println("Action Chosen: To Build an Emporium under the consense of the king");
			System.out.println("Select the four cards that you would to spend for corrupting the king's council");
			System.out.println("Your Cards:");
			ownedCards=current.getCardsOwned();
			selectedCards=new ArrayList<>();
			for(i=0;i<ownedCards.size();i++){
				System.out.println((i+1)+"- "+ownedCards.get(i).toString());
			}
			for(i=0;i<4;i++){
				int cardsIndex=this.selector(1, ownedCards.size(), stdin);
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
			initialChosen=stdin.next();
			cityChosen=game.getMap().getVertexByKey(initialChosen);
			if(cityChosen==null){
				System.out.println("The input contains a not valid value");
			}else{
				action=new BuildEmporiumByKing(kingsCouncil, selectedCards, cityChosen);
			}
			break;
		}
		request = new ActionRequest(action);
	}

	/**
	 * display the quick actions and then guides the player through the 
	 * selection & the configuration of the action to send to the controller
	 * @param stdin the standard input for the different selections
	 */
	private void quickAction(Scanner stdin) {
		System.out.println("Select now the quick action to perform");
		System.out.println("1. To Engage a new Assistant");
		System.out.println("2. To Change Face Up permits using an assistant");
		System.out.println("3. To Elect a councillor using an assistant");
		System.out.println("4. To obtain an extra main action");
		int select = this.selector(1, 4, stdin);
		
		Action action = null;
		switch(select){
		case 1:
			//engage assistant
			System.out.println("Action Chosen: To engage a new assistant");
			action=new EngageAssistant();
			break;
		case 2:
			//change face up permits
			System.out.println("Action Chosen: To change face up permits using an assistant");
			System.out.println("Select the deck you would to change");
			System.out.println("Decks Avaliable:");
			//select council
			List<RegionalCouncil> regionalCouncils=game.getRegionalCouncils();
			for(int i=0;i<regionalCouncils.size();i++){
				System.out.println((i+1)+"- "+regionalCouncils.get(i).getPermitsDeck().toString());
			}
			int deckIndex=this.selector(1, regionalCouncils.size(), stdin);
			PermitsDeck deckChosen=regionalCouncils.get(deckIndex-1).getPermitsDeck();
			action=new ChangeFaceUpPermits(deckChosen);
			break;
		case 3:
			//elect councillor by assistant
			System.out.println("Action Chosen: To Elect a councillor using an assistant");
			System.out.println("These are the avaliable councillors");
			List<Councillor> councillors=game.getAvaliableCouncillor();
			for(int i=0;i<councillors.size();i++){
				System.out.println((i+1)+"- "+councillors.get(i).toString());
			}
			System.out.println("Select the councillor you would like");
			int councillorIndex=this.selector(1,councillors.size(), stdin);
			Councillor councillorSelected=councillors.get(councillorIndex-1);
			List<Council> councils= game.getAllCouncils();
			for(int i=0;i<councils.size();i++){
				System.out.println((i+1)+"- "+councils.get(i).getCouncillors().toString());
			}
			int councilIndex=this.selector(1, councils.size(), stdin);
			Council council=councils.get(councilIndex-1);
			action=new ElectCouncillorByAssistant(council, councillorSelected);
			break;
		case 4: 
			//extra main action
			System.out.println("Action Chosen: To obtain an extra main action");
			action=new ExtraMainAction();
			break;
		}
		request = new ActionRequest(action);
	}

	private void market(Scanner stdin) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * display the permits and then guides the player through the 
	 * selection of the permit to send to the controller
	 * @param stdin the standard input for the different selections
	 */
	private void permit(Scanner stdin) {
		System.out.println("Select the permit you want to acquire");
		memoryContainer.getPermits().forEach(System.out::println);
		int selection=this.selector(1, memoryContainer.getPermitsLenght(), stdin);
		PermitsRequest request = new PermitsRequest();
		request.addPermit(memoryContainer.retrievePermit(selection-1));
		memoryContainer.setPermits(new ArrayList<BuildingPermit>());
	}

	/**
	 * display the bonuses and then guides the player through the 
	 * selection of the bonus to send to the controller
	 * @param stdin the standard input for the different selections
	 */
	private void bonus(Scanner stdin) {
		System.out.println("Select the bonus you want to acquire");
		memoryContainer.getBonus().forEach(System.out::println);
		int selection=this.selector(1, memoryContainer.getBonusLenght(), stdin);
		BonusRequest request = new BonusRequest();
		request.addBonus(memoryContainer.retrieveBonus(selection-1));
		memoryContainer.setBonus(new ArrayList<Bonus>());
	}

	private int selector(int min, int max, Scanner stdin){
		int selection=stdin.nextInt();
		while(selection<min||selection>max){
			System.out.println("The input cointains a not valid value. Please, try Again");
			selection=stdin.nextInt();
		}
		return selection;	
	}

	public Request getRequest() {
		return request;
	}

	public void setRequest(Request request) {
		this.request = request;
	}


}
