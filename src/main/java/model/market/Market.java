/**

 * 
 */
package model.market;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import controller.MarketChange;
import controller.ModelChange;
import model.game.BuildingPermit;
import model.game.Game;
import model.game.Player;
import model.game.politics.PoliticsCard;

/**
 * @author Francesco Vetr�
 * @param <T> the type of the object 
 *
 */
public class Market implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3231074513289905689L;
	private List<MarketObject<?>> products;
	private final Game game;
	
	/**
	 * constructor
	 * no parameters needed: elements will be added at the end of each turn
	 */
	public Market(Game game) {
		this.game=game;
		this.products = new ArrayList<>();
	}
	
	/**
	 * add the selected product to the sellable items
	 * @param product
	 * @throws NotEnoughResources 
	 */
	public void addProduct(MarketObject<?> product) throws IllegalStateException{
		Player sellingPlayer=game.getCurrentPlayer();

		if(product.getObject().getClass().getSuperclass().equals(PoliticsCard.class))
			if(sellingPlayer.getCardsOwned().contains(product.getObject()))
			{
				sellingPlayer.getCardsOwned().remove((PoliticsCard)product.getObject());
				this.products.add(product);
			}
			else
				throw new IllegalStateException("Impossible to add "+PoliticsCard.class.getSimpleName()+
						". You don't own one.");
		else if(product.getObject().getClass().equals(Assistant.class))
			if(sellingPlayer.checkAssistants(((Assistant)product.getObject()).getNumber()))
				this.products.add(product);
			else 
				throw new IllegalStateException("Impossible to add "+Assistant.class.getSimpleName()+
						". You own just "+sellingPlayer.getAssistants()+" "+Assistant.class.getSimpleName());
		else if(product.getObject().getClass().equals(BuildingPermit.class))
			if(sellingPlayer.getAllPermits().contains(product.getObject()))
			{
				sellingPlayer.getBuildingPermits().remove((BuildingPermit)product.getObject());
				this.products.add(product);
			}
			else 
				throw new IllegalStateException("Impossible to add "+BuildingPermit.class.getSimpleName()+
						". You don't own one.");
		game.notifyObservers(new ModelChange(game));
	}

	/**
	 * select which objects are different from different player
	 * than the one who can buy from the market and randomize the objects
	 * @param customer
	 * @return the list of objects
	 */
	public List<MarketObject<?>> getAvailableProducts(Player customer) {
		List<MarketObject<?>> available = new ArrayList<>();
		for(MarketObject<?> o: products)
			if(o.getSellingPlayer().getPlayerID() != customer.getPlayerID())
				available.add(o);
		return available;
	}
	
	/**
	 * perform the trade of coins and the exchange of product
	 * from the list to the player who buys
	 * @param customer
	 * @throws IllegalStateException if the customer has not the required coin.
	 */
	public void buyElement(Player customer, MarketObject<?> x) throws IllegalStateException{
		this.transferCoin(customer, x);
		if(x.getObject().getClass().equals(Assistant.class))
			this.assignAssistants(customer, ((Assistant)x.getObject()));
		else if(x.getObject().getClass().getSuperclass().equals(PoliticsCard.class))
			this.assignPoliticsCard(customer, ((PoliticsCard)x.getObject()));
		else if(x.getObject().getClass().equals(BuildingPermit.class))
			this.assignBuildingLicense(customer, ((BuildingPermit)x.getObject()));
		products.remove(x);
		game.notifyObservers(new ModelChange(game));
	}

	

	/**
	 * @param <T> the type of the object in MarketObject
	 * @param the owner of the element
	 */
	public void returnUnselledItems(){
		for(MarketObject<?> o : products){
			Player owner=game.getPlayerByID(o.getSellingPlayer().getPlayerID());
			if(o.getObject().getClass().equals(Assistant.class))
				this.assignAssistants(owner, ((Assistant)o.getObject()));
			else if(o.getObject().getClass().getSuperclass().equals(PoliticsCard.class))
				this.assignPoliticsCard(owner, ((PoliticsCard)o.getObject()));
			else if(o.getObject().getClass().equals(BuildingPermit.class))
				this.assignBuildingLicense(owner, ((BuildingPermit)o.getObject()));
		}
	}

	/**
	 * transfer the building license
	 * @param owner
	 * @param buildingLicense
	 */
	private void assignBuildingLicense(Player owner, BuildingPermit buildingLicense) {
		owner.addBuildingPermit(buildingLicense);
	}

	/**
	 * transfer the amount of assistants
	 * @param owner
	 * @param the object assistant
	 */
	private void assignAssistants(Player owner, Assistant a) {
		owner.setAssistants(owner.getAssistants() + a.getNumber());
	}
	
	/**
	 * transfer the politics card
	 * @param customer
	 * @param politicsCard
	 */
	private void assignPoliticsCard(Player customer, PoliticsCard politicsCard) {
		customer.addPoliticsCard(politicsCard);
	}
	
	/**
	 * the owner gets paid from the customer
	 * @param customer
	 * @param the whole object
	 * @throws IllegalStateException 
	 */
	private void transferCoin(Player customer, MarketObject<?> x) throws IllegalStateException {
		Player seller=game.getPlayerByID(x.getSellingPlayer().getPlayerID());
		if(customer.checkCoins(x.getPrice()))
			seller.setCoins(
				seller.getCoins() + x.getPrice());
		else
			throw new IllegalStateException("Impossible to buy. You only own "
					+customer.getCoins()+" instead of the "+x.getPrice()+" required");
		
	}
	
	public int getLengthAvailableProducts(Player customer){
		List<MarketObject<?>> available = new ArrayList<>();
		for(MarketObject<?> o: products)
			if(o.getSellingPlayer().getPlayerID() != customer.getPlayerID())
				available.add(o);
		return available.size();
	}

	//solo per provare.. va tolto!
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Market [products=" + products + "]";
	}
	
	
}
