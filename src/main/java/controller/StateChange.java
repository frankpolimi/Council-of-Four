package controller;

import view.State;

public class StateChange extends Change {
	private static final long serialVersionUID = -8689393320577241503L;
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
