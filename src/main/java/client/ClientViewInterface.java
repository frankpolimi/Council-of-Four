package client;

import model.game.Game;
import view.Request;

public interface ClientViewInterface {
	public Request start();
	public void updateModel(Game game);
	public int getId();
	public int setId(int id);
	public void stampMessage(String message);
}
