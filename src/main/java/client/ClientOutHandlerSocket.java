package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;
import model.actions.*;
import model.game.Game;
import model.game.Player;
import view.ActionRequest;
import view.ClientView;
import view.LocalStorage;
import view.Request;

public class ClientOutHandlerSocket implements Runnable 
{
	private int ID;
	private Request request; 
	private ObjectOutputStream socketOut;
	private LocalStorage memoryContainer;
	private Game game;

	public ClientOutHandlerSocket(ObjectOutputStream socketOut, Game game, LocalStorage container, int ID) {
		this.game = game;
		this.socketOut = socketOut;
		this.memoryContainer=container;
		this.ID = ID;
	}

	/**
	 * method in charge of starting the selection process
	 * to get to a specific request to send to the server
	 */
	@Override
	public void run() 
	{
		Scanner stdin = new Scanner(System.in);
		while (true) 
		{
			String inputLine = this.start(stdin);
			if(!inputLine.equalsIgnoreCase("quit")){
				stdin.close();
				this.notify();
			}
			try {
				socketOut.writeObject(request);
				socketOut.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * first level of input where the player can choose what to do
	 * exceptional cases when the input required is a bonus or a permit
	 * that are sent by the server as a change due to a certain score on
	 * on the nobility lane
	 * @param stdin the standard input to input the selection
	 * @return 
	 */
	private String start(Scanner stdin) {
		int actionType;
		ClientView view = new ClientView(game, memoryContainer, ID);
		if(!memoryContainer.getBonus().isEmpty())
			request = view.bonus(stdin);
		else if(!memoryContainer.getPermits().isEmpty())
			request = view.permit(stdin);
		else{	
			System.out.println("Select the action type to perform");
			System.out.println("1. main action");
			System.out.println("2. quick action");
			System.out.println("3. skip the quick action");
			System.out.println("4. perform market");
			System.out.println("5. quit");
			actionType= view.selector(1, 5, stdin);
			switch (actionType) {
			case 1:
				request = view.mainAction(stdin);
				break;
			case 2:
				request = view.quickAction(stdin);
				break;
			case 3:
				request = new ActionRequest(new SkipAction(), game.getPlayers().stream().filter((e)-> e.getPlayerID()==this.ID).findFirst().get());
				break;
			case 4:
				request = view.market(stdin);
				break;
			case 5:
				return "quit";
			default:
				break;
			}
		}
		return "";
	}

	/**
	 * get the request to the server
	 * @return a request
	 */
	public Request getRequest() {
		return request;
	}

	/**
	 * set the request to send to the server
	 * @param request the request to send
	 */
	public void setRequest(Request request) {
		this.request = request;
	}


}
