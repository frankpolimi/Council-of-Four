/**
 * 
 */
package client;

import java.io.File;
import java.io.IOException;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

/**
 * @author Francesco Vetro'
 *
 */
public class ConfigReader {
	
	private Element root;
	
	public ConfigReader() throws JDOMException, IOException {
		SAXBuilder builder=new SAXBuilder();
		Document document= builder.build(new File("src/main/resources/configIP_PORT.xml"));
		root = document.getRootElement();
	}

	public String getIP(){
		return (root.getChild("IP").getAttributeValue("value"));
	}
	
	public int getSocketPort(){
		return Integer.parseInt(root.getChild("PORT").getChild("SOCKETPORT")
				.getAttributeValue("value"));
	}
	
	public int getRMIPort(){
		return Integer.parseInt(root.getChild("PORT").getChild("RMIPORT")
				.getAttributeValue("value"));
	}
	
	public int getDisconnectionTimer(){
		return Integer.parseInt(root.getChild("DISC_TIME").getAttributeValue("value"));
	}
}
