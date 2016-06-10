package controller;

import model.market.Market;
import model.market.MarketObject;
import view.View;

public class MarketChange extends Change {
	/**
	 * 
	 */
	private static final long serialVersionUID = 676905990125466125L;
	private final Market market;
	
	
	/**
	 * This class is used to bring the market ref through the network for updating the client's 
	 * game ref.
	 * @param market is the ref of marketObject in game.
	 */
	public MarketChange(Market market){
		this.market=market;
	}

	/**
	 * @return the market
	 */
	public Market getMarket() {
		return market;
	}
	
	
	
	
}
