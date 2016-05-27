package client;

import java.io.PrintWriter;
import java.util.Scanner;
import model.actions.*;

public class ClientOutHandlerSocket implements Runnable 
{
	private Action action; //Ha senso tenerla qua perchè la riempiamo
	//piano piano ed è più facile accederci in caso di necessità
	private PrintWriter socketOut;
	private Object memoryContainer; //Ora è object ma poi decidi te cosa mettere

	public ClientOutHandlerSocket(PrintWriter socketOut, Object container) {
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
			String inputLine = stdin.nextLine();
			socketOut.println(inputLine);
			socketOut.flush();  //Qua c'è da spiaccicare la view
			//Poi hai il container, estrai la roba, ci lavori e spedisci
			if(inputLine.equals("Quit")) break;  
		}
		
		stdin.close();

	}
	
	public Action getAction() {
		return action;
	}

	public void setAction(Action action) {
		this.action = action;
	}


}
