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
import model.market.Assistant;
import model.market.MarketObject;

public class ClientView{
	
	private Game game;
	private LocalStorage storage;
	private int ID;
	
	public ClientView(Game game, LocalStorage memoryContainer, int ID) {
		this.game=game;
		this.storage = memoryContainer;
		this.ID = ID;
	}
	
	/**
	 * this method will return the player 
	 * with the same playerID as the socket out
	 * @return the player with the ID desired
	 */
	private Player getPlayerByID() {
		for(Player p: game.getPlayers())
			if(p.getPlayerID() == this.ID)
				return p;
		return null;
	}
	
	/**
	 * display the main actions and then guides the player through the 
	 * selection & the configuration of the action to send to the controller
	 * @param stdin the standard input for the different selections
	 */
	public Request mainAction(Scanner stdin) {
		System.out.println("Select now the main action to perform");
		System.out.println("1. To Elect a councillor");
		System.out.println("2. To Acquire a Building Permit");
		System.out.println("3. To Build an Emporium by permit");
		System.out.println("4. To Build an Emporium under the consense of the king");
		int select = this.selector(1, 4, stdin);
	
		Action action = null;
		switch(select){
		case 1:
			//elect councillor
			System.out.println("Action Chosen: To Elect a councillor");
			System.out.println("These are the avaliable councillors");
			List<Councillor> councillors=game.getAvaliableCouncillor();
			System.out.println("0- exit");
			for(int i=0;i<councillors.size();i++){
				System.out.println((i+1)+"- "+councillors.get(i).toString());
			}
			System.out.println("Select the councillor you would like");
			int councillorIndex=this.selector(0,councillors.size(), stdin);
			if(councillorIndex==0) break;
			Councillor councillorSelected=councillors.get(councillorIndex-1);
			System.out.println("Now select the council");
			List<Council> councils= game.getAllCouncils();
			System.out.println("0- exit");
			for(int i=0;i<councils.size();i++){
				System.out.println((i+1)+"- "+councils.get(i).getCouncillors());
			}
			int councilIndex=this.selector(0, councils.size(), stdin);
			if(councilIndex==0) break;
			Council council=councils.get(councilIndex-1);
			//action
			action=new ElectCouncillor(councillorSelected, council);
			return new ActionRequest(action, ID);
		case 2:
			//acquire permit
			System.out.println("Action Chosen: To Acquire a building permit");
			
			System.out.println("Your Cards:");
			List<PoliticsCard> ownedCards=getPlayerByID().getCardsOwned();
			ArrayList <PoliticsCard> selectedCards=new ArrayList<>();
			int i;
			for(i=0;i<ownedCards.size();i++){
				System.out.println((i+1)+"- "+ownedCards.get(i).toString());
			}
			System.out.println("Select the cards that you would to spend for corrupting a council - max 4 cards");
			System.out.println("Select 0 if you want to change main action");
			System.out.println("Insert -1 if you want to terminate this drawing step");
			i=0;
			int cardsIndex=this.selector(-1, ownedCards.size(), stdin);
			while(cardsIndex!=0&&cardsIndex!=-1&&i<4){
				PoliticsCard card=ownedCards.get(cardsIndex-1);
				selectedCards.add(card);
				i++;
				cardsIndex=this.selector(-1, ownedCards.size(), stdin);
			}
			if(cardsIndex==0) break;
			//select council
			List<RegionalCouncil> regionalCouncils=game.getRegionalCouncils();
			System.out.println("0- exit");
			for(i=0;i<regionalCouncils.size();i++){
				System.out.println((i+1)+"- "+regionalCouncils.get(i).getCouncillors());
			}
			councilIndex=this.selector(0, regionalCouncils.size(), stdin);
			if(councilIndex==0) break;
			RegionalCouncil councilCorrupted=regionalCouncils.get(councilIndex-1);
			System.out.println("Select the permit to acquire");
			PermitsDeck councilDeck=councilCorrupted.getPermitsDeck();
			Iterator<BuildingPermit> it=councilDeck.getFaceUpPermits().iterator();
			i=1;
			while(it.hasNext()){
				System.out.println(i+"- "+it.next().toString());
				i++;
			}
			int permitIndex=this.selector(1, 2, stdin);
			BuildingPermit chosenPermit=councilDeck.giveAFaceUpPermit(permitIndex-1);
			action=new AcquirePermit(councilCorrupted, selectedCards, chosenPermit);
			return new ActionRequest(action, ID);
		case 3:
			//build emporium by permit
			System.out.println("Action Chosen: To Build an Emporium using an owned Permit");
			System.out.println("Select the building permit you want to use for building an emporium");
			System.out.println("Your permits:");
			if (getPlayerByID().getBuildingPermits().isEmpty()){
				System.out.println("You don't have any permit. You couldn't select this action");
				break;
			}
			System.out.println("0- exit");
			for(i=0;i<getPlayerByID().getBuildingPermits().size();i++){
				System.out.println((i+1)+"- "+getPlayerByID().getBuildingPermits().get(i).toString());
			}
			permitIndex=this.selector(0, getPlayerByID().getBuildingPermits().size(), stdin);
			if(permitIndex==0) break;
			chosenPermit=getPlayerByID().getBuildingPermits().get(permitIndex-1);
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
				break;
			}else{
				action=new BuildEmporiumByPermit(chosenPermit, cityChosen);
			}
			return new ActionRequest(action, ID);
		case 4: 
			//build emporium by king
			System.out.println("Action Chosen: To Build an Emporium under the consense of the king");
			System.out.println("Your Cards:");
			ownedCards=getPlayerByID().getCardsOwned();
			selectedCards=new ArrayList<>();
			for(i=0;i<ownedCards.size();i++){
				System.out.println((i+1)+"- "+ownedCards.get(i).toString());
			}
			System.out.println("Select the cards that you would to spend for corrupting the king's council - MAX 4 cards");
			System.out.println("Select 0 if you want to change the main action");
			System.out.println("Select -1 if you want to terminate the drawing step and to confirm your selected cards");
			i=0;
			cardsIndex=this.selector(-1, ownedCards.size(), stdin);
			while(cardsIndex!=0&&cardsIndex!=-1&&i<4){
				PoliticsCard card=ownedCards.get(cardsIndex-1);
				selectedCards.add(card);
				i++;
				cardsIndex=this.selector(-1, ownedCards.size(), stdin);
			}
			if(cardsIndex==0) break;
			KingsCouncil kingsCouncil=game.getKingsCouncil();
			System.out.println("The king is now in the city: ");
			System.out.println(game.getKingsPosition().getName());
			System.out.println("In which city would you build? Insert the initial");
			System.out.println("Type a character not present in the list below to change the action");
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
				break;
			}else{
				action=new BuildEmporiumByKing(kingsCouncil, selectedCards, cityChosen);
			}
			return new ActionRequest(action, ID);
		}
		return null;
	}

	/**
	 * display the quick actions and then guides the player through the 
	 * selection & the configuration of the action to send to the controller
	 * @param stdin the standard input for the different selections
	 */
	public Request quickAction(Scanner stdin) {
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
			return new ActionRequest(action, ID);
		case 2:
			//change face up permits
			System.out.println("Action Chosen: To change face up permits using an assistant");
			System.out.println("Decks Avaliable:");
			//select council
			List<RegionalCouncil> regionalCouncils=game.getRegionalCouncils();
			for(int i=0;i<regionalCouncils.size();i++){
				System.out.println((i+1)+"- "+regionalCouncils.get(i).getPermitsDeck().getFaceUpPermits().toString());
			}
			System.out.println("Select the deck you would to change");
			System.out.println("Select 0 if you want to change your action");
			int deckIndex=this.selector(0, regionalCouncils.size(), stdin);
			if(deckIndex==0) break;
			PermitsDeck deckChosen=regionalCouncils.get(deckIndex-1).getPermitsDeck();
			action=new ChangeFaceUpPermits(deckChosen);
			return new ActionRequest(action, ID);
		case 3:
			//elect councillor by assistant
			System.out.println("Action Chosen: To Elect a councillor using an assistant");
			System.out.println("These are the avaliable councillors");
			List<Councillor> councillors=game.getAvaliableCouncillor();
			
			for(int i=0;i<councillors.size();i++){
				System.out.println((i+1)+"- "+councillors.get(i).toString());
			}
			System.out.println("Select the councillor you would like");
			System.out.println("Select 0 if you want to change the action");
			int councillorIndex=this.selector(0,councillors.size(), stdin);
			if(councillorIndex==0) break;
			Councillor councillorSelected=councillors.get(councillorIndex-1);
			List<Council> councils= game.getAllCouncils();
			System.out.println("These are the avaliable councils");
			for(int i=0;i<councils.size();i++){
				System.out.println((i+1)+"- "+councils.get(i).getCouncillors().toString());
			}
			System.out.println("Select the council in which you want to elect the councillor");
			System.out.println("Select 0 if you want to change the action");
			int councilIndex=this.selector(0, councils.size(), stdin);
			if(councilIndex==0) break;
			Council council=councils.get(councilIndex-1);
			action=new ElectCouncillorByAssistant(council, councillorSelected);
			return new ActionRequest(action, ID);
		case 4: 
			//extra main action
			System.out.println("Action Chosen: To obtain an extra main action");
			action=new ExtraMainAction();
			return new ActionRequest(action, ID);
		}
		return null;
	}
	
	/**
	 * this method generate the request of buying an object within
	 * the market by displaying and selecting the object
	 * @param stdin the standard input
	 */
	public Request buyProducts(Scanner stdin) {
		System.out.println("These are the object for sale now!");
		game.getMarket().getAvailableProducts(getPlayerByID());
		System.out.println("Are you interested from something? Y/N");
		String answer=stdin.nextLine();
		if(answer.equalsIgnoreCase("N")){
			System.out.println("Ok, the turn passes to the next one player");
		}else if(answer.equalsIgnoreCase("Y")){
			System.out.println("Select the product you want to buy");
			int selection = this.selector(1, 
					game.getMarket().getLengthAvailableProducts(getPlayerByID()), stdin);
			return new MarketRequest<>(
					game.getMarket().getAvailableProducts(getPlayerByID()).get(selection));
		}else{
			System.out.println("You are insert a not valid value");
		}
		return null;
	}

	/**
	 * this method will generate a request to sell an object
	 * and put it available in the market for other players
	 * @param stdin the standard input
	 */
	public Request addProduct(Scanner stdin) {
		int i;
		System.out.println("ADD A PRODUCT");
		System.out.println("These are the product you could sell");
		System.out.println("POLITIC CARDS");
		for(i=0;i<getPlayerByID().getCardsOwned().size();i++){
			System.out.println("pc"+(i+1)+"- "+getPlayerByID().getCardsOwned().get(i).toString());
		}
		System.out.println("BUILDING PERMITS NOT USED");
		for(int j=0;j<getPlayerByID().getBuildingPermits().size();j++){
			System.out.println("bp"+(j+1)+"- "+getPlayerByID().getBuildingPermits().get(j).toString());
		}
		System.out.println("ASSISTANTS avaliable "+getPlayerByID().getAssistants());
		System.out.println("Now select the category of object that you are interested");
		System.out.println("1. Politic Cards");
		System.out.println("2. Building Permits");
		System.out.println("3. Assistants");
		int catIndex=this.selector(1, 3, stdin);
		switch(catIndex){
		case 1: 
			System.out.println("Insert the number of politic card that interested you");
			int cardIndex=this.selector(1, getPlayerByID().getCardsOwned().size(), stdin);
			PoliticsCard card=getPlayerByID().getCardsOwned().get(cardIndex-1);
			int price=this.priceInsertion(stdin);
			game.getMarket().addProduct(new MarketObject<PoliticsCard>(card, getPlayerByID(), price));
			return new MarketRequest<PoliticsCard>(
					new MarketObject<PoliticsCard>(card, getPlayerByID(), price));
		case 2:
			System.out.println("Insert the number of building permit that interested you");
			int permitIndex=this.selector(1, getPlayerByID().getBuildingPermits().size(), stdin);
			BuildingPermit permit=getPlayerByID().getBuildingPermits().get(permitIndex-1);
			price=this.priceInsertion(stdin);
			return new MarketRequest<BuildingPermit>(
					new MarketObject<BuildingPermit>(permit, getPlayerByID(), price));
		case 3:
			System.out.println("Insert the amount of assistant you would sell");
			int assistantNumber=this.selector(1, getPlayerByID().getAssistants(), stdin);
			System.out.println("Insert the price");
			price=this.priceInsertion(stdin);
			Assistant assistant= new Assistant(assistantNumber);
			return new MarketRequest<Assistant>(
					new MarketObject<Assistant>(assistant, getPlayerByID(), price));
		}
		return null;
	}

	/**
	 * method that allows to enter a price for a specific
	 * object in the market
	 * @param stdin
	 * @return the price of the object
	 */
	private int priceInsertion(Scanner stdin) {
		System.out.println("Insert the price");
		int price=stdin.nextInt();
		while(price<0){
			System.out.println("You cannot insert a negative value of price, try again");
		}
		return price;
	}

	/**
	 * display the permits and then guides the player through the 
	 * selection of the permit to send to the controller
	 * @param stdin the standard input for the different selections
	 */
	public Request permit(Scanner stdin) {
		System.out.println("Select the permit you want to acquire");
		storage.getPermits().forEach(System.out::println);
		int selection=this.selector(1, storage.getPermitsLenght(), stdin);
		PermitsRequest request = new PermitsRequest(this.ID);
		request.addPermit(storage.retrievePermit(selection-1));
		storage.setPermits(new ArrayList<BuildingPermit>());
		return request;
	}

	/**
	 * display the bonuses and then guides the player through the 
	 * selection of the bonus to send to the controller
	 * @param stdin the standard input for the different selections
	 */
	public Request bonus(Scanner stdin) {
		System.out.println("Select the bonus you want to acquire");
		storage.getBonus().forEach(System.out::println);
		int selection=this.selector(1, storage.getBonusLenght(), stdin);
		BonusRequest request = new BonusRequest(this.ID);
		request.addBonus(storage.retrieveBonus(selection-1));
		storage.setBonus(new ArrayList<Bonus>());
		return request;
	}

	/**
	 * method to perform the selection & control between a 
	 * min value and a max value via the standard input
	 * @param min the minimum value to input
	 * @param max the maximum value to input
	 * @param stdin the standard input
	 * @returnn the integer selected to use in methods
	 */
	public int selector(int min, int max, Scanner stdin){
		int selection=stdin.nextInt();
		while(selection<min||selection>max){
			System.out.println("The input cointains a not valid value. Please, try Again");
			selection=stdin.nextInt();
		}
		return selection;	
	}
	
	private void stampModel(){
		System.out.println("GAME");
		System.out.println(game.toString());
	}
	
	private Game getGame() {
		return game;
	}
	
	
	/*
	 * TODO move to server view
	 * 
	@Override
	public void update() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(String communication) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(Change change) {
		if(change.getClass().equals(StateChange.class))
			this.setState(((StateChange)change).getStateChanged());
		else if(change.getClass().equals(BonusChange.class))
			this.setState(new BonusState(
					((BonusChange)change).getBonusList()));
		else if(change.getClass().equals(PermitsChange.class))
			this.setState(new PermitsState(
					((PermitsChange)change).getPermits()));
	}
	*/
}
