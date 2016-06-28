package controller;

import view.State;

public class StateChange extends Change {
	
	private static final long serialVersionUID = -8689393320577241503L;
	private final State stateChanged;
	
	/**
	 * constructor for a change that represent 
	 * the transition from one state of the game to 
	 * the next
	 * @param change
	 */
	public StateChange(State change) {
		stateChanged=change;
	}

	/**
	 * get the state in which the game has evolved
	 * @return the stateChanged
	 */
	public State getStateChanged() {
		return stateChanged;
	}
	
	
}
