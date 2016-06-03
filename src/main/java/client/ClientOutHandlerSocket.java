package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;

import javax.sound.midi.Synthesizer;

import model.actions.*;
import model.game.Game;
import view.ActionRequest;
import view.ClientView;
import view.LocalStorage;
import view.MarketBuyingState;
import view.MarketSellingState;
import view.Request;
import view.StartState;
import view.State;

public class ClientOutHandlerSocket implements Runnable 
{
	private int ID;
	private Request request; 
	private ObjectOutputStream socketOut;
	private LocalStorage memoryContainer;
	private Game game;

	public ClientOutHandlerSocket(ObjectOutputStream socketOut, LocalStorage container, int ID) {
	
		this.socketOut = socketOut;
		this.memoryContainer=container;
		this.game = memoryContainer.getGameRef();
		this.ID = ID;
	}

	/**
	 * method in charge of starting the selection process
	 * to get to a specific request to send to the server
	 */
	@Override
	public void run() 
	{
		Scanner stdin=new Scanner(System.in);
		while(game==null);
		System.err.println("game ricevuto");
		while (true) 
			//!game.getGameState().equals(EndState.class)
		{
			synchronized (memoryContainer) {
				game=memoryContainer.getGameRef();
			}
			if(game.getGameState()!=null){
				try {
					Thread.sleep(2000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				String inputLine = this.start(stdin);
				if(inputLine.equals(""))
					try {
						socketOut.writeUnshared(request);
						socketOut.flush();
					} catch (IOException e) {
						e.printStackTrace();
					}
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
	public String start(Scanner stdin) {
		int actionType;
		ClientView view = new ClientView(game, memoryContainer, ID);
		if(game.isLastTurn())
			System.out.println("THIS IS YOUR LAST TURN");
		if(this.game.getGameState().getClass().equals(StartState.class)){
			if(!memoryContainer.getBonus().isEmpty())
				request = view.bonus(stdin);
			else if(!memoryContainer.getPermits().isEmpty())
				request = view.permit(stdin);
			else{	
				game.getGameState().display();
				actionType= view.selector(1, 4, stdin);
				switch (actionType) {
				case 1:
					request = view.mainAction(stdin);
					break;
				case 2:
					request = view.quickAction(stdin);
					break;
				case 3:
					request = new ActionRequest(new SkipAction(), this.ID);
					break;
				case 4:
					return "quit";
				}
				return "";
			}
		}
		else if(game.getGameState().getClass().equals(MarketSellingState.class)){
			game.getGameState().display();
			actionType = view.selector(1, 3, stdin);
			switch(actionType){
			case 1:
				request = view.addProduct(stdin);
				break;
			case 2:
				request = new ActionRequest(new SkipAction(), ID);
				break;
			case 3:
				return "quit";
			}
			return "";
		}
		else if(game.getGameState().getClass().equals(MarketBuyingState.class)){
			game.getGameState().display();
			actionType = view.selector(1, 3, stdin);
			switch(actionType){
			case 1:
				request = view.buyProducts(stdin);
				break;
			case 2: 
				request = new ActionRequest(new SkipAction(), ID);
				break;
			case 3:
				return "quit";
			}
			return "";
		}
		return "not ready";
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
