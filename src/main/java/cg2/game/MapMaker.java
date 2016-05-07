package cg2.game;

import java.awt.Color;
import java.io.*;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jgrapht.*;
import org.jgrapht.generate.RandomGraphGenerator;
import org.jgrapht.graph.ClassBasedEdgeFactory;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import cg2.model.City;


public class MapMaker {
	
	private Map<Character, City> cityMap=new HashMap<>();
	
	
	/**
	 * This Method converts a colorRBG String like "r,g,b" where r,g and b are the integer values
	 * of red, green and blue of the color represented.
	 * @param colorName
	 * @return an object of Color 
	 */
	private Color convert(String colorRGB){
		Color c;
		List<Integer> rgb=new ArrayList<>();
		StringTokenizer token=new StringTokenizer(colorRGB, ",");
		while(token.hasMoreTokens()){
			rgb.add(Integer.parseInt(token.nextToken()));
		}
		c=new Color(rgb.get(0),rgb.get(1),rgb.get(2));
		return c;	
	}
	/**
	 * This method creates the graph that contains the game cities
	 * @return
	 * @throws IOException 
	 * @throws JDOMException 
	 */
	public UndirectedGraph<City, DefaultEdge> generateMap() throws JDOMException, IOException{
		//my choise: the XML file pathname is imposed by me to don't improve any errors with the 
		//file opening. The map is always avaliable at that pathname and it is not allowed to change it.
		SAXBuilder builder=new SAXBuilder();
		//inizializzazione grafo
		UndirectedGraph<City, DefaultEdge> graph=new SimpleGraph<>(DefaultEdge.class);
		//XML Reader objects needed
		Document document= builder.build(new File("src/main/map.xml"));
		Element root=document.getRootElement();
		List<Element> children=root.getChildren();
		List<Element> regions=children.get(0).getChildren();
		Iterator<Element> iterator=regions.iterator();
		//extraction of cities informations from XML file and insertion into graph map.
		while(iterator.hasNext()){
			Element region=iterator.next();
			List<Element> cities=region.getChildren();
			Iterator<Element> cityIt=cities.iterator();
			while(cityIt.hasNext()){
				Element city=cityIt.next();
				City c;
				Color color;
				String name=city.getAttributeValue("name");
				color=this.convert(city.getAttributeValue("RGB"));
				c=new City(name,color,null);
				graph.addVertex(c);
				cityMap.put(c.getFirstChar(), c);
			}
		}
		//extraction and creation of edges
		List<Element> links=children.get(1).getChildren();
		Iterator<Element> edgeIt=links.iterator();
		while(edgeIt.hasNext()){
			Element edge=edgeIt.next();
			List<Attribute>attributes=edge.getAttributes();
			City leadCity=cityMap.get(attributes.get(0).getValue().charAt(0));
			Iterator<Attribute>attrIt=attributes.iterator();
			while(attrIt.hasNext()){
				Attribute city=attrIt.next();
				if(!city.getName().equals("city1")){
					try{
					graph.addEdge(leadCity, cityMap.get(city.getValue().charAt(0)));
					}catch(IllegalArgumentException e){
						System.out.println("Insertion not correct. One of those city doesn't exist");
					}
				}
			}			
		}
		
		return graph;
	}
	
	public static void main(String[] args)throws IOException, JDOMException {
	
	}
	
	

}
 