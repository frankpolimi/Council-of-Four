package client;

import java.io.PrintWriter;
import java.util.Scanner;
import model.actions.*;
import view.ActionRequest;
import view.LocalStorage;
import view.Request;

public class ClientOutHandlerSocket implements Runnable 
{
	private Request request; 
	//Ha senso tenerla qua perchè la riempiamo
	//piano piano ed è più facile accederci in caso di necessità
	private PrintWriter socketOut;
	private LocalStorage memoryContainer; //Ora è object ma poi decidi te cosa mettere

	public ClientOutHandlerSocket(PrintWriter socketOut, LocalStorage container) {
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
			String inputLine = this.start(stdin, request);
			if(inputLine.equalsIgnoreCase("quit")){
				socketOut.println(inputLine);
				socketOut.flush();
				break; 
			}
			  //Qua c'è da spiaccicare la view
			//Poi hai il container, estrai la roba, ci lavori e spedisci
			 
		}
		stdin.close();

	}
	
	private String start(Scanner stdin, Request request2) {
		int actionType;
		System.out.println("Select the action type to perform");
		System.out.println("1. main action");
		System.out.println("2. quick action");
		System.out.println("3. pass to the next player");
		System.out.println("4. perform market action");
		System.out.println("5. quit");
		actionType=this.selector(1, 5, stdin);		
		switch (actionType) {
		case 1:
			this.mainAction(stdin, request);
			break;
		case 2:
			this.quickAction(stdin, request);
			break;
		case 3:
			request = new ActionRequest(new SkipQuickAction());
			break;
		case 4:
			this.market(stdin, request);
			break;
		case 5:
			return "quit";
		default:
			break;
		}
		return "";
	}
	
	
	
	private void mainAction(Scanner stdin, Request request2) {
		// TODO Auto-generated method stub
		
	}

	private void quickAction(Scanner stdin, Request request2) {
		// TODO Auto-generated method stub
		
	}

	private void market(Scanner stdin, Request request2) {
		// TODO Auto-generated method stub
		
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
