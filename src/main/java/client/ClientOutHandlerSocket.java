package client;

import java.io.PrintWriter;
import java.util.Scanner;
import model.actions.*;

public class ClientOutHandlerSocket implements Runnable 
{
	private Action action; //Ha senso tenerla qua perch� la riempiamo
	//piano piano ed � pi� facile accederci in caso di necessit�
	private PrintWriter socketOut;
	private Object memoryContainer; //Ora � object ma poi decidi te cosa mettere

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
			socketOut.flush();  //Qua c'� da spiaccicare la view
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
