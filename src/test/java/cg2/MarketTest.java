package cg2;

import static org.junit.Assert.*;

import java.awt.Color;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.jdom2.JDOMException;
import org.junit.Test;

import model.game.BuildingPermit;
import model.game.Game;
import model.game.politics.ColoredPoliticsCard;
import model.game.politics.JollyPoliticsCard;
import model.game.politics.PoliticsCard;
import model.market.Assistant;
import model.market.Market;
import model.market.MarketObject;

public class MarketTest {
	Game game;
	Market market;
	
	public void MarketTestSetup(){
		try {
			game=SupportClass.gameWithPlayersCreator("a", "b", "c", "d");
		} catch (JDOMException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		BuildingPermit permit=game.getAllPermitsDecks().get(0).giveAFaceUpPermit(1);
		game.getPlayerByID(1).addBuildingPermit(permit);
		game.getPlayerByID(2).addBuildingPermit(permit);
		game.getPoliticsDeck().drawCard(game.getPlayerByID(1));
		game.getPoliticsDeck().drawCard(game.getPlayerByID(2));
		game.getPlayerByID(1).setCoins(100);
		game.getPlayerByID(2).setCoins(100);
		game.getPlayerByID(3).setCoins(100);
		game.getPlayerByID(4).setCoins(100);
		market=new Market(game);
	}
	
	@Test(expected=NullPointerException.class)
	public void gameIsNull(){
		Market market=new Market(null);
	}
	
	@Test
	public void testAddPoliticsCardOwned(){
		this.MarketTestSetup();
		game.setCurrentPlayer(game.getPlayerByID(1));
		PoliticsCard card=game.getPlayerByID(1).getCardsOwned().get(0);
		Color cardColor;
		if(card.getClass().equals(ColoredPoliticsCard.class)){
			cardColor=((ColoredPoliticsCard)card).getColor();
		}else{
			cardColor=new Color(55,156,136);
		}
		List<Color> distincts=new ArrayList<>();
		
		for(PoliticsCard c:game.getCurrentPlayer().getCardsOwned()){
			if(c.getClass().equals(ColoredPoliticsCard.class)){
				if(!distincts.contains(((ColoredPoliticsCard)c).getColor())){
					distincts.add(((ColoredPoliticsCard)c).getColor());
				}
			}
		}
		Map<Color, Integer> colorOccureBefore=SupportClass.giveColorMapInPoliticsDeck(game.getPlayerByID(1),distincts);
		int colorOcc=colorOccureBefore.get(cardColor);
		MarketObject<PoliticsCard> object=new MarketObject<>(card, game.getPlayerByID(1), 
				10);
		market.addProduct(object);
		assertTrue(market.getAvailableProducts(game.getPlayerByID(2)).contains(object));
		assertTrue(SupportClass.giveColorMapInPoliticsDeck(game.getPlayerByID(1),distincts)
				.get(cardColor)==colorOcc-1);
		Map<Color, Integer> colorOccureAfter=SupportClass.giveColorMapInPoliticsDeck(game.getPlayerByID(1),distincts);
		colorOccureBefore.remove(cardColor);
		colorOccureAfter.remove(cardColor);
		assertEquals(colorOccureBefore,colorOccureAfter);
		
	}
	
	@Test (expected=IllegalStateException.class)
	public void testAddPoliticsCardNotOwned(){
		this.MarketTestSetup();
		game.getPlayerByID(1).getCardsOwned().clear();
		MarketObject<PoliticsCard> object=new MarketObject<>(new JollyPoliticsCard(), game.getPlayerByID(1), 
				10);
		market.addProduct(object);
	}
	
	@Test
	public void testAddAssistantOwned(){
		this.MarketTestSetup();
		game.setCurrentPlayer(game.getPlayerByID(1));
		int numberOfAssistants=game.getCurrentPlayer().getAssistants();
		Assistant assistant=new Assistant(1);
		MarketObject<Assistant> object=new MarketObject<>(assistant, game.getPlayerByID(1), 
				10);
		market.addProduct(object);
		assertTrue(market.getAvailableProducts(game.getPlayerByID(2)).contains(object));
		assertTrue(game.getCurrentPlayer().getAssistants()==numberOfAssistants-1);
	}
	
	@Test
	public void testAddBuildingPermit(){
		this.MarketTestSetup();
		game.setCurrentPlayer(game.getPlayerByID(1));
		BuildingPermit permit=game.getPlayerByID(1).getAllPermits().get(0);
		MarketObject<BuildingPermit> object=new MarketObject<>(permit, game.getPlayerByID(1), 
				10);
		market.addProduct(object);
		assertTrue(market.getAvailableProducts(game.getPlayerByID(2)).contains(object));
		assertTrue(game.getCurrentPlayer().getAllPermits().isEmpty());
	}
	
	@Test
	public void testNoShowOwnedMarketObject(){
		this.MarketTestSetup();
		game.setCurrentPlayer(game.getPlayerByID(1));
		BuildingPermit permit=game.getPlayerByID(1).getAllPermits().get(0);
		MarketObject<BuildingPermit> object=new MarketObject<>(permit, game.getPlayerByID(1), 
				10);
		MarketObject<BuildingPermit> objectOfAnother=new MarketObject<>(permit, game.getPlayerByID(2), 
				10);
		market.addProduct(object);
		game.setCurrentPlayer(game.getPlayerByID(2));
		market.addProduct(objectOfAnother);
		assertFalse(market.getAvailableProducts(game.getPlayerByID(1)).contains(object));
		assertTrue(market.getAvailableProducts(game.getPlayerByID(1)).contains(objectOfAnother));
	}
	
	@Test
	public void testBuyingAnObject(){
		this.MarketTestSetup();
		game.setCurrentPlayer(game.getPlayerByID(1));
		BuildingPermit permit=game.getPlayerByID(1).getAllPermits().get(0);
		MarketObject<BuildingPermit> object=new MarketObject<>(permit, game.getPlayerByID(1), 
				10);
		market.addProduct(object);
		game.setCurrentPlayer(game.getPlayerByID(3));
		int coins3=game.getPlayerByID(3).getCoins();
		market.buyElement(game.getPlayerByID(3), object);
		assertTrue(game.getPlayerByID(3).getAllPermits().contains(permit));
		assertTrue(game.getPlayerByID(3).getCoins()==coins3-object.getPrice());
		assertTrue(game.getPlayerByID(1).getCoins()==coins3+object.getPrice());
		assertFalse(market.getAvailableProducts(game.getPlayerByID(3)).contains(permit));
	}
	
	@Test
	public void testReturnUnselled(){
		this.MarketTestSetup();
		game.setCurrentPlayer(game.getPlayerByID(1));
		BuildingPermit permit=game.getPlayerByID(1).getAllPermits().get(0);
		MarketObject<BuildingPermit> object=new MarketObject<>(permit, game.getPlayerByID(1), 
				10);
		market.addProduct(object);
		assertFalse(game.getCurrentPlayer().getAllPermits().contains(permit));
		market.returnUnselledItems();
		assertTrue(game.getCurrentPlayer().getAllPermits().contains(permit));
	}
	
	
	
	
}
