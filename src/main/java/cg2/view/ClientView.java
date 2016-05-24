package cg2.view;

import java.util.Scanner;

import actions.Action;
import actions.ElectCouncillor;
import cg2.game.Game;
import cg2.player.Player;

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
				//manca metodo prelievo permesso
				
				
				
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
