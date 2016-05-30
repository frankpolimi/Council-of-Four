/**
 * 
 */
package client;

import java.io.IOException;
import java.util.Scanner;

/**
 * @author Francesco Vetrò
 *
 */
public class ClientMain {

	public static void main(String[] args) {

		/**
		 * inserimento dell'IP
		 */
		ClientSocket cs = new ClientSocket();
		try {
			cs.startClient();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
