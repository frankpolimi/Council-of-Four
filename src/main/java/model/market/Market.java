/**

 * 
 */
package model.market;

import java.util.ArrayList;
import java.util.List;

import model.game.BuildingPermit;
import model.game.Player;
import model.game.politics.PoliticsCard;

/**
 * @author Francesco Vetr�
 * @param <T> the type of the object 
 *
 */
public class Market {

	private List<MarketObject<?>> products;
	
	/**
	 * constructor
	 * no parameters needed: elements will be added at the end of each turn
	 */
	public Market() {
		this.products = new ArrayList<>();
	}
	
	/**
	 * add the selected product to the sellable items
	 * @param product
	 * @throws NotEnoughResources 
	 */
	public void addProduct(MarketObject<?> product) throws IllegalStateException{
		if(product.getObject().getClass().equals(PoliticsCard.class))
			if(product.getSellingPlayer().getCardsOwned().contains(product.getObject()))
				this.products.add(product);
			else
				throw new IllegalStateException("Impossible to add "+PoliticsCard.class.getSimpleName()+
						". You don't own one.");
		else if(product.getObject().getClass().equals(Assistant.class))
			if(((Assistant)product.getObject()).getNumber()
					<= product.getSellingPlayer().getAssistants())
				this.products.add(product);
			else 
				throw new IllegalStateException("Impossible to add "+Assistant.class.getSimpleName()+
						". You own just "+product.getSellingPlayer().getAssistants()+" "+Assistant.class.getSimpleName());
		else if(product.getObject().getClass().equals(BuildingPermit.class))
			if(product.getSellingPlayer().getAllPermits().contains(product.getObject()))
				this.products.add(product);
			else 
				throw new IllegalStateException("Impossible to add "+BuildingPermit.class.getSimpleName()+
						". You don't own one.");
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
	 * @throws IllegalStateException 
	 */
	public void buyElement(Player customer, MarketObject<?> x) throws IllegalStateException{
		this.transferCoin(customer, x);
		if(x.getObject().getClass().equals(Assistant.class))
			this.assignAssistants(customer, ((Assistant)x.getObject()));
		else if(x.getObject().getClass().equals(PoliticsCard.class))
			this.assignPoliticsCard(customer, ((PoliticsCard)x.getObject()));
		else if(x.getObject().getClass().equals(BuildingPermit.class))
			this.assignBuildingLicense(customer, ((BuildingPermit)x.getObject()));
		products.remove(x);
	}

	

	/**
	 * @param <T> the type of the object in MarketObject
	 * @param the owner of the element
	 */
	public void returnUnselledItems(Player owner){
		for(MarketObject<?> o : products)
			if(o.getSellingPlayer().getPlayerID() == owner.getPlayerID()){
				if(o.getObject().getClass().equals(Assistant.class))
					this.assignAssistants(owner, ((Assistant)o.getObject()));
				else if(o.getObject().getClass().equals(PoliticsCard.class))
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
		if(customer.checkCoins(x.getPrice()))
			x.getSellingPlayer().setCoins(
				x.getSellingPlayer().getCoins() + x.getPrice());
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
	
}
