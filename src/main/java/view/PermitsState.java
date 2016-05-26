/**
 * 
 */
package view;

import java.util.List;
import java.util.Scanner;

import model.game.BuildingPermit;

/**
 * @author Francesco Vetr�
 *
 */
public class PermitsState implements State {
	
	private List<BuildingPermit> permitsList;

	public PermitsState(List<BuildingPermit> permitsList) {
		this.permitsList = permitsList;
	}
	
	public List<BuildingPermit> getPermitsList() {
		return permitsList;
	}
	
	/* (non-Javadoc)
	 * @see cg2.view.State#display()
	 */
	/**
	 * display the face up permits for each region
	 * gives the controller the desired building permits to acquire
	 */
	@Override
	public void display() {
		System.out.println("Select the permit you want to acquire");
		for(BuildingPermit b : permitsList)
			System.out.println((permitsList.indexOf(b)+1)+" - " +b.toString());
		int selection=this.selector(1, permitsList.size());
		PermitsRequest request = new PermitsRequest();
		request.addPermit(permitsList.get(selection-1));
		/*
		 * TODO send to view server
		 * via socket/RMI
		 */
	}

}
