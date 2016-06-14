package view;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import org.jdom2.JDOMException;

import client.ClientView;
import controller.BonusChange;
import controller.Change;
import controller.Controller;
import controller.ModelChange;
import controller.PermitsChange;
import model.actions.AcquirePermit;
import model.actions.*;
import model.actions.BuildEmporiumByPermit;
import model.bonus.*;
import model.bonus.GetPoliticBonus;
import model.bonus.NobilityBonus;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.PermitsDeck;
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
		Scanner scanner=new Scanner(System.in);
		if(change.getClass().equals(PermitsChange.class)){
			System.out.println("Permessi FACEUPS");
			for(Region r:game.getRegions()){
				System.out.println("REGIONE "+r.getName());
				System.out.println(r.getPermitsDeck().getFaceUpPermits().toString());
			}
			System.out.println();
			PermitsChange permit=(PermitsChange)change;
			for(int i=0;i<permit.getPermits().size();i++)
				System.out.println((i+1)+"- "+permit.getPermits().get(i));
			scanner=new Scanner(System.in);
			int scelta=scanner.nextInt();
			PermitsRequest permitReq=new PermitsRequest(game.getCurrentPlayer().getPlayerID());
			permitReq.addPermit(permit.getPermits().get(scelta-1));
			this.notifica(permitReq);
		}else if(change.getClass().equals(BonusChange.class)){
			BonusChange bonus=(BonusChange)change;
			for(int i=0;i<bonus.getBonusList().size();i++)
				System.out.println((i+1)+"- "+bonus.getBonusList().get(i));
			scanner=new Scanner(System.in);
			int scelta=scanner.nextInt();
			BonusRequest request=new BonusRequest(game.getCurrentPlayer().getPlayerID());
			request.addBonus(bonus.getBonusList().get(scelta-1));
			this.notifica(request);
		}else if(change.getClass().equals(ModelChange.class)){
			System.out.println(((ModelChange)change).getGame().getCurrentPlayer());
			
			ClientView view=new ClientView(game, new LocalStorage(), game.getCurrentPlayer().getPlayerID());
			if(game.getGameState().getClass().equals(MarketSellingState.class)){
				game.getGameState().display();
				int scelta=scanner.nextInt();
				while(scelta!=2){
					scanner.reset();
					this.notifica(view.addProduct(scanner));
					game.getGameState().display();
					scelta=scanner.nextInt();
				}
			}else if(game.getGameState().getClass().equals(MarketBuyingState.class)){
				game.getGameState().display();
				int scelta=scanner.nextInt();
				while(scelta!=2){
					scanner.reset();
					this.notifica(view.buyProducts(scanner));
					game.getGameState().display();
					scelta=scanner.nextInt();
				}
			}
		}
		
			
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
		//view.provaFinePartita(p1,p2,p3, view);
		//view.avanzamentoNobiltà(p1, view);
		view.provaMarket(p1, p2, p3, view);
	}
	
	public void avanzamentoNobiltà(Player p1, ViewProva view) throws InterruptedException{
		game.getMap().getVertexByKey("K").addEmporium(p1);
		game.getMap().getVertexByKey("j").addEmporium(p1);
		game.getMap().getVertexByKey("A").addEmporium(p1);
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
		
		//game.setCurrentPlayer(p1);
		System.out.println("Carte usate "+carte3+"\n");
		System.out.println("Carta prossima "+game.getPoliticsDeck().getCardAtIndex(0));
		view.notifica(new ActionRequest((Action)new AcquirePermit(mountain.getCouncil(), carte3, permessoScelto),p1.getPlayerID()));


	}
	
	public void provaFinePartita(Player p1, Player p2, Player p3, ViewProva view) throws InterruptedException{
		p1.setRemainingEmporiums(1);
		Set<City> cities=new HashSet<>();
		cities.add(game.getMap().getVertexByKey("a"));
		cities.add(game.getMap().getVertexByKey("c"));
		Set<Bonus> bonuses=new HashSet<>();
		bonuses.add(new	GetPoliticBonus(2));
		
		BuildingPermit permit=new BuildingPermit(cities, bonuses);
		p1.getBuildingPermits().add(permit);
		Request request=new ActionRequest(new BuildEmporiumByPermit(permit, game.getMap().getVertexByKey("C")),1);
		view.notifica(request);
		request=new ActionRequest(new SkipAction(),1);
		view.notifica(request);
		view.avanzamentoNobiltà(p2, view);
		request=new ActionRequest(new SkipAction(),2);
		view.notifica(request);
		view.avanzamentoNobiltà(p3, view);
		request=new ActionRequest(new SkipAction(),3);
		view.notifica(request);
		
	}

	public void provaMarket(Player p1, Player p2, Player p3, ViewProva view){
		game.decrementMainActionCounter();
		game.setCurrentPlayer(p3);
		p1.setAssistants(100);
		p1.setCoins(100);
		p2.setAssistants(30);
		p2.setCoins(100);
		p3.setAssistants(15);
		p3.setCoins(200);
		PermitsDeck mountainDeck=game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("mountain")).findFirst().get().getPermitsDeck();
		for(int i=0;i<4;i++){
			Random random=new Random();
			int pos=random.nextInt(mountainDeck.getBuildingPermitsDeck().size());
			BuildingPermit permit=mountainDeck.getBuildingPermitsDeck().remove(pos);
			p1.addBuildingPermit(permit);
		}
		mountainDeck=game.getRegions().stream().filter(e->e.getName().equalsIgnoreCase("hill")).findFirst().get().getPermitsDeck();
		for(int i=0;i<4;i++){
			Random random=new Random();
			int pos=random.nextInt(mountainDeck.getBuildingPermitsDeck().size());
			BuildingPermit permit=mountainDeck.getBuildingPermitsDeck().remove(pos);
			p2.addBuildingPermit(permit);
		}
		for(int i=0;i<4;i++){
			Random random=new Random();
			int pos=random.nextInt(mountainDeck.getBuildingPermitsDeck().size());
			BuildingPermit permit=mountainDeck.getBuildingPermitsDeck().remove(pos);
			p3.addBuildingPermit(permit);
		}
		view.notifica(new ActionRequest(new SkipAction(),3));
		view.notifica(new ActionRequest(new SkipAction(),1));
		view.notifica(new ActionRequest(new SkipAction(),2));
		System.out.println("Oggetti risultati ");
		System.out.println(game.getMarket().toString());
		view.notifica(new ActionRequest(new SkipAction(),3));
		view.notifica(new ActionRequest(new SkipAction(),game.getCurrentPlayer().getPlayerID()));
		view.notifica(new ActionRequest(new SkipAction(),game.getCurrentPlayer().getPlayerID()));
		view.notifica(new ActionRequest(new SkipAction(),game.getCurrentPlayer().getPlayerID()));
		System.out.println("Giocatore 1");
		System.out.println(p1);
		System.out.println("Giocatore 2");
		System.out.println(p2);
		System.out.println("Giocatore 3");
		System.out.println(p3);
		
	}



	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}
}
