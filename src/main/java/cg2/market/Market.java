/**
 * 
 */
package cg2.market;

import java.util.ArrayList;
import java.util.List;

import cg2.model.BuildingPermit;
import cg2.player.Player;
import politics.PoliticsCard;

/**
 * @author Francesco Vetr�
 * @param <T> the type of the object 
 *
 */
public class Market<T> {

	private List<MarketObject<?>> products;
	private int elementDisplayed;
	
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
	 */
	public void addProduct(MarketObject<?> product){
		//TODO insert check on player
		this.products.add(product);
	}
	
	/**
	 * display which objects are different from different player
	 * than the one who can buy from the market and randomize the objects
	 * @param customer
	 */
	public void displayProducts(Player customer){
		List<MarketObject<?>> productsForPlayer = null;
		productsForPlayer = this.getAvailableProducts(customer);
		for(int i=0; i < products.size(); i++){
			elementDisplayed = (int) Math.random()*productsForPlayer.size();
			// to print productsForPlayer.get(elementDisplayed);
		}
	}

	/**
	 * select which objects are different from different player
	 * than the one who can buy from the market and randomize the objects
	 * @param customer
	 * @return the list of objects
	 */
	private List<MarketObject<?>> getAvailableProducts(Player customer) {
		List<MarketObject<?>> available = new ArrayList<>();
		for(MarketObject<?> o: products)
			if(o.getSellingPlayer().getPlayerID() != customer.getPlayerID())
				available.add(o);
		return null;
	}
	
	/**
	 * perform the trade of coins and the exchange of product
	 * from the list to the player who buys
	 * @param customer
	 */
	public void buyElement(Player customer){
		MarketObject<?> x = products.get(elementDisplayed);
		this.transferCoin(customer, x);
		if(x.getObject().getClass().equals(Assistant.class))
			this.assignAssistants(customer, ((Assistant)x.getObject()));
		else if(x.getObject().getClass().equals(PoliticsCard.class))
			this.assignPoliticsCard(customer, ((PoliticsCard)x.getObject()));
		else if(x.getObject().getClass().equals(BuildingPermit.class))
			this.assignBuildingLicense(customer, ((BuildingPermit)x.getObject()));
		products.remove(elementDisplayed);
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
	 */
	private void transferCoin(Player customer, MarketObject<?> x) {
		customer.setCoins(
				customer.getCoins() - x.getPrice());
		x.getSellingPlayer().setCoins(
				x.getSellingPlayer().getCoins() + x.getPrice());
		
	}
}
