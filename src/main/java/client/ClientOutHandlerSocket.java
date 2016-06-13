package client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Scanner;


import model.actions.*;
import model.game.Game;
import view.ActionRequest;
import view.ClientView;
import view.EndState;
import view.LocalStorage;
import view.MarketBuyingState;
import view.MarketSellingState;
import view.QuitRequest;
import view.Request;
import view.StartState;

public class ClientOutHandlerSocket implements Runnable 
{
	private int ID;
	private Request request; 
	//private ObjectOutputStream socketOut;
	private ConnectionHandler handler;
	private LocalStorage memoryContainer;
	private Game game;

	public ClientOutHandlerSocket(ConnectionHandler handler, LocalStorage container, int ID) {
	
		this.handler=handler;
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
		boolean isUpdated;
		Scanner stdin=new Scanner(System.in);
		while(game==null);
		System.err.println("game ricevuto");
		while (true) 
		{

			synchronized (memoryContainer) {
				game=memoryContainer.getGameRef();
				isUpdated=memoryContainer.isUpdated();
			}
			
			if(game.getGameState()!=null&&isUpdated&&game.getCurrentPlayer().getPlayerID()==ID){
								
				ClientView view = new ClientView(game, memoryContainer, ID);
				request = view.start(); 
				if(request!=null){
					try {
						if(!memoryContainer.getGameRef().getGameState().getClass().equals(EndState.class)){
							handler.sendToServer(request);
							synchronized (memoryContainer) {
								memoryContainer.setUpdated(false);
							}
						}
					} catch (IOException e) {
						if(e.getMessage().equals("Socket closed"))
						System.err.println("THE GAME IS FINISHED, BYE BYE");
						break;
					} catch (IllegalArgumentException | IllegalStateException c){
						System.out.println("Error in performing action: "+c.getMessage());
					}
				}
				
				if(request.getClass().equals(QuitRequest.class)){
					try {
						handler.closeConnection();
						System.err.println("THE GAME IS FINISHED, BYE BYE");
						break;
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				/*
				try {
					Thread.currentThread().wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}*/
				

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
