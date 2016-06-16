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
	private final static String XMLPATH="src/main/resources/map.xml";
	private final static String PATH="src/main/resources/";
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
		Element root=this.getRootFromFile(XMLPATH);
		List<Element> avCouncillors=root.getChild("decks").getChild("avaliableCouncillors").getChildren();
		Iterator<Element> avIt=avCouncillors.iterator();
		while(avIt.hasNext()){
			Element councillorEl=avIt.next();
			int num=Integer.parseInt(councillorEl.getAttributeValue("number"));
			String imagePath=councillorEl.getAttributeValue("path");
			for(int i=0;i<num;i++){
				Color color=this.convert(councillorEl.getAttributeValue("RGB"));
				Councillor cons= new Councillor(color,imagePath);
				extractedCouncillors.add(cons);
			}
		}
	}
	
	/**
	 * This method draws a random bonus from the array that contains the all avaliable bonus extracted from file.
	 *@throws JDOMException - when errors occur in parsing
	 *@throws java.io.IOException - when an I/O error prevents a document from being fully parsed
	 *@return a List of bonus extracted random
	 *
	private List<Bonus> extractNewRandomicBonus() throws JDOMException, IOException{
		Random random = new Random();
		Element root=this.getRootFromFile(XMLPATH);
		int childChosen=random.nextInt(extractedCityBonus.size());
		Element child=root.getChild("cityBonusList").getChildren().stream().filter(e->Integer.parseInt(e.getAttribute("id"))==childChosen).findFirst().get();
		List<Bonus> bonusList=extractedCityBonus.remove(childChosen);
		
		return bonusList;
	}
	*/
	
	
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
		
		if(!string.equals("regional")&&!string.equals("kings")){
			throw new IllegalArgumentException("the string must be 'regional' or 'kings'");
		}

		if(string.equals("regional")&&region==null){
			throw new IllegalArgumentException("the Region must not be null");
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
	
	/**
	 * This method returns the KingsCouncil contained in the XML File
	 * @return the KingsCouncil
	 * @throws JDOMException
	 * @throws IOException
	 */
	public KingsCouncil getKingsCouncil() throws JDOMException, IOException{
		
		return (KingsCouncil)this.extractNewCouncil(null, "kings");
		
	}
	
	/**
	 * This method extracts all the City bonuses contained in the XML FILE and it puts them into the extractedCityBonus List
	 * @throws JDOMException
	 * @throws IOException
	 */
	private void fillExtractedCityBonuses() throws JDOMException, IOException{
		Element root=this.getRootFromFile(XMLPATH);
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
	
	/**
	 * This method creates a new instance of a bonus using its className.
	 * @param className is the name of the bonus class in the game
	 * @param amount is how many points the owner earns.
	 * @return the instance of the bonus required
	 * @throws Exception is Class.forName or constructor.newInstance fails
	 * @throws NullPointerException if ClassName is null
	 * @throws IllegalArgumentException if className is null or the amount is negative or zero
	 */
	private Bonus getBonus(String className, int amount) throws Exception{
	
			if(className==null){
				throw new NullPointerException("ClassName cannot be null");
			}
			
			if(className.equals("")||amount<=0){
				throw new IllegalArgumentException("The className is '' or amount is negative or zero");
			}
			
			Class<?> tile= Class.forName("model.bonus."+className);
			Constructor<?> constructor= tile.getConstructor(Integer.class);
			Bonus obj=(Bonus)constructor.newInstance(amount);
			return obj;

	}
	/**
	 * This method creates the graph that contains the game cities
	 * @param regionSet is the set of the regions contained in the XML File
	 * @return the map contained in the XML File
	 * @throws IOException 
	 * @throws JDOMException 
	 * @throws NullPointerException if the regionSet is null
	 * @throws IllegalArgumentException if the regionSet is empty
	 */
	public ExtendedGraph<City, DefaultEdge> generateMap(Set<Region> regionSet) throws JDOMException, IOException{
		if(regionSet==null){
			throw new NullPointerException("The set of game's region cannot be null");
		}
		
		if(regionSet.isEmpty()){
			throw new IllegalArgumentException("The set cannot be empty");
		}
		
		//inizializzazione grafo
		ExtendedGraph<City, DefaultEdge> graph=new ExtendedGraph<>(DefaultEdge.class);
		//XML Reader objects needed
		
		Set<Region> regions=regionSet;
		Iterator<Region> regionIt=regions.iterator();
		List<Element> links =new ArrayList<>();
		int i=0;
		while(regionIt.hasNext()){
			Region reg=regionIt.next();
			Element cityRoot=this.getRootFromFile(PATH+reg.getName()+reg.getType()+".xml");
			for(City c:reg.getCities()){
				graph.addVertex(c);
			}
			
			for(Element e:cityRoot.getChild("links").getChildren())
				links.add(e);
				
			i++;
		}
		//extraction and creation of edges
		
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
	 * This method @return how many emporiums every player could build in the game.
	 * @return the number of emporiums contained in the XML File
	 * @throws JDOMException
	 * @throws IOException
	 */
	public int getEmporiumsAvaliableNumber() throws JDOMException, IOException{
		Element root=this.getRootFromFile(XMLPATH);
	
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
		Element root=getRootFromFile(XMLPATH);
		List<Element> cardChild=root.getChild("decks").getChild("politicsDeck").getChildren();
		Iterator<Element> cardIt=cardChild.iterator();
		while(cardIt.hasNext()){
			Element card=cardIt.next();
			int num=Integer.parseInt(card.getAttributeValue("number"));
			String rgb=card.getAttributeValue("RGB");
			String imagePath=card.getAttributeValue("path");
			if(!rgb.equals("")){
				Color color=this.convert(rgb);
				for(int i=0;i<num;i++){
					ColoredPoliticsCard cpc=new ColoredPoliticsCard(color, imagePath);
					cards.add(cpc);
				}
			}else{
				for(int i=0;i<num;i++){
					JollyPoliticsCard jpc=new JollyPoliticsCard(imagePath);
					cards.add(jpc);
				}
			}
		}	
		politicsCards=new PoliticsDeck(cards);
		
		return politicsCards;
		
	}
	
	
	/**
	 * This method finds a region in the region's set passed by its name
	 * @param regions is the set of the regions where to search in.
	 * @param name is the name of the regions that I would to find
	 * @return null if the region is not found, else it returns the instance of that.
	 * @throws NullPointerException if one of the parameters is null.
	 * @throws IllegalArgumentException if the set is empty or the name is like an empty String.
	 */
	private Region getSpecificRegionByName(Set<Region> regions, String name){
		if(regions==null||name==null){
			throw new NullPointerException("The parameter values cannot be null");
		}
		
		if(regions.isEmpty()||name.equals("")){
			throw new IllegalArgumentException("The set is be empty or the name is ''");
		}
		
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
		Element root=this.getRootFromFile(XMLPATH);
		List<Element> colorTileList = root.getChild("decks").getChild(type).getChildren();
		Iterator<Element> colorIt=colorTileList.iterator();
		while(colorIt.hasNext()){
			Element tile=colorIt.next();
			String imagePath=tile.getAttributeValue("path");
			PointsTile ct;
			int points=Integer.parseInt(tile.getAttributeValue("points"));
			if(type.equals("colorTileList")){
				Color color=this.convert(tile.getAttributeValue("RGB"));
				ct=new ColorTile(points, color, imagePath);
			}else if(type.equals("regionTileList")){
				String name=tile.getAttributeValue("name");
				Region region=this.getSpecificRegionByName(regions, name);
				ct=new RegionTile(points, region, imagePath);
			}else{
				ct=new KingTile(points, imagePath);
			}
			tilesList.add(ct);
		}
		
		return tilesList;
	}
	
	
	/**
	 * This method creates the BuildingPermitDeck used in the game
	 * @param region is the element of the XML File that refer a region contained in it. It's used as a pointer to extract the permit
	 * Deck
	 * @return the PermitsDeck of the region passed
	 * @throws JDOMException
	 * @throws IOException
	 * @throws NullPointerException if region is null
	 */
	public PermitsDeck createBuildingPermitDeck(Element region) throws JDOMException, IOException{	
		if(region==null){
			throw new NullPointerException("the region cannot be null");
		}
		
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
			String imagePath="";
			while(permitCitiesAttr.hasNext()){
				Attribute attr=permitCitiesAttr.next();
				if(!attr.getName().equals("path")){
				City c=cityMap.get(attr.getValue().charAt(0));
				avCities.add(c);
				}else{
					imagePath=attr.getValue();
				}
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
			building=new BuildingPermit(avCities, bonusList, imagePath);
			permits.add(building);
		}
		PermitsDeck pd=new PermitsDeck(permits);
		return pd;
	}
	
	public String getCityBonusPath(int position) throws JDOMException, IOException{
		List<Element> bonusList=this.getRootFromFile(XMLPATH).getChild("decks").getChild("cityBonusList").getChildren();
		return bonusList.stream().filter(e->Integer.parseInt(e.getAttributeValue("id"))==position+1).findFirst().get().getAttributeValue("path");
	}
	
	/**
	 * 
	 * @return the set of Regions used in the game
	 * @throws JDOMException when errors occur in parsing
	 * @throws IOException  when an I/O error prevents a document from being fully parsed
	 */
	public Set<Region> createRegionSet(String extracted) throws JDOMException, IOException{
		Element root=this.getRootFromFile(XMLPATH);
		List<Element> children=root.getChildren();
		List<Element> regions=children.get(0).getChildren();
		Iterator<Element> iterator=regions.iterator();
		Set<Region> allRegions=new HashSet<>();
		
		//extraction of cities informations from XML file and insertion into graph map.
		int i=0;
		while(iterator.hasNext()){
			Element region=iterator.next();
			Region r;
			String regionName=region.getAttributeValue("name");
			ArrayList<City> arrayCity=new ArrayList<>();
			char type=extracted.toUpperCase().charAt(i);
			Element cityRoot=this.getRootFromFile(PATH+regionName+type+".xml");
			String regionPathName=cityRoot.getChild("region").getAttribute("path").getValue();
			Element kingCity=cityRoot.getChild("region").getChild("kingCity");
			List<Element> cities=cityRoot.getChild("region").getChild("cities").getChildren();
			Iterator<Element> cityIt=cities.iterator();
			Random random=new Random();
			while(cityIt.hasNext()){
				String initial="";
				Element city=cityIt.next();
				City c;
				Color color;
				String name=city.getAttributeValue("name");
				color=this.convert(city.getAttributeValue("RGB"));
				if(kingCity!=null){
					initial=kingCity.getAttributeValue("initial");
				}
				List<Bonus> bonusChosen=new ArrayList<>();
				String imagePath="";
				if(initial.isEmpty()||name.charAt(0)!=initial.charAt(0)){
					int randomBonusPosition=random.nextInt(this.extractedCityBonus.size());
					bonusChosen=this.extractedCityBonus.remove(randomBonusPosition);
					imagePath=this.getCityBonusPath(randomBonusPosition);
				}
				
				c=new City(name,color,bonusChosen,imagePath);
				arrayCity.add(c);
				cityMap.put(c.getFirstChar(), c);
			}
			
			RegionalCouncil rc=(RegionalCouncil)this.extractNewCouncil(region,"regional");
			PermitsDeck permits= rc.getPermitsDeck();
			r=new Region(regionName, arrayCity, rc, permits,regionPathName, type);
			allRegions.add(r);
			i++;
		}
			return allRegions;
		
	}
		
	/**
	 * This method creates the nobility lane used in the game
	 * @return the NobilityLane instance
	 * @throws JDOMException
	 * @throws IOException
	 */
	public NobilityLane createNobilityLane() throws JDOMException, IOException{
		Element root=this.getRootFromFile(XMLPATH);
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
	
	/**
	 * This method returns a reference of the XML file's root
	 * @return the root reference.
	 * @throws JDOMException
	 * @throws IOException
	 */
	private Element getRootFromFile(String path) throws JDOMException, IOException{
		//my choise: the XML file pathname is imposed by me to don't improve any errors with the 
		//file opening. The map is always avaliable at that pathname and it is not allowed to change it.
		SAXBuilder builder=new SAXBuilder();
		Document document= builder.build(new File(path));
		return document.getRootElement();
	}
	
	public int getRegionNumber() throws JDOMException, IOException{
		Element root=this.getRootFromFile(XMLPATH);
		return root.getChild("regions").getChildren().size();
	}
	
	
	public City getKingCity(Set<Region> regions, ExtendedGraph<City,DefaultEdge> map) throws JDOMException, IOException{
		Region hill=regions.stream().filter(e->e.getName().equals("hill")).findFirst().get();
		Element root=this.getRootFromFile(PATH+"hill"+hill.getType()+".xml");
		
		return map.getVertexByKey(root.getChild("region").getChild("kingCity").getAttribute("initial").getValue());
		
	}
	
	public static void main(String[] args)throws IOException, JDOMException {
		MapMaker mp=new MapMaker();
		Set<Region> reg=mp.createRegionSet("ABA");
		ExtendedGraph<City, DefaultEdge> map=mp.generateMap(reg);
		map.vertexSet().stream().forEach(System.out::println);
		System.out.println(map.toString());
		
	}
	

}
 