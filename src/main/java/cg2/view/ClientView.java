package cg2.view;

import java.util.Scanner;

import cg2.game.Game;
import council.Council;
import council.Councillor;
import council.RegionalCouncil;
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
				List<Council> councils=game.getAllCouncils();
				
				
					
			}
			
		}else{
			
		}
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
