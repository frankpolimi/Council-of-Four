package cg2.controller;

import cg2.market.MarketObject;
import cg2.view.View;

public class MarketChange extends Change {
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
