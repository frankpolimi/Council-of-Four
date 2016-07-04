/**
 * 
 */
package client;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.Scanner;
import java.util.logging.Logger;

import org.jdom2.JDOMException;

import client.GUI.StartScreen;

/**
 * @author Francesco Vetro'
 *
 */
public class Client {
	
	public static final Logger logger=Logger.getLogger(Client.class.getName());
	
	public static void main(String[] args) throws JDOMException, IOException {
		
		Scanner in = new Scanner(System.in);
		
		ConfigReader c = new ConfigReader();
		
		String host = c.getIP();
		int rmi = c.getRMIPort();
		int socket = c.getSocketPort();
		
		System.out.println("Welcome to the Council of Four!");
		
		
		System.out.println("Please select how you want to play");
		System.out.println("1 - Command Line Interface (CLI)");
		System.out.println("2 - Graphical User Interface (GUI)");
		do{
			String input = in.nextLine();
			if(input.equalsIgnoreCase("CLI") || Integer.parseInt(input) == 1){

				System.out.println("Please select your connection");
				System.out.println("1 - Socket");
				System.out.println("2 - Remote Method Invocation (RMI)");
				do{
					ClientView clientView=new ClientView();
					
					input = in.nextLine();
					if(!(input.equalsIgnoreCase("socket") || Integer.parseInt(input) == 1)&&!(input.equalsIgnoreCase("RMI") || Integer.parseInt(input) == 2)){
						System.out.println("Not valid value inserted");
					}else{
						ClientInterface cs = null;
						if(input.equalsIgnoreCase("socket") || Integer.parseInt(input) == 1){
							cs = new ClientSocket(host, socket, clientView); 
							
						}else{
							try{
								cs= new ClientRMI(host, rmi, clientView);
								
							}catch (NotBoundException e){
								System.out.println("Sorry! Errors Occurred. Terminating");
								break;
							}catch (JDOMException e) {
								System.out.println("Sorry! Errors Occurred. Terminating");
								break;
							}catch (IOException e) {
								System.out.println("Sorry! Errors Occurred. "+e.getMessage());
								break;
							}
							
						}
						
						try {
							cs.startClient();
							break;
						} catch (NotBoundException | AlreadyBoundException e) {
							e.printStackTrace();
						}
					}
				}while(true);
			
				break;
			}else if(input.equalsIgnoreCase("GUI") || Integer.parseInt(input) == 2){
				in.close();
				StartScreen screen=new StartScreen();

				screen.start();

				break;
			}	
		}while(true);
		
	}		
}
