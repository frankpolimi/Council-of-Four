package client;

import java.io.IOException;
import java.util.Scanner;

import view.ChatRequest;

public class CLIChatHandler{

	
	private final ConnectionHandler handler;
	private final ClientView view;
	private final String name;
	
	public CLIChatHandler(ConnectionHandler handler, ClientView view, String name) {
		this.handler=handler;
		this.view=view;
		this.name=name;
	}
	
	
	
	public void fai() {
		/*Scanner scanner=new Scanner(System.in);
		while(true){
			String start=scanner.nextLine();
			if(start.equals("$")){
				synchronized(System.in){
					String message="";
					System.out.println("Insert your message and at the and press ENTER. Type $$ if you want to close the chat");
					do{
						message=scanner.nextLine();
						if(!message.equals("$$")){
							message=message.trim();
							try {
								handler.sendToServer(new ChatRequest(message, view.getId(), this.name));
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
								scanner.close();
							}
						}
					}while(!message.equals("$$"));
				}
			}
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
		synchronized(System.in){
			Scanner scanner=new Scanner(System.in);
			String message="";
			System.out.println("Insert your message and at the and press ENTER. Type $$ if you want to close the chat");
			do{
				message=scanner.nextLine();
				if(!message.equals("$$")){
					message=message.trim();
					try {
						handler.sendToServer(new ChatRequest(message, view.getId(), this.name));
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						scanner.close();
					}
				}
			}while(!message.equals("$$"));
		}
	}

}
