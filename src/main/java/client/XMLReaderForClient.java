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

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import client.GUI.ImagePanel;

public class XMLReaderForClient {
	private static final String PATH="src/main/resources/positionInTheMap.xml";
	
	private Element getRootFromFile(String path) throws JDOMException, IOException{
		SAXBuilder builder=new SAXBuilder();
		Document document= builder.build(new File(path));
		return document.getRootElement();
	}
	
	public void createCitiesFromRegionPanel(JPanel panel, Map<Character, String> bonuses) throws JDOMException, IOException{
		List<Element> regions=this.getRootFromFile(PATH).getChild("cityCoordinates").getChildren();
		Iterator<Element> regIt=regions.iterator();
		int i=0;
		Dimension bonusDim=new Dimension(45,45);
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
				Element city=cityIt.next();
				JPanel newPanel=new JPanel();
				newPanel.setName(city.getAttributeValue("name"));
				newPanel.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						// TODO Auto-generated method stub
						JOptionPane.showMessageDialog(null, "ciao sono "+city.getAttributeValue("name"));
					}
				});
				newPanel.setOpaque(false);
				newPanel.setBounds(Integer.parseInt(city.getAttributeValue("x"))-i*regionPanel.getWidth(), Integer.parseInt(city.getAttributeValue("y")),
						Integer.parseInt(city.getAttributeValue("width")), Integer.parseInt(city.getAttributeValue("height")));
				
				String bonusPath=bonuses.get(city.getAttributeValue("name").charAt(0));
				System.out.println(bonusPath);
				if(!bonusPath.isEmpty()){
					JPanel bonus=new ImagePanel(bonuses.get(city.getAttributeValue("name").charAt(0)), bonusDim);
					bonus.setOpaque(false);
					bonus.setBounds(newPanel.getX(), newPanel.getY(), bonusDim.width, bonusDim.height);
					bonus.setLayout(null);
					regionPanel.add(bonus);
				}
				regionPanel.add(newPanel);
				
			}
			i++;
		}
	}
}
