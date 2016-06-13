package client;

import java.io.IOException;

public interface ConnectionHandler {
	
	public void sendToServer(Object o) throws IOException;
	public void sendToClient(Object o) throws IOException;
	public Object receiveFromServer() throws ClassNotFoundException, IOException;
	public Object receiveFromClient() throws ClassNotFoundException, IOException;
	public void closeConnection() throws IOException;
	
}
