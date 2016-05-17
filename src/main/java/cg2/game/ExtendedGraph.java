/**
 * 
 */
package cg2.game;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.Subgraph;
import org.jgrapht.graph.UndirectedSubgraph;

import java.util.*;
import cg2.model.*;

import org.jgrapht.UndirectedGraph;
import org.jgrapht.alg.ConnectivityInspector;
import org.jgrapht.alg.DijkstraShortestPath;
/**
 * @author Emanuele Ricciardelli
 *
 */
public class ExtendedGraph<V extends City,E> extends SimpleGraph<V, E> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6185214971194227862L;//spero esso vada bene!

	public ExtendedGraph(Class<? extends E> edgeClass) {
		super(edgeClass);
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
	 * are connected to newEmpVertex  
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
		}
		
	}

	
	
	
}
