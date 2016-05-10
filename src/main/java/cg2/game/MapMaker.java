package cg2.game;

import java.awt.Color;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

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
import council.*;


public class MapMaker {
	
	private final Map<Character, City> cityMap;
	private final List<Councillor> extractedCouncillors;
	
	//Constructor
	public MapMaker() throws JDOMException, IOException {
		this.cityMap = new HashMap<>();
		this.extractedCouncillors = new ArrayList<>();
		this.fillExtractedCouncillors();
	}
	
	//Council
	private void fillExtractedCouncillors() throws JDOMException, IOException{
		Element root=this.getRootFromFile();
		List<Element> avCouncillors=root.getChild("decks").getChild("avaliableCouncillors").getChildren();
		Iterator<Element> avIt=avCouncillors.iterator();
		while(avIt.hasNext()){
			Element councillorEl=avIt.next();
			int num=Integer.parseInt(councillorEl.getAttributeValue("number"));
			for(int i=0;i<num;i++){
				Color color=this.convert(councillorEl.getAttributeValue("RGB"));
				Councillor cons= new Councillor(color);
				extractedCouncillors.add(cons);
			}
		}
	}
	
	private RegionalCouncil extractNewRegionalCouncil(String regionName) throws JDOMException, IOException{
		Random random= new Random();
		RegionalCouncil regional;
		ArrayBlockingQueue<Councillor> elected= new ArrayBlockingQueue<>(4);
		while(elected.remainingCapacity()!=0){
			Councillor councillor=extractedCouncillors.remove(random.nextInt(extractedCouncillors.size()));
			elected.add(councillor);
		}
		regional=new RegionalCouncil(elected, this.createBuildingPermitDeck(regionName));
		return regional;
	}
	

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
	
	private Bonus getBonus(String className, int amount) throws Exception{
		Class<?> tile= Class.forName("bonus."+className);
		Constructor<?> constructor= tile.getConstructor(Integer.class);
		Bonus obj=(Bonus)constructor.newInstance(amount);
		return obj;
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
		PoliticsDeck politicsCards;
		ArrayList<PoliticsCard> cards=new ArrayList<>();
		Element root=getRootFromFile();
		List<Element> cardChild=root.getChild("decks").getChild("politicsDeck").getChildren();
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
		politicsCards=new PoliticsDeck(cards);
		
		return politicsCards;
		
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
	
	public PermitsDeck createBuildingPermitDeck(String regionName) throws JDOMException, IOException{
		Element root=this.getRootFromFile();
		List<Element> reg=root.getChild("Regions").getChildren();
		int i=0;
		Element selectedReg=new Element("");
		while(i<reg.size()&&
				!reg.get(i).getAttributeValue("name").toLowerCase().equals(regionName.toLowerCase())){
			selectedReg=reg.get(i);
			i++;
		}
		
		if(i==reg.size()){
			throw new IllegalArgumentException();
		}
		
		List<Element> permitList=selectedReg.getChild("permitsDeck").getChildren();
		Iterator <Element> permitIt=permitList.iterator();
		List<BuildingPermit> permits=new ArrayList<>();
		while(permitIt.hasNext()){
			BuildingPermit building;
			Set<City> avCities=new HashSet<>();
			Set<Bonus> bonusList=new HashSet<>();
			Element permit=permitIt.next();
			List<Attribute> permitCities=permit.getAttributes();
			Iterator<Attribute> permitCitiesAttr=permitCities.iterator();
			while(permitCitiesAttr.hasNext()){
				Attribute attr=permitCitiesAttr.next();
				City c=cityMap.get(attr.getValue().charAt(0));
				avCities.add(c);
			}
			List<Element> permitBonus=permit.getChildren();
			Iterator<Element> permitBonusIt=permitBonus.iterator();
			while(permitBonusIt.hasNext()){
				Element bonus=permitBonusIt.next();
				try{
				Bonus obj=this.getBonus(bonus.getAttributeValue("className"), Integer.parseInt(bonus.getAttributeValue("amount")));
				bonusList.add(obj);
				}catch(Exception e){e.printStackTrace();}
				
			}
			building=new BuildingPermit(avCities, bonusList);
			permits.add(building);
		}
		PermitsDeck pd=new PermitsDeck(permits);
		return pd;
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
			List<Element> cities=region.getChild("cities").getChildren();
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
			RegionalCouncil rc=this.extractNewRegionalCouncil(regionName);
			PermitsDeck permits= rc.getPermitsDeck();
			r=new Region(regionName, arrayCity, rc, permits);
			allRegions.add(r);
		}
			return allRegions;
		
	}
		
	
	
	public NobilityLane createNobilityLane() throws JDOMException, IOException{
		NobilityLane nobilityLane=new NobilityLane();
		Element root=this.getRootFromFile();
		List<Element> nobilityPosition=root.getChild("nobilityLane").getChildren();
		Iterator<Element> nobIt=nobilityPosition.iterator();
		while(nobIt.hasNext()){
			Element position=nobIt.next();
			int pos=Integer.parseInt(position.getAttributeValue("number"));
			List<Element> bonuses=position.getChildren();
			Iterator<Element> bonusIt=bonuses.iterator();
			while(bonusIt.hasNext()){
				Element bonus=bonusIt.next();
				//da provare
				try{
					Bonus obj=this.getBonus(bonus.getAttributeValue("className"), Integer.parseInt(bonus.getAttributeValue("amount")));
					System.out.println("Posizione: "+pos+"Bonus:"+obj.toString());
					}catch(Exception e){e.printStackTrace();}
			}
		}
		//da finire
		
		
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
 