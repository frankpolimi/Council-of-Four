package view;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import org.jdom2.JDOMException;

import controller.Change;
import controller.Controller;
import model.actions.AcquirePermit;
import model.actions.Action;
import model.bonus.Bonus;
import model.bonus.NobilityBonus;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.Player;
import model.game.council.Councillor;
import model.game.politics.ColoredPoliticsCard;
import model.game.politics.PoliticsCard;
import model.game.topology.City;
import model.game.topology.Region;

public class ViewProva extends View {

	private Game game;
	private Controller controller;
	
	/**
	 * constructor for the socket
	 * @param socket the socket that the server prepares
	 * @throws IOException if is an error in the input or output stream
	 * @throws ClassNotFoundException 
	 */
	public ViewProva(Game game)
	{
		
		this.game=game;
		this.registerObserver(new Controller(game));
		game.registerObserver(this);
	}


	
	public void notifica(Request request)
	{
		this.notifyObservers(request);
	}
	
	/**
	 * this method receives changes from the model 
	 * and will send to the client via socket
	 */
	@Override
	public void update(Change change){
		System.out.println("CAMBIAMENTO");
		System.out.println(change);
	}


	
	public static void main(String[] args) throws JDOMException, IOException, InterruptedException {
		Game game=new Game();
		ViewProva view=new ViewProva(game);
		Player p1=new Player("io",1);
		Player p2=new Player("me",2);
		Player p3=new Player("mestesso",3);
		List<Player> players=new ArrayList<>();
		players.add(p1);
		players.add(p2);
		players.add(p3);
		game.setPlayers(players);
		p1.setNobilityPoints(9);
		p1.setCoins(100000);
		Region mountain=game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("mountain")).findFirst().get();
		List<ColoredPoliticsCard> carte=new ArrayList<>();
		for(PoliticsCard pc:p1.getCardsOwned()){
			if(pc.getClass().equals(ColoredPoliticsCard.class)){
				carte.add((ColoredPoliticsCard)pc);
			}
		}
		List<ColoredPoliticsCard> carte2=new ArrayList<>();
		if(carte.size()>4){
			for(int i=0;i<4;i++){
				carte2.add(carte.get(i));
			}
		}else{
			carte2.addAll(carte);
		}
		
		for(int i=0;i<carte2.size();i++){
			mountain.getCouncil().electCouncillor(new Councillor(carte2.get(i).getColor()));
		}
		ArrayBlockingQueue<BuildingPermit> array=new ArrayBlockingQueue<>(2);
		Bonus bonus=new NobilityBonus(1);
		Set<City> cities1=new HashSet<>();
		cities1.add(game.getMap().getVertexByKey("K"));
		Set<Bonus> bonuses=new HashSet<>();
		bonuses.add(bonus);
		BuildingPermit permessoScelto= new BuildingPermit(cities1,bonuses);
		
		
		array.put(permessoScelto);
		array.put(new BuildingPermit(new HashSet<City>(game.getAllCities()),bonuses));
		mountain.getPermitsDeck().setFaceUpPermits(array);
		ArrayList<PoliticsCard> carte3=new ArrayList<>();
		carte3.addAll(carte2);
		
		game.setCurrentPlayer(p1);
		
		view.notifica(new ActionRequest((Action)new AcquirePermit(mountain.getCouncil(), carte3, permessoScelto),1));
		
	}

}
