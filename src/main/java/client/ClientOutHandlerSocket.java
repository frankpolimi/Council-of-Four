package client;

import java.io.PrintWriter;
import java.util.Scanner;

public class ClientOutHandlerSocket implements Runnable 
{
	private PrintWriter socketOut;

	public ClientOutHandlerSocket(PrintWriter socketOut) {
		super();
		this.socketOut = socketOut;
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
			if(inputLine.equals("Quit")) break;  
		}
		
		stdin.close();

	}

}
