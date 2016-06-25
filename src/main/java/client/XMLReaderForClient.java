package client;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import client.GUI.GUI;
import client.GUI.ImagePanel;
import model.game.topology.City;
import model.game.topology.Region;

public class XMLReaderForClient {
	private static final String PATH="src/main/resources/positionInTheMap.xml";
	
	
	private static final Dimension bonusDim=new Dimension(GUI.singleRegionDimension.width*1282/10000, GUI.singleRegionDimension.width*1282/10000);
	private static Dimension kingDim=bonusDim;
	
	private Element getRootFromFile(String path) throws JDOMException, IOException{
		SAXBuilder builder=new SAXBuilder();
		Document document= builder.build(new File(path));
		return document.getRootElement();
	}
	
	public void createCitiesFromRegionPanel(JPanel panel, Map<Character, City> bonuses, Dimension boardDim, Set<Region> regionSet) throws JDOMException, IOException{
		List<Element> regions=this.getRootFromFile(PATH).getChild("cityCoordinates").getChildren();
		Iterator<Element> regIt=regions.iterator();
		
		while(regIt.hasNext()){
			Element region=regIt.next();
			List<Component> componentList=Arrays.asList(panel.getComponents());
			char type = 0;
			JPanel regionPanel=null;
			for(Component c:componentList){
				if(c.getName()!=null&&c.getName().equals(region.getAttributeValue("name"))){
					regionPanel=(JPanel)c;
					type=regionSet.stream().filter(e->e.getName().equalsIgnoreCase(c.getName())).map(e->e.getType()).findFirst().get();
					break;
				}
			}

			Element child = null;
			for(Element c:region.getChildren()){
				if(c.getAttributeValue("value").charAt(0)==type){
					child=c;
					break;
				}
			}
			
			List<Element> cities=child.getChildren();
			Iterator<Element> cityIt=cities.iterator();
			while(cityIt.hasNext()){
				Element cityElement=cityIt.next();
				Dimension cityDim=new Dimension(GUI.singleRegionDimension.width*Integer.parseInt(cityElement.getAttributeValue("width"))/1000, GUI.singleRegionDimension.height*Integer.parseInt(cityElement.getAttributeValue("height"))/1000);
				JPanel newPanel=new JPanel();
				newPanel.setName(cityElement.getAttributeValue("name"));
				newPanel.setLayout(null);
				newPanel.setOpaque(false);
				newPanel.setLocation(Integer.parseInt(cityElement.getAttributeValue("xRel"))*GUI.singleRegionDimension.width/1000, 
						Integer.parseInt(cityElement.getAttributeValue("yRel"))*GUI.singleRegionDimension.height/1000);
				newPanel.setSize(cityDim);
				City city=bonuses.get(cityElement.getAttributeValue("name").charAt(0));
				JPanel kingPanel;
				if(!city.getBonusImagePath().isEmpty()){
					JPanel bonus=new ImagePanel(bonuses.get(cityElement.getAttributeValue("name").charAt(0)).getBonusImagePath(), bonusDim);
					bonus.setOpaque(false);
					bonus.setBounds(0, 0, bonusDim.width, bonusDim.height);
					bonus.setLayout(null);
					newPanel.add(bonus);
					kingPanel=new JPanel();
				}else{
					kingPanel=new ImagePanel("src/main/resources/Immagini/corona.png",kingDim);
					kingPanel.setOpaque(false);
				}
				
				kingPanel.setName("kingPanel");
				kingPanel.setOpaque(false);
				kingPanel.setBounds((newPanel.getWidth()/2)-(kingDim.width/2),(newPanel.getHeight()/2)-(kingDim.height/2), kingDim.width, kingDim.height);
				kingPanel.setSize(bonusDim);
				newPanel.add(kingPanel);
					
				regionPanel.add(newPanel);
			}
		}
	}
}
