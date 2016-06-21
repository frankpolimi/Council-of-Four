package client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import client.GUI.ImagePanel;
import model.game.topology.City;

public class XMLReaderForClient {
	private static final String PATH="src/main/resources/positionInTheMap.xml";
	
	private Element getRootFromFile(String path) throws JDOMException, IOException{
		SAXBuilder builder=new SAXBuilder();
		Document document= builder.build(new File(path));
		return document.getRootElement();
	}
	
	public void createCitiesFromRegionPanel(JPanel panel, Map<Character, City> bonuses, Dimension boardDim) throws JDOMException, IOException{
		List<Element> regions=this.getRootFromFile(PATH).getChild("cityCoordinates").getChildren();
		Iterator<Element> regIt=regions.iterator();
		int i=0;
		Dimension bonusDim=new Dimension(45,45);
		Dimension kingDim=new Dimension(45,45);
		while(regIt.hasNext()){
			Element region=regIt.next();
			List<Component> componentList=Arrays.asList(panel.getComponents());
			JPanel regionPanel=null;
			for(Component c:componentList){
				if(c.getName()!=null&&c.getName().equals(region.getAttributeValue("name"))){
					regionPanel=(JPanel)c;
					break;
				}
			}			
			List<Element> cities=region.getChildren();
			Iterator<Element> cityIt=cities.iterator();
			while(cityIt.hasNext()){
				Element cityElement=cityIt.next();
				JPanel newPanel=new JPanel();
				newPanel.setName(cityElement.getAttributeValue("name"));
				newPanel.setLayout(null);
				newPanel.setOpaque(false);
				newPanel.setBounds((int)(Double.parseDouble(cityElement.getAttributeValue("xRel"))*boardDim.width)-i*regionPanel.getWidth(), (int)(Double.parseDouble(cityElement.getAttributeValue("yRel"))
						*boardDim.height),
						Integer.parseInt(cityElement.getAttributeValue("width")), Integer.parseInt(cityElement.getAttributeValue("height")));
				
				City city=bonuses.get(cityElement.getAttributeValue("name").charAt(0));
				/*JPanel kingPanel=new JPanel();
				kingPanel.setName("kingPanel");
				kingPanel.setBounds((newPanel.getWidth()/2)-(kingDim.width/2), (newPanel.getHeight()/2)-(kingDim.height/2), kingDim.width, kingDim.height);
				newPanel.add(kingPanel);*/
				JPanel kingPanel;
				if(!city.getBonusImagePath().isEmpty()){
					JPanel bonus=new ImagePanel(bonuses.get(cityElement.getAttributeValue("name").charAt(0)).getBonusImagePath(), bonusDim);
					bonus.setOpaque(false);
					bonus.setBounds(newPanel.getX(), newPanel.getY(), bonusDim.width, bonusDim.height);
					bonus.setLayout(null);
					regionPanel.add(bonus);
					kingPanel=new JPanel();
				}else{
					kingPanel=new ImagePanel("src/main/resources/Immagini/corona.png",kingDim);
				}
				
				kingPanel.setName("kingPanel");
				kingPanel.setOpaque(false);
				kingPanel.setBounds((newPanel.getWidth()/2)-(kingDim.width/2),(newPanel.getHeight()/2)-(kingDim.height/2), kingDim.width, kingDim.height);
				newPanel.add(kingPanel);
					
				regionPanel.add(newPanel);
			}
			i++;
		}
	}
}
