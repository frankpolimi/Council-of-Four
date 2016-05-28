package controller;

import model.market.MarketObject;
import view.View;

public class MarketChange extends Change {
	/**
	 * 
	 */
	private static final long serialVersionUID = 676905990125466125L;
	private final View viewID;
	private final MarketObject<?> marketObject;
	
	
	public MarketChange(View view, MarketObject<?> marketObject) {
		this.viewID=view;
		this.marketObject=marketObject;
	}

	/**
	 * @return the viewID
	 */
	public View getViewID() {
		return viewID;
	}

	/**
	 * @return the marketObject
	 */
	public MarketObject<?> getMarketObject() {
		return marketObject;
	}
	
	
}
