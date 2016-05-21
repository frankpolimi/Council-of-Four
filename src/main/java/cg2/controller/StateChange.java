package cg2.controller;

import cg2.view.State;

public class StateChange extends Change {
	private final State stateChanged;
	
	public StateChange(State change) {
		stateChanged=change;
	}

	/**
	 * @return the stateChanged
	 */
	public State getStateChanged() {
		return stateChanged;
	}
	
	
}
