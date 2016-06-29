/**
 * 
 */
package model.game;

import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.UndirectedSubgraph;

import model.bonus.Bonus;
import model.bonus.NobilityBonus;
import model.game.topology.City;

import java.awt.Color;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;
import org.jdom2.JDOMException;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.DijkstraShortestPath;
/**
 * @author Emanuele Ricciardelli
 *
 */
public class ExtendedGraph<V extends City,E> extends SimpleGraph<V, E> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6185214971194227862L;//spero esso vada bene!
	private final Class<? extends E> type;
	
	public ExtendedGraph(Class<? extends E> edgeClass) {
		super(edgeClass);
		type=edgeClass;
	}
	
	/**
	 * This method is used to fetch a vertex by its key.
	 * @param key must have the first Character in Upper Case.
	 * @return the vertex that has the param "key" as its key. It returns null if the vertex is not found.
	 * @throws NullPointerException if the key is null.
	 * @throws IllegalArgumentException if the key is empty.
	 */
	public V getVertexByKey(String key){
		if(key==null){
			throw new NullPointerException("The key must not be null");
		}else if(key.isEmpty()){
			throw new IllegalArgumentException("The key must not be empty");
		}
		
		Set<V> list=super.vertexSet();
		Iterator<V> it=list.iterator();
		V object;
		while(it.hasNext()){
			object=it.next();
			if(object.getFirstChar()==(key.toUpperCase().charAt(0))){
				return object;
			}
		}
		return null;
	}
	
	/**
	 * This method counts how many vertex are present in the shortest path between position and target vertex (the source vertex is not counted).
	 * @param position is the source vertex
	 * @param target is the target vertex
	 * @return the number of vertex
	 */
	public int howManyVertexPassed(V position, V target){
		if(position==null||target==null)
			throw new NullPointerException("One of the parameters is null");
		
		if(position.equals(target)) return 0;
		
		DijkstraShortestPath<V, E> dsp= new DijkstraShortestPath<V,E>(this, position, target);
		List<E> list= dsp.getPathEdgeList();
		return list.size();

	}
	
	/**
	 * This Method applies the bonuses of all cities where the player has already built its own emporium and which 
	 * are connected to newEmpVertex. newEmpVertex is included in those cities.  
	 * @param newEmpVertex is the vertex where the player wants to build a new emporium
	 * @param cities is a set that contains the cities where the current player has already built an emporium.
	 * @param game is the ref of the game 
	 * @throws NullPointerException is one of the parameters is null.
	 */
	public void applyConnectedCitiesBonus(V newEmpVertex, Set<V> cities, Game game){
		if(newEmpVertex==null||cities==null||game==null){
			throw new NullPointerException("One of these parameters is null");
		}
		
		UndirectedSubgraph<V,E> sub=new UndirectedSubgraph<V, E>(this, cities, null);
		ConnectivityInspector<V, E> connectivity=new ConnectivityInspector<>(sub);
		Set<V> connected=connectivity.connectedSetOf(newEmpVertex);
		for(V v:connected){
			v.applyBonus(game);
			System.out.println("Applico a "+v);
			System.out.println("I BONUS"+((City)v).getBonus());
		}
		
	}

	/**
	 * return the copy of the graph
	 * @return the cloned graph
	 */
	@Override
	public ExtendedGraph<V,E> clone(){
		ExtendedGraph<V,E> cloned=new ExtendedGraph<>(type);
		Set<V> vertex=this.vertexSet();
		Set<E> edges=this.edgeSet();
		for(V v:vertex)
			cloned.addVertex(v);
		for(E e:edges)
			cloned.addEdge(this.getEdgeSource(e), this.getEdgeTarget(e));
		
		return cloned;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String visual;
		visual="Cities:\n";

		/*for(V v:vertexSet()){
			visual=visual.concat(v.toString());
		}*/
		//visual=visual.concat("\n");
		for(E e:edgeSet()){
			visual=visual.concat("Source : "+this.getEdgeSource(e).getName()+", Target : "+this.getEdgeTarget(e).getName()+"\n");
		}
		return visual;
	}
	
	
	public static void main(String[]args) throws JDOMException, IOException{
		
		Game game=new Game();
		List<Player> players=new ArrayList<>();
		players.add(new Player("io",1));
		players.add(new Player("lui",2));
		game.setPlayers(players);
		List<Bonus> bonus=new ArrayList<>();
		game.getMap().getVertexByKey("K").registerBonus(new NobilityBonus(1));
		System.out.println("BONUS"+game.getMap().getVertexByKey("K").displayBonus());
		game.getMap().getVertexByKey("K").addEmporium(game.getPlayerByID(1));
		System.out.println(game.getPlayerByID(1));
		game.getMap().applyConnectedCitiesBonus(game.getMap().getVertexByKey("K"), game.getPlayerByID(1).getEmporiumsCitiesSet(), game);
		System.out.println(game.getPlayerByID(1));
	}
}

