package cg2.view;

import java.util.Scanner;
import java.util.concurrent.ArrayBlockingQueue;

import actions.AcquirePermit;
import actions.Action;
import actions.BuildEmporiumByPermit;
import actions.ElectCouncillor;
import cg2.game.Game;
import cg2.model.BuildingPermit;
import cg2.model.PermitsDeck;
import cg2.player.Player;
import cg2.model.*;

import council.*;
import politics.PoliticsCard;
import topology.Region;

import java.util.*;

public class ClientView {
	
	Game game;
	Scanner scanner;
	
	public ClientView(Game game) {
		this.scanner=new Scanner(System.in);
		this.game=game;
	}
	
	public int selectAction(){
		
		System.out.println("Select the action type to perform");
		System.out.println("1. main action");
		System.out.println("2. quick action");
		int selection=scanner.nextInt();
		while(selection<1||selection>2){
			System.out.println("The input cointains a not valid value. Please, try Again");
			selection=scanner.nextInt();
		}
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
			System.out.println("4. To Use an extra main action");
			break;			
		}
		
		int selection=scanner.nextInt();
		while(selection<1||selection>2){
			System.out.println("The input cointains a not valid value. Please, try Again");
			selection=scanner.nextInt();
		}
		return selection;
	}
	
	public void buildTheAction(int type, int select){
		Action action;
		Player current=game.getCurrentPlayer();
		if(type==1){
			switch(select){
			case 1:
				System.out.println("Action Chosen: To Elect a councillor");
				System.out.println("These are the avaliable councillors");
				List<Councillor> councillors=game.getAvaliableCouncillor();
				for(int i=0;i<councillors.size();i++){
					System.out.println((i+1)+"- "+councillors.get(i).toString());
				}
				System.out.println("Select the councillor you would like");
				int councillorIndex=this.selector(1,councillors.size());
				Councillor councillorSelected=councillors.get(councillorIndex);
				List<Council> councils= game.getAllCouncils();
				for(int i=0;i<councils.size();i++){
					System.out.println((i+1)+"- "+councils.get(i).toString());
				}
				int councilIndex=this.selector(1, councils.size());
				Council council=councils.get(councilIndex);
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
					PoliticsCard card=ownedCards.get(cardsIndex);
					selectedCards.add(card);
				}
				//select council
				List<RegionalCouncil> regionalCouncils=game.getRegionalCouncils();
				for(int i=0;i<regionalCouncils.size();i++){
					System.out.println((i+1)+"- "+regionalCouncils.get(i).toString());
				}
				councilIndex=this.selector(1, regionalCouncils.size());
				RegionalCouncil councilCorrupted=regionalCouncils.get(councilIndex);
				System.out.println("Select the permit to acquire");
				PermitsDeck councilDeck=councilCorrupted.getPermitsDeck();
				Iterator<BuildingPermit> it=councilDeck.getFaceUpPermits().iterator();
				int i=1;
				while(it.hasNext()){
					System.out.println(i+"- "+it.next().toString());
				}
				int permitIndex=this.selector(1, 2);
				BuildingPermit chosenPermit=councilDeck.giveAFaceUpPermit(permitIndex);
				action=new AcquirePermit(councilCorrupted, selectedCards, chosenPermit);
				break;
			case 3:
				System.out.println("Action Chosen: To Acquire a building permit");
				System.out.println("Select the building permit you want to use for building an emporium");
				System.out.println("Your permits:");
				for(i=0;i<current.getBuildingPermits().size();i++){
					System.out.println((i+1)+"- "+current.getBuildingPermits().get(i).toString());
				}
				permitIndex=this.selector(1, current.getBuildingPermits().size());
				chosenPermit=current.getBuildingPermits().get(permitIndex);
				System.out.println("Now type the initial of the city in which you would to build");
				Iterator<City> cityIt=chosenPermit.getBuildingAvaliableCities().iterator();
				while(cityIt.hasNext()){
					City city=cityIt.next();
					System.out.println(city.getName()+" "+city.getFirstChar());
				}
				String initialChosen=scanner.next();
				City cityChosen=game.getMap().getVertexByKey(initialChosen);
				action=new BuildEmporiumByPermit(chosenPermit, cityChosen);
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
					PoliticsCard card=ownedCards.get(cardsIndex);
					selectedCards.add(card);
				}
				//council of the king
			}
			
		}else{
			
		}
	}
	
	
	
	private Council selectCouncil(){
		List<Council> councils=game.getAllCouncils();
		for(int i=0;i<councils.size();i++){
			System.out.println((i+1)+"- "+councils.get(i).toString());
		}
		int councilIndex=this.selector(1, councils.size());
		Council council=councils.get(councilIndex);
		return council;
	}
	
	
	private int selector(int min, int max){
		int selection=scanner.nextInt();
		while(selection<1||selection>2){
			System.out.println("The input cointains a not valid value. Please, try Again");
			selection=scanner.nextInt();
		}
		return selection;
		
	}
}
