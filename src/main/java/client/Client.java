/**
 * 
 */
package client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author Francesco Vetrò
 *
 */
public class Client {
	
	public static void main(String[] args) {
		
		//Scanner in = new Scanner(System.in);
		
		System.out.println("Welcome to the Council of Four!");
		
		/*
		System.out.println("Please select how you want to play");
		System.out.println("1 - Command Line Interface (CLI)");
		System.out.println("2 - Graphical User Interface (GUI)");
		do{
			String input = in.nextLine();
			if(input.equalsIgnoreCase("CLI") || Integer.parseInt(input) == 1)
				//START CLI
				break;
			else if(input.equalsIgnoreCase("GUI") || Integer.parseInt(input) == 2)
				//START GUI
				break;
		}while(true);
		
		System.out.println("Please select your connection");
		System.out.println("1 - Socket");
		System.out.println("2 - Remote Method Invocation (RMI)");
		do{
			String input = in.nextLine();
			if(input.equalsIgnoreCase("socket") || Integer.parseInt(input) == 1)
				//START SOCKET
				break;
			else if(input.equalsIgnoreCase("RMI") || Integer.parseInt(input) == 2)
				//START RMI
				break;
		}while(true);
		*/
		
		
		try {
			ClientSocket cs = new ClientSocket();
			cs.startClient();
		}catch (UnknownHostException e){
			System.out.println("Sorry! Errors Occurred. Terminating");
		} catch (IOException e) {
			System.out.println("Sorry! Errors Occurred. Terminating");
		} 
	}

}
