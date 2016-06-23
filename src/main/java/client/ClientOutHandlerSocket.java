package client;

import java.io.IOException;
import java.util.logging.Level;

import model.game.Game;
import view.EndState;
import view.LocalStorage;
import view.QuitRequest;
import view.Request;

public class ClientOutHandlerSocket implements Runnable 
{
	private ClientViewInterface clientView;
	private Request request; 
	//private ObjectOutputStream socketOut;
	private ConnectionHandler handler;
	private LocalStorage memoryContainer;
	private Game game;

	public ClientOutHandlerSocket(ConnectionHandler handler, LocalStorage container, ClientViewInterface view) {
		this.handler=handler;
		this.memoryContainer=container;
		this.game = memoryContainer.getGameRef();
		this.clientView=view;
	}

	/**
	 * method in charge of starting the selection process
	 * to get to a specific request to send to the server
	 */
	@Override
	public void run() 
	{
		boolean isUpdated;
		while(game==null){
			game=this.memoryContainer.getGameRef();
		}
		System.err.println("game ricevuto");
		while (true) 
		{

			synchronized (memoryContainer) {
				game=memoryContainer.getGameRef();
				isUpdated=memoryContainer.isUpdated();
			}
			
			if(game.getGameState()!=null&&isUpdated&&game.getCurrentPlayer().getPlayerID()==this.clientView.getId()){
				request = clientView.start(); 
				if(request!=null){
					try {
						if(!memoryContainer.getGameRef().getGameState().getClass().equals(EndState.class)){
							synchronized (this.memoryContainer) {
								this.memoryContainer.setUpdated(false);
							}
							handler.sendToServer(request);
						}
					} catch (IOException e) {
						if(e.getMessage().equals("Socket closed"))
						clientView.stampMessage("THE GAME IS FINISHED, BYE BYE");
						break;
					} catch (IllegalArgumentException | IllegalStateException c){
						System.out.println("Error in performing action: "+c.getMessage());
					}
					
					if(request.getClass().equals(QuitRequest.class)){
						try {
							handler.closeConnection();
							clientView.stampMessage("THE GAME IS FINISHED, BYE BYE");
							break;
						} catch (IOException e) {
							Client.logger.log(Level.ALL, e.getMessage());
							//e.printStackTrace();
						}
					}
				}
			}
		}
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
