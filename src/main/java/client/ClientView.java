package client;

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
import model.actions.SkipAction;
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
import view.ActionRequest;
import view.BonusRequest;
import view.EndState;
import view.LocalStorage;
import view.MarketBuyingState;
import view.MarketRequest;
import view.MarketSellingState;
import view.PermitsRequest;
import view.QuitRequest;
import view.Request;
import view.StartState;
@SuppressWarnings("resource")
public class ClientView implements ClientViewInterface{
	
	private Game game;
	private LocalStorage memoryContainer;
	private int ID;
	
	@Override
	public void setMemoryContainer(LocalStorage memoryContainer) {
		this.memoryContainer = memoryContainer;
	}
	
	@Override
	public void setGame(Game game) {
		this.game = game;
	}
	
	@Override
	public int getId() {
		return ID;
	}
	
	@Override
	public void setId(int id) {
		this.ID=id;
	}
	
	/**
	 * display the main actions and then guides the player through the 
	 * selection & the configuration of the action to send to the controller
	 */
	public Request mainAction() {
		Scanner scanner1 = new Scanner(System.in);
		System.out.println("Select now the main action to perform");
		System.out.println("1. To Elect a councillor");
		System.out.println("2. To Acquire a Building Permit");
		System.out.println("3. To Build an Emporium by permit");
		System.out.println("4. To Build an Emporium under the consense of the king");
		int select = this.selector(1, 4);
	
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
			int councillorIndex=this.selector(0,councillors.size());
			if(councillorIndex==0) break;
			Councillor councillorSelected=councillors.get(councillorIndex-1);
			System.out.println("Now select the council");
			List<Council> councils= game.getAllCouncils();
			System.out.println("0- exit");
			for(int i=0;i<councils.size();i++){
				System.out.println((i+1)+"- "+councils.get(i).getCouncillors());
			}
			int councilIndex=this.selector(0, councils.size());
			if(councilIndex==0) break;
			Council council=councils.get(councilIndex-1);
			//action
			action=new ElectCouncillor(councillorSelected, council);
			return new ActionRequest(action, ID);
		case 2:
			//acquire permit
			System.out.println("Action Chosen: To Acquire a building permit");
			
			System.out.println("Your Cards:");
			List<PoliticsCard> ownedCards=game.getPlayerByID(this.ID).getCardsOwned();
			ArrayList <PoliticsCard> selectedCards=new ArrayList<>();
			int i;
			for(i=0;i<ownedCards.size();i++){
				System.out.println((i+1)+"- "+ownedCards.get(i).toString());
			}
			System.out.println("Select the cards that you would to spend for corrupting a council - max 4 cards");
			System.out.println("Select 0 if you want to change main action");
			System.out.println("Insert -1 if you want to terminate this drawing step");
			i=1;
			int cardsIndex=this.selector(-1, ownedCards.size());
			while(cardsIndex!=0&&cardsIndex!=-1&&i<4){
				PoliticsCard card=ownedCards.get(cardsIndex-1);
				selectedCards.add(card);
				i++;
				cardsIndex=this.selector(-1, ownedCards.size());
			}
			if(cardsIndex==0) break;
			//select council
			List<RegionalCouncil> regionalCouncils=game.getRegionalCouncils();
			System.out.println("0- exit");
			for(i=0;i<regionalCouncils.size();i++){
				System.out.println((i+1)+"- "+regionalCouncils.get(i).getCouncillors());
			}
			councilIndex=this.selector(0, regionalCouncils.size());
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
			int permitIndex=this.selector(1, 2);
			BuildingPermit chosenPermit=councilDeck.giveAFaceUpPermit(permitIndex);
			action=new AcquirePermit(councilCorrupted, selectedCards, chosenPermit);
			return new ActionRequest(action, ID);
		case 3:
			//build emporium by permit
			System.out.println("Action Chosen: To Build an Emporium using an owned Permit");
			System.out.println("Select the building permit you want to use for building an emporium");
			System.out.println("Your permits:");
			Player player=game.getPlayerByID(this.ID);
			if (player.getBuildingPermits().isEmpty()){
				System.out.println("You don't have any permit. You couldn't select this action");
				break;
			}
			System.out.println("0- exit");
			for(i=0;i<player.getBuildingPermits().size();i++){
				System.out.println((i+1)+"- "+player.getBuildingPermits().get(i).toString());
			}
			permitIndex=this.selector(0, player.getBuildingPermits().size());
			if(permitIndex==0) break;
			chosenPermit=player.getBuildingPermits().get(permitIndex-1);
			System.out.println("Now type the initial of the city in which you would to build");
			Iterator<City> cityIt=chosenPermit.getBuildingAvaliableCities().iterator();
			while(cityIt.hasNext()){
				City city=cityIt.next();
				System.out.println(city.getName()+" "+city.getFirstChar());
			}
			String initialChosen=scanner1.next();
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
			Player owner=game.getPlayerByID(this.ID);
			ownedCards=owner.getCardsOwned();
			selectedCards=new ArrayList<>();
			for(i=0;i<ownedCards.size();i++){
				System.out.println((i+1)+"- "+ownedCards.get(i).toString());
			}
			System.out.println("Select the cards that you would to spend for corrupting the king's council - MAX 4 cards");
			System.out.println("Select 0 if you want to change the main action");
			System.out.println("Select -1 if you want to terminate the drawing step and to confirm your selected cards");
			i=1;
			cardsIndex=this.selector(-1, ownedCards.size());
			while(cardsIndex!=0&&cardsIndex!=-1&&i<4){
				PoliticsCard card=ownedCards.get(cardsIndex-1);
				selectedCards.add(card);
				i++;
				cardsIndex=this.selector(-1, ownedCards.size());
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
			initialChosen=scanner1.next();
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
	 */
	public Request quickAction() {
		System.out.println("Select now the quick action to perform");
		System.out.println("1. To Engage a new Assistant");
		System.out.println("2. To Change Face Up permits using an assistant");
		System.out.println("3. To Elect a councillor using an assistant");
		System.out.println("4. To obtain an extra main action");
		int select = this.selector(1, 4);
		
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
			int deckIndex=this.selector(0, regionalCouncils.size());
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
			int councillorIndex=this.selector(0,councillors.size());
			if(councillorIndex==0) break;
			Councillor councillorSelected=councillors.get(councillorIndex-1);
			List<Council> councils= game.getAllCouncils();
			System.out.println("These are the avaliable councils");
			for(int i=0;i<councils.size();i++){
				System.out.println((i+1)+"- "+councils.get(i).getCouncillors().toString());
			}
			System.out.println("Select the council in which you want to elect the councillor");
			System.out.println("Select 0 if you want to change the action");
			int councilIndex=this.selector(0, councils.size());
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
	 */
	public Request buyProducts() {
		Scanner scanner3 = new Scanner(System.in);
		System.out.println("These are the object for sale now!");
		int i=0;
		Player buyer=game.getPlayerByID(this.ID);
		for(MarketObject<?> obj:game.getMarket().getAvailableProducts(buyer)){
			i++;
			System.out.println(i+"- "+obj.toString());
		}
		System.out.println("Are you interested from something? Y/N");
		String answer=scanner3.nextLine();
		if(answer.equalsIgnoreCase("N")){
			return null;
		}else if(answer.equalsIgnoreCase("Y")){
			System.out.println("Select the product you want to buy");
			int selection = this.selector(1, 
					game.getMarket().getLengthAvailableProducts(buyer));
			return new MarketRequest<>(
					game.getMarket().getAvailableProducts(buyer).get(selection-1),ID);
		}else{
			System.out.println("You are insert a not valid value");
		}
		return null;
	}

	/**
	 * this method will generate a request to sell an object
	 * and put it available in the market for other players
	 */
	public Request addProduct() {
		int i;
		System.out.println("ADD A PRODUCT");
		System.out.println("These are the product you could sell");
		System.out.println("POLITIC CARDS");
		Player seller=game.getPlayerByID(this.ID);
		for(i=0;i<seller.getCardsOwned().size();i++){
			System.out.println("pc"+(i+1)+"- "+seller.getCardsOwned().get(i).toString());
		}
		System.out.println("BUILDING PERMITS NOT USED");
		for(int j=0;j<seller.getBuildingPermits().size();j++){
			System.out.println("bp"+(j+1)+"- "+seller.getBuildingPermits().get(j).toString());
		}
		System.out.println("ASSISTANTS avaliable "+seller.getAssistants());
		System.out.println("Now select the category of object that you are interested");
		System.out.println("1. Politic Cards");
		System.out.println("2. Building Permits");
		System.out.println("3. Assistants");
		int catIndex=this.selector(1, 3);
		switch(catIndex){
		case 1: 
			System.out.println("Insert the number of politic card that interested you");
			int cardIndex=this.selector(1, seller.getCardsOwned().size());
			PoliticsCard card=seller.getCardsOwned().get(cardIndex-1);
			int price=this.priceInsertion();
			return new MarketRequest<PoliticsCard>(
					new MarketObject<PoliticsCard>(card,seller, price),ID);
		case 2:
			System.out.println("Insert the number of building permit that interested you");
			int permitIndex=this.selector(1, seller.getBuildingPermits().size());
			BuildingPermit permit=seller.getBuildingPermits().get(permitIndex-1);
			price=this.priceInsertion();
			return new MarketRequest<BuildingPermit>(
					new MarketObject<BuildingPermit>(permit, seller, price),ID);
		case 3:
			System.out.println("Insert the amount of assistant you would sell");
			int assistantNumber=this.selector(1, seller.getAssistants());
			System.out.println("Insert the price");
			price=this.priceInsertion();
			Assistant assistant= new Assistant(assistantNumber);
			return new MarketRequest<Assistant>(
					new MarketObject<Assistant>(assistant, seller, price),ID);
		}
		return null;
	}

	/**
	 * method that allows to enter a price for a specific
	 * object in the market
	 * @return the price of the object
	 */
	private int priceInsertion() {
		Scanner scanner4 = new Scanner(System.in);
		System.out.println("Insert the price");
		int price=scanner4.nextInt();
		while(price<0){
			System.out.println("You cannot insert a negative value of price, try again");
		}
		return price;
	}

	/**
	 * display the permits and then guides the player through the 
	 * selection of the permit to send to the controller
	 */
	public Request permit() {
		System.out.println("Select the permit you want to acquire");
		memoryContainer.getPermits().forEach(System.out::println);
		int selection=this.selector(1, memoryContainer.getPermitsLenght());
		PermitsDeck selected=memoryContainer.retrievePermit(selection-1);
		for(int i=1;i<=selected.getFaceUpPermits().size();i++){
			BuildingPermit permit=selected.giveAFaceUpPermit(i);
			//the index starts with 1 because giveAFaceUpPermit works with 1 and 2 value.
			System.out.println((i)+"- "+permit);
		}
		selection=this.selector(1, selected.getFaceUpPermits().size());
		BuildingPermit selectedPermit=selected.giveAFaceUpPermit(selection);
		PermitsRequest request = new PermitsRequest(this.ID,selected,selectedPermit);
		memoryContainer.setPermits(new ArrayList<PermitsDeck>());
		return request;
	}

	/**
	 * display the bonuses and then guides the player through the 
	 * selection of the bonus to send to the controller
	 */
	public Request bonus() {
		System.out.println("Select the bonus you want to acquire");
		List<Object> bonusContainer=memoryContainer.getBonus();
		List<List<Bonus>> bonusList=new ArrayList<>();
		for(Object obj:bonusContainer){
			if(obj.getClass().equals(City.class)){
				bonusList.add(((City)obj).getBonus());
			}else if(obj.getClass().equals(BuildingPermit.class)){
				bonusList.add(((BuildingPermit)obj).getBonusList());
			}
		}
		bonusList.forEach(System.out::println);
		int selection=this.selector(1, bonusList.size());
		BonusRequest request = new BonusRequest(this.ID);
		for(Bonus b:bonusList.get(selection-1)){
			request.addBonus(b);
		}
		
		memoryContainer.setBonusRepeat(memoryContainer.getBonusRepeat()-1);
		if(memoryContainer.getBonusRepeat()==0){
			memoryContainer.setBonus(new ArrayList<Object>());
		}else{
			memoryContainer.getBonus().remove(bonusList.get(selection-1));
		}
		return request;
	}

	/**
	 * method to perform the selection & control between a 
	 * min value and a max value via the standard input
	 * @param min the minimum value to input
	 * @param max the maximum value to input
	 * @return the integer selected to use in methods
	 */
	public int selector(int min, int max){
		
		Scanner scanner = new Scanner(System.in);
		int selection = scanner.nextInt();
		while(selection<min||selection>max){
			System.out.println("The input cointains a not valid value. Please, try Again");
			selection=scanner.nextInt();
		}
		return selection;	
	}
	
	public Request start(){
		int actionType;
		Request request=null;
		if(game.isLastTurn())
			System.err.println("THIS IS YOUR LAST TURN");
		if(this.game.getGameState().getClass().equals(StartState.class)){
			if(!memoryContainer.getBonus().isEmpty())
				request = this.bonus();
			else if(!memoryContainer.getPermits().isEmpty())
				request = this.permit();
			else{	
				game.getGameState().display();
				actionType= this.selector(1, 4);
				switch (actionType) {
				case 1:
					request = this.mainAction();
					break;
				case 2:
					request = this.quickAction();
					break;
				case 3:
					request = new ActionRequest(new SkipAction(), this.ID);
					break;
				case 4:
					return this.quitter();
				}
			}
		}
		else if(game.getGameState().getClass().equals(MarketSellingState.class)){
			game.getGameState().display();
			actionType = this.selector(1, 3);
			switch(actionType){
			case 1:
				request = this.addProduct();
				break;
			case 2:
				request = new ActionRequest(new SkipAction(), ID);
				break;
			case 3:
				return this.quitter();
			}
			
		}
		else if(game.getGameState().getClass().equals(MarketBuyingState.class)){
			game.getGameState().display();
			actionType = this.selector(1, 3);
			switch(actionType){
			case 1:
				request = this.buyProducts();
				break;
			case 2: 
				request = new ActionRequest(new SkipAction(), ID);
				break;
			case 3:
				return this.quitter();
			}
		}else if(game.getGameState().getClass().equals(EndState.class)){
			game.getGameState().display();
			return null;
		}
		return request;
	}
	
	private Request quitter(){
		System.out.println("Are you sure? Type 'YES' is you agree, otherwise type anything else");
		Scanner scanner6 = new Scanner(System.in);
		String yesorno = scanner6.nextLine();
		if(yesorno.equalsIgnoreCase("yes")){
			System.err.println("You have been disconnected");
			scanner6.close();
			return new QuitRequest(this.ID);
		}	
		return null;
	}


	@Override
	public void updateModel(Game game) {
		System.out.println(game);
		this.game=game;
	}

	@Override
	public void stampMessage(String message) {
		System.out.println(message);
	}

	@Override
	public void updateChat(String message, String owner, int id) {
		//Our choise is to not implemented a chat in the CLI Version.
		//But we leave the possibility to do that with this method.
	}
}
