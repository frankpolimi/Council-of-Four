package view;

import org.jdom2.JDOMException;

import controller.ActionChange;
import controller.BonusChange;
import controller.Change;
import controller.PermitsChange;
import controller.StateChange;
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
import model.bonus.CoinBonus;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.PermitsDeck;
import model.game.Player;
import model.game.council.*;
import model.game.politics.PoliticsCard;
import model.game.topology.City;
import model.market.Assistant;
import model.market.MarketObject;
import model.observers.Observable;
import model.observers.Observer;

import java.io.IOException;
import java.util.*;

public class ClientView{
	
	private Game game;
	private Scanner scanner;
	private State state;
	
	
	public ClientView(Game game) {
		this.scanner=new Scanner(System.in);
		this.game=game;
		this.state=new StartState();
	}
	
	public int selectMarket(){
		System.out.println("Select the Market step action to perform");
		System.out.println("1. Add a product");
		System.out.println("2. pass to the next player");
		int selection=this.selector(1, 2);
		return selection;
	}
	
	public void buyProducts(){
		System.out.println("These are the object for sale now!");
		game.getMarket().displayProducts(game.getCurrentPlayer());
		System.out.println("Are you interested from something? Y/N");
		String answer=scanner.nextLine();
		if(answer.equalsIgnoreCase("N")){
			System.out.println("Ok, the turn passes to the next one player");
		}else if(answer.equalsIgnoreCase("Y")){
			System.out.println("Select the product you want to buy");
			
		}else{
			System.out.println("You are insert a not valid value");
		}
	}
	
	public MarketObject<?> performMarketAction(int marketIndex){
		Player current=game.getCurrentPlayer();
		switch(marketIndex){
		case 1: 
			int i;
			System.out.println("ADD A PRODUCT");
			System.out.println("These are the product you could sell");
			System.out.println("POLITIC CARDS");
			for(i=0;i<current.getCardsOwned().size();i++){
				System.out.println("pc"+(i+1)+"- "+current.getCardsOwned().get(i).toString());
			}
			System.out.println("BUILDING PERMITS NOT USED");
			for(int j=0;j<current.getBuildingPermits().size();j++){
				System.out.println("bp"+(j+1)+"- "+current.getBuildingPermits().get(j).toString());
			}
			System.out.println("ASSISTANTS avaliable "+current.getAssistants());
			System.out.println("Now select the category of object that you are interested");
			System.out.println("1. Politic Cards");
			System.out.println("2. Building Permits");
			System.out.println("3. Assistants");
			int catIndex=this.selector(1, 3);
			switch(catIndex){
			case 1: 
				System.out.println("Insert the number of politic card that interested you");
				int cardIndex=this.selector(1, current.getCardsOwned().size());
				PoliticsCard card=current.getCardsOwned().get(cardIndex-1);
				int price=this.priceInsertion();
				return new MarketObject<PoliticsCard>(card, current, price);
			case 2:
				System.out.println("Insert the number of building permit that interested you");
				int permitIndex=this.selector(1, current.getBuildingPermits().size());
				BuildingPermit permit=current.getBuildingPermits().get(permitIndex-1);
				price=this.priceInsertion();
				return new MarketObject<BuildingPermit>(permit, current, price);
			case 3:
				System.out.println("Insert the amount of assistant you would sell");
				int assistantNumber=this.selector(1, current.getAssistants());
				System.out.println("Insert the price");
				price=this.priceInsertion();
				Assistant assistant= new Assistant(assistantNumber);
				return new MarketObject<Assistant>(assistant, current, price);
			}
		}
		return null;
	}
	
	private int priceInsertion(){
		System.out.println("Insert the price");
		int price=scanner.nextInt();
		while(price<0){
			System.out.println("You cannot insert a negative value of price, try again");
		}
		return price;
	}
	
	public int selectAction(){
		System.out.println("Select the action type to perform");
		System.out.println("1. main action");
		System.out.println("2. quick action");
		System.out.println("3. pass to the next player");
		int selection=this.selector(1, 3);
		return selection;
	}
	
	public int showAndSelectActions(int actionType){
		switch(actionType){
		case 1:
			System.out.println("Select now the main action to perform");
			System.out.println("1. To Elect a councillor");
			System.out.println("2. To Acquire a Building Permit");
			System.out.println("3. To Build an Emporium by permit");
			System.out.println("4. To Build an Emporium under the consense of the king");
			break;
		case 2: 
			System.out.println("Select now the quick action to perform");
			System.out.println("1. To Engage a new Assistant");
			System.out.println("2. To Change Face Up permits using an assistant");
			System.out.println("3. To Elect a councillor using an assistant");
			System.out.println("4. To obtain an extra main action");
			break;			
		}
		
		int selection=this.selector(1, 4);
		return selection;
	}
	
	public void buildTheAction(int type, int select){
		//devo inizializzare action
		Action action = null;
		Player current=game.getCurrentPlayer();
		if(type==1){
			switch(select){
			case 1://ricordare che sta mossa � praticamente uguale alla 3� quick action.. ricordarsene e modificare in seguito
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
			case 2:
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
	
	public void displayAvaliableActions(){
		if(this.state.getClass().equals(StartState.class)){
			int type=this.selectAction();
			int sel=this.showAndSelectActions(type);
			this.buildTheAction(type, sel);
		}else if(this.state.getClass().equals(MarketState.class)){
			int type=this.selectMarket();
			this.performMarketAction(type);
		}
		else if(this.state.getClass().equals(BonusState.class))
			this.selectBonus();
			
	}
	
	public void selectBonus() {
		System.out.println("Select the bonus you want to acquire");
		List<Bonus> list = ((BonusState)state).getBonus();
		for(Bonus b : list)
			System.out.println((list.indexOf(b)+1)+" - " +b.toString());
		int selection=this.selector(1, list.size());
		List<Bonus> aux = new ArrayList<>();
		aux.add(list.get(selection));
		//TODO send to view server
		//this.notifyObservers(new BonusChange(aux));
	}

	private void stampModel(){
		System.out.println("GAME");
		System.out.println(game.toString());
	}
	
	private int selector(int min, int max){
		int selection=scanner.nextInt();
		while(selection<min||selection>max){
			System.out.println("The input cointains a not valid value. Please, try Again");
			selection=scanner.nextInt();
		}
		return selection;
		
	}
	
	public State getState(){
		return this.state;
	}
	
	public void setState(State state){
		this.state=state;
	}
	
	/*public static void main(String[]args) throws JDOMException, IOException{
		Player player=new Player("ema", 1, 10, 200);
		ArrayList<Player> players=new ArrayList<>();
		players.add(player);
		Game game=new Game(players);
		ClientView view=new ClientView(game);
		List<Bonus> l = new ArrayList<>(); 
		l.add(new CoinBonus(10));
		while(true){
			view.setState(new StartState());
			view.displayAvaliableActions();
			view.setState(new BonusState(l));
			view.selectBonus();
			view.setState(new MarketState());
			view.displayAvaliableActions();
		}
	}*/

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