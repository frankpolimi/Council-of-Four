package client;

import java.rmi.Remote;
import java.rmi.RemoteException;

import model.actions.Action;
import model.game.Game;
import view.Request;

public interface RMIClientRemote extends Remote{
	
	public void performAction(Request request, Game game) throws RemoteException;

}
