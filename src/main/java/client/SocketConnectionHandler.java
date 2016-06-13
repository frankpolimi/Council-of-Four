package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketTimeoutException;

public class SocketConnectionHandler implements ConnectionHandler {

	private Socket socket;
	private ObjectOutputStream socketOut;
	private ObjectInputStream socketIn;
	
	public SocketConnectionHandler(Socket socket) throws IOException {
		this.socket=socket;
		socketOut=new ObjectOutputStream(this.socket.getOutputStream());
		socketIn=new ObjectInputStream(this.socket.getInputStream());
	}
	
	@Override
	public void sendToServer(Object o) throws IOException {
		this.socketOut.reset();
		this.socketOut.writeObject(o);
		this.socketOut.flush();
	}

	@Override
	public void sendToClient(Object o) throws IOException {
		this.sendToServer(o);

	}

	@Override
	public Object receiveFromServer() throws ClassNotFoundException, IOException {
		return this.socketIn.readObject();
	}

	@Override
	public Object receiveFromClient() throws ClassNotFoundException, IOException {
		return this.receiveFromServer();
	}
	
	public String getName() throws ClassNotFoundException, IOException{
		this.socket.setSoTimeout(30*1000);
		String name="";
		try{
			name= (String)this.receiveFromClient();
		}catch(SocketTimeoutException e){
			System.out.println("The client is connected but it didn't insert its name in time");
			this.socket.close();
		}
		this.socket.setSoTimeout(0);		
		return name;
	}
	
	public Socket getSocket() {
		return socket;
	}
	
	public ObjectInputStream getSocketIn() {
		return socketIn;
	}
	 
	public ObjectOutputStream getSocketOut() {
		return socketOut;
	}
	
	@Override
	public void closeConnection() throws IOException {
		this.socket.close();
		this.socketIn.close();
		this.socketOut.close();
	}

}
