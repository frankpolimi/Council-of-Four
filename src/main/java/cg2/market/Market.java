/**
 * 
 */
package cg2.market;

import java.util.ArrayList;
import java.util.List;

import cg2.model.BuildingLicense;
import cg2.player.Player;
import politics.PoliticsCard;

/**
 * @author Francesco Vetrò
 *
 */
public class Market {

	private List<MarketObject> products;
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
	public <T> void addProduct(MarketObject<T> product){
		this.products.add(product);
	}
	
	/**
	 * display which objects are different from different player
	 * than the one who can buy from the market and randomize the objects
	 * @param customer
	 */
	public void displayProducts(Player customer){
		List<MarketObject> productsForPlayer = null;
		productsForPlayer = this.getAvailableProducts(customer);
		for(int i=0; i < products.size(); i++){
			elementDisplayed = (int) Math.random()*productsForPlayer.size();
			// to print productsForPlayer.get(i);
		}
	}

	/**
	 * select which objects are different from different player
	 * than the one who can buy from the market and randomize the objects
	 * @param customer
	 * @return the list of objects
	 */
	private List<MarketObject> getAvailableProducts(Player customer) {
		List<MarketObject> available = new ArrayList<>();
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
		//TODO
	}
	
	/**
	 * @param <T> the type of the object in MarketObject
	 * @param the owner of the element
	 */
	public <T> void returnUnselledItems(Player owner){
		for(MarketObject<T> o : products)
			if(o.getSellingPlayer().getPlayerID() == owner.getPlayerID()){
				if(o.getObject().getClass().equals(Assistant.class))
					owner.getStatus().setHelpers(
							owner.getStatus().getHelpers() + 
							((Assistant)o.getObject()).getNumber());
				else if(o.getObject().getClass().equals(PoliticsCard.class));
					//TODO politics card
				else if(o.getObject().getClass().equals(BuildingLicense.class));
					//TODO building license
			}
	}
}
