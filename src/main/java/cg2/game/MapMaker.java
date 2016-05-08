package cg2.game;

import java.awt.Color;
import java.io.*;
import java.util.*;
import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jgrapht.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import bonus.*;
import cg2.model.*;
import politics.ColoredPoliticsCard;
import politics.JollyPoliticsCard;
import politics.PoliticsCard;
import politics.PoliticsDeck;
import topology.Region;


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
		//inizializzazione grafo
		UndirectedGraph<City, DefaultEdge> graph=new SimpleGraph<>(DefaultEdge.class);
		//XML Reader objects needed
		Element root=this.getRootFromFile();
		List<Element> children=root.getChildren();
		Set<Region> regions=this.createRegionSet();
		Iterator<Region> regionIt=regions.iterator();
		while(regionIt.hasNext()){
			Region reg=regionIt.next();
			for(City c:reg.getCities()){
				graph.addVertex(c);
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

	/**
	 * 
	 * @return an instance of the PoliticsDeck used in the game
	 * @throws JDOMException when errors occur in parsing
	 * @throws IOException  when an I/O error prevents a document from being fully parsed
	 */
	public PoliticsDeck createPoliticsDeck() throws JDOMException, IOException{
		PoliticsDeck politicsCards= new PoliticsDeck();
		List<PoliticsCard> cards=new ArrayList<>();
		Element root=getRootFromFile();
		List<Element> cardChild=root.getChild("decks").getChild("deck").getChildren();
		Iterator<Element> cardIt=cardChild.iterator();
		while(cardIt.hasNext()){
			Element card=cardIt.next();
			int num=Integer.parseInt(card.getAttributeValue("number"));
			String rgb=card.getAttributeValue("RGB");
			if(!rgb.equals("")){
				Color color=this.convert(rgb);
				for(int i=0;i<num;i++){
					ColoredPoliticsCard cpc=new ColoredPoliticsCard(color);
					cards.add(cpc);
				}
			}else{
				for(int i=0;i<num;i++){
					JollyPoliticsCard jpc=new JollyPoliticsCard();
					cards.add(jpc);
				}
			}
		}	
		//manca la parte di inserimento nel mazzo
		return null;
		
	}
	
	/**
	 * 
	 * @return an instance of the ColorTileList used in the game
	 * @throws JDOMException when errors occur in parsing
	 * @throws IOException  when an I/O error prevents a document from being fully parsed
	 */
	public List<ColorTile> createColorTiles() throws JDOMException, IOException{
		List<ColorTile> tilesList =new ArrayList<>();
		Element root=this.getRootFromFile();
		List<Element> colorTileList = root.getChild("decks").getChild("colorTileList").getChildren();
		Iterator<Element> colorIt=colorTileList.iterator();
		while(colorIt.hasNext()){
			Element tile=colorIt.next();
			ColorTile ct;
			Color color=this.convert(tile.getAttributeValue("RGB"));
			int points=Integer.parseInt(tile.getAttributeValue("points"));
			ct=new ColorTile(points, color);
			tilesList.add(ct);
		}
		
		return tilesList;
	}
	
	/**
	 * 
	 * @return the set of Regions used in the game
	 * @throws JDOMException when errors occur in parsing
	 * @throws IOException  when an I/O error prevents a document from being fully parsed
	 */
	public Set<Region> createRegionSet() throws JDOMException, IOException{
		Element root=this.getRootFromFile();
		List<Element> children=root.getChildren();
		List<Element> regions=children.get(0).getChildren();
		Iterator<Element> iterator=regions.iterator();
		Set<Region> allRegions=new HashSet<>();
		//extraction of cities informations from XML file and insertion into graph map.
		while(iterator.hasNext()){
			Element region=iterator.next();
			Region r;
			String regionName=region.getAttributeValue("name");
			ArrayList<City> arrayCity=new ArrayList<>();
			List<Element> cities=region.getChildren();
			Iterator<Element> cityIt=cities.iterator();
			while(cityIt.hasNext()){
				Element city=cityIt.next();
				City c;
				Color color;
				String name=city.getAttributeValue("name");
				color=this.convert(city.getAttributeValue("RGB"));
				c=new City(name,color,null);
				arrayCity.add(c);
				cityMap.put(c.getFirstChar(), c);
			}
			r=new Region(regionName, arrayCity);
			allRegions.add(r);
		}
		return allRegions;
	}
	
	public NobilityLane createNobilityLane() throws JDOMException, IOException{
		NobilityLane nobilityLane=new NobilityLane();
		Element root=this.getRootFromFile();
		List<Element> nobilityPosition=root.getChild("nobilityLane").getChildren();
		Iterator<Element> nobIt=nobilityPosition.iterator();
		/*while(nobIt.hasNext()){
			Element position=nobIt.next();
			//int pos=Integer.parseInt(position.getAttributeValue("number"));
			
		}*/
		try{
			Bonus bonus =(Bonus) Class.forName("bonus.ReuseTileBonus").newInstance();
		}catch(Exception e){
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		System.out.println("OK");
		return null;
	}
	
	private Element getRootFromFile() throws JDOMException, IOException{
		//my choise: the XML file pathname is imposed by me to don't improve any errors with the 
		//file opening. The map is always avaliable at that pathname and it is not allowed to change it.
		SAXBuilder builder=new SAXBuilder();
		Document document= builder.build(new File("src/main/map.xml"));
		return document.getRootElement();
	}
	
	public static void main(String[] args)throws IOException, JDOMException {
		MapMaker mp=new MapMaker();
		mp.createNobilityLane();
	}
	
	

}
 