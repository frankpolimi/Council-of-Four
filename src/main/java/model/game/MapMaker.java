package model.game;

import java.awt.Color;
import java.io.*;
import java.lang.reflect.Constructor;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jgrapht.graph.DefaultEdge;

import model.bonus.*;
import model.game.council.*;
import model.game.politics.ColoredPoliticsCard;
import model.game.politics.JollyPoliticsCard;
import model.game.politics.PoliticsCard;
import model.game.politics.PoliticsDeck;
import model.game.topology.City;
import model.game.topology.Region;


public class MapMaker {
	
	private final Map<Character, City> cityMap;
	private final List<Councillor> extractedCouncillors;
	private final List<List<Bonus>> extractedCityBonus;
	
	//Constructor
	/**
	 * This class is used to extract the necessary objects used to initialize and play a game.
	 *@throws JDOMException - when errors occur in parsing
	 *@throws java.io.IOException - when an I/O error prevents a document from being fully parsed
	 */
	public MapMaker() throws JDOMException, IOException {
		this.cityMap = new HashMap<>();
		this.extractedCouncillors = new ArrayList<>();
		this.extractedCityBonus=new ArrayList<>();
		this.fillExtractedCouncillors();
		this.fillExtractedCityBonuses();
		
	}
	
	//Council
	/**
	 * This method extracts the councillors contained into the file map and puts it into the extractedCouncillor array.
	 *@throws JDOMException - when errors occur in parsing
	 *@throws java.io.IOException - when an I/O error prevents a document from being fully parsed
	 */
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
	
	/**
	 * This method draws a random bonus from the array that contains the all avaliable bonus extracted from file.
	 *@throws JDOMException - when errors occur in parsing
	 *@throws java.io.IOException - when an I/O error prevents a document from being fully parsed
	 *@return a List of bonus extracted random
	 */
	private List<Bonus> extractNewRandomicBonus(){
		Random random = new Random();
		List<Bonus> bonusList=extractedCityBonus.remove(random.nextInt(extractedCityBonus.size()));
		return bonusList;
	}
	
	
	
	/**
	 * @return the extractedCouncillors
	 */
	public List<Councillor> getExtractedCouncillors() {
		return extractedCouncillors;
	}

	
	/**
	 * This method extracts a new council. The type of the new council is specified in the 'string' param
	 * @param region indicates the region associated to the new regional council
	 * @param string is the type of the new council. only "regional" or "kings" are allowed.
	 * @return the new extracted council
	 * @throws JDOMException
	 * @throws IOException
	 * @throws NullPointerException if one or both the parameters are null.
	 * @throws IllegalArgumentException if the string is not "regional" or "kings"
	 */
	private Council extractNewCouncil(Element region, String string) throws JDOMException, IOException{
		if(region==null||string==null){
			throw new NullPointerException("one (or both) parameter is null");
		}
		
		if(!string.equals("regional")&&!string.equals("kings")){
			throw new IllegalArgumentException("the string must be 'regional' or 'kings'");
		}
		
		Random random= new Random();
		ArrayBlockingQueue<Councillor> elected= new ArrayBlockingQueue<>(4);
		while(elected.remainingCapacity()!=0){
			Councillor councillor=extractedCouncillors.remove(random.nextInt(extractedCouncillors.size()));
			elected.add(councillor);
		}
		
		if(string.equals("regional"))
			return new RegionalCouncil(elected, this.createBuildingPermitDeck(region));
		else
			return new KingsCouncil(elected);
	}
	
	public KingsCouncil getKingsCouncil() throws JDOMException, IOException{
		
		return (KingsCouncil)this.extractNewCouncil(null, "kings");
		
	}
	
	private void fillExtractedCityBonuses() throws JDOMException, IOException{
		Element root=this.getRootFromFile();
		List<Element> bonusList=root.getChild("decks").getChild("cityBonusList").getChildren();
		Iterator<Element> bonusListIt=bonusList.iterator();
		while(bonusListIt.hasNext()){
			List<Element> bonus=bonusListIt.next().getChildren();
			Iterator<Element> bonusIt=bonus.iterator();
			List<Bonus> bonuses=new ArrayList<>();
			while(bonusIt.hasNext()){
				Element bonusCore=bonusIt.next();
				try{
					Bonus obj=this.getBonus(bonusCore.getAttributeValue("className"), 
							Integer.parseInt(bonusCore.getAttributeValue("amount")));	
					bonuses.add(obj);
				}catch(Exception e){e.printStackTrace();};
			}
			extractedCityBonus.add(bonuses);
		}
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
	
			Class<?> tile= Class.forName("model.bonus."+className);
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
	public ExtendedGraph<City, DefaultEdge> generateMap(Set<Region> regionSet) throws JDOMException, IOException{
		//inizializzazione grafo
		ExtendedGraph<City, DefaultEdge> graph=new ExtendedGraph<>(DefaultEdge.class);
		//XML Reader objects needed
		Element root=this.getRootFromFile();
		List<Element> children=root.getChildren();
		Set<Region> regions=regionSet;
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

	public int getEmporiumsAvaliableNumber() throws JDOMException, IOException{
		Element root=this.getRootFromFile();
	
		return root.getChild("emporiums").getAttribute("number").getIntValue();
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
	
	private Region getSpecificRegionByName(Set<Region> regions, String name){
		Iterator<Region> regionIt=regions.iterator();
		Region selected=null;
		while(regionIt.hasNext()){
			Region next=regionIt.next();
			if(next.getName().equals(name)){
				selected=next;
			}
		}
		return selected;
	}
		
	/**
	 * 
	 * @return an instance of the ColorTileList used in the game
	 * @throws JDOMException when errors occur in parsing
	 * @throws IOException  when an I/O error prevents a document from being fully parsed
	 * @throws NullPointerException if type is null
	 * @throws IllegalArgumentException if you want to create a RegionTile but region is null or if type is not 'colorTileList',
	 * 'kingTileList' or 'regionTileList'.
	 */
	public List<PointsTile> createTiles(String type, Set<Region> regions) throws JDOMException, IOException{
		if(type==null){
			throw new NullPointerException("Type must not be null");
		}
		if(type.equals("regionTileList")&&regions==null){
			throw new IllegalArgumentException("Region cannot be null if you want to instanciate region tiles");
		}
		if(!type.equals("regionTileList")&&!type.equals("colorTileList")&&!type.equals("kingTileList"))
			throw new IllegalArgumentException("Only regionTileList, colorTileList or kingTileList is accepted in parameter type");
		
		
		List<PointsTile> tilesList =new ArrayList<>();
		Element root=this.getRootFromFile();
		List<Element> colorTileList = root.getChild("decks").getChild(type).getChildren();
		Iterator<Element> colorIt=colorTileList.iterator();
		while(colorIt.hasNext()){
			Element tile=colorIt.next();
			PointsTile ct;
			int points=Integer.parseInt(tile.getAttributeValue("points"));
			if(type.equals("colorTileList")){
				Color color=this.convert(tile.getAttributeValue("RGB"));
				ct=new ColorTile(points, color);
			}else if(type.equals("regionTileList")){
				String name=tile.getAttributeValue("name");
				Region region=this.getSpecificRegionByName(regions, name);
				ct=new RegionTile(points, region);
			}else{
				ct=new KingTile(points);
			}
			tilesList.add(ct);
		}
		
		return tilesList;
	}
	
	public PermitsDeck createBuildingPermitDeck(Element region) throws JDOMException, IOException{	
		List<Element> permitList=region.getChild("permitsDeck").getChildren();
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
				c=new City(name,color,this.extractNewRandomicBonus());
				arrayCity.add(c);
				cityMap.put(c.getFirstChar(), c);
			}
			
			
			RegionalCouncil rc=(RegionalCouncil)this.extractNewCouncil(region,"regional");
			PermitsDeck permits= rc.getPermitsDeck();
			r=new Region(regionName, arrayCity, rc, permits);
			allRegions.add(r);
		}
			return allRegions;
		
	}
		
	
	
	public NobilityLane createNobilityLane() throws JDOMException, IOException{
		Element root=this.getRootFromFile();
		List<Element> nobilityPosition=root.getChild("nobilityLane").getChildren();
		Iterator<Element> nobIt=nobilityPosition.iterator();
		NobilityLane lane=new NobilityLane();
		while(nobIt.hasNext()){
			Element position=nobIt.next();
			int pos=Integer.parseInt(position.getAttributeValue("number"));
			List<Element> bonuses=position.getChildren();
			Iterator<Element> bonusIt=bonuses.iterator();
			List<Bonus> bonusList=new ArrayList<>();
			while(bonusIt.hasNext()){
				Element bonusElement=bonusIt.next();
				try{
					Bonus obj=this.getBonus(bonusElement.getAttributeValue("className"), Integer.parseInt(bonusElement.getAttributeValue("amount")));
					bonusList.add(obj);
					}catch(Exception e){
						e.printStackTrace();
					}
			}

			NobilityCell cell=new NobilityCell(bonusList);
			lane.setLane(pos,cell);
		}
		return lane;
	}
	
	private Element getRootFromFile() throws JDOMException, IOException{
		//my choise: the XML file pathname is imposed by me to don't improve any errors with the 
		//file opening. The map is always avaliable at that pathname and it is not allowed to change it.
		SAXBuilder builder=new SAXBuilder();
		Document document= builder.build(new File("src/main/resources/map.xml"));
		return document.getRootElement();
	}
	
	public static void main(String[] args)throws IOException, JDOMException {
		MapMaker mp=new MapMaker();
		Set<Region> set=mp.createRegionSet();
		set.stream().forEach(System.out::println);
		ExtendedGraph<City, DefaultEdge> graph=mp.generateMap(set);
		set.stream().map(e -> e.getPermitsDeck()).forEach(System.out::println);
	}
	

}
 