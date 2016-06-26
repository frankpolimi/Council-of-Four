/**
 * 
 */
package client;

import java.io.IOException;
import java.net.UnknownHostException;
import java.rmi.AlreadyBoundException;
import java.rmi.NotBoundException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom2.JDOMException;

import client.GUI.StartScreen;

/**
 * @author Francesco Vetrò
 *
 */
public class Client {
	
	public final static Logger logger=Logger.getLogger(Client.class.getName());
	
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
					if(input.equalsIgnoreCase("socket") || Integer.parseInt(input) == 1){
						try {
							ClientSocket cs = new ClientSocket(host, socket, clientView);
							cs.startClient();
						}catch (UnknownHostException e){
							System.out.println("Sorry! Errors Occurred. Terminating");
						} catch (IOException e) {
							logger.log(Level.ALL, e.getMessage());
						} 
						break;
					}
					else if(input.equalsIgnoreCase("RMI") || Integer.parseInt(input) == 2){
						try{
							ClientRMI cr = new ClientRMI(host, rmi, clientView);
							cr.startClient();
						}catch (NotBoundException e){
							System.out.println("Sorry! Errors Occurred. Terminating");
						}catch (JDOMException e) {
							System.out.println("Sorry! Errors Occurred. Terminating");
						}catch (IOException e) {
							System.out.println("Sorry! Errors Occurred. "+e.getMessage());
						}catch (AlreadyBoundException e) {
							System.out.println("Sorry! Errors Occurred. Terminating");
						}
						break;
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
