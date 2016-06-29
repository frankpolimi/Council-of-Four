package client.GUI;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import client.ConnectionHandler;
import view.ChatRequest;

public class ChatHandler extends MouseAdapter{
	
	private final ConnectionHandler handler;
	private final GUI view;
	private JPanel chatPanel;
	private String name;
	
	public ChatHandler(ConnectionHandler handler, GUI view, String name) {
		this.handler=handler;
		this.view=view;
		this.name=name;
	}

	public void initializeChatPanel(){
		this.chatPanel=view.getChat();
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		super.mouseClicked(e);
		JScrollPane scroll=(JScrollPane)view.getComponentByName("scrollMessage", chatPanel);
		try{
		JTextArea messageBox=((JTextArea)scroll.getViewport().getView());
		String message=messageBox.getText();
		message=message.trim();
		if(!message.isEmpty())
			handler.sendToServer(new ChatRequest(message,view.getId(),this.name));
		messageBox.setText("");
		}catch(NullPointerException exc){
			
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}



}
