package client;

import model.game.Game;
import model.game.Player;
import view.LocalStorage;
import view.Request;

public interface ClientViewInterface {
	public Request start();
	public void updateModel(Game game);
	public int getId();
	public void setId(int id);
	public void stampMessage(String message);
	public void setGame(Game game);
	public void setMemoryContainer(LocalStorage memoryLocator);
	public void updateChat(String message, String string, int i);
}
