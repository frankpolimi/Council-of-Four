/**
 * 
 */
package cg2.game;

import org.jgrapht.graph.SimpleGraph;
import org.jgrapht.graph.Subgraph;
import org.jgrapht.traverse.BreadthFirstIterator;

import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;

import cg2.market.MarketObject;
import cg2.model.*;
import cg2.player.Player;

import org.jgrapht.alg.BellmanFordShortestPath;
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
	 * 
	 * @param key
	 * @return the vertex that has the param "key" as its key.
	 */
	public V getVertexByKey(String key){
		Set<V> list=super.vertexSet();
		Iterator<V> it=list.iterator();
		V object;
		while(it.hasNext()){
			object=it.next();
			if(object.getFirstChar()==(key.charAt(0))){
				return object;
			}
		}
		return null;
	}
	
	/**
	 * 
	 * @param position
	 * @param target
	 * @return the number of vertex on the shortest Path between position and target one (the source vertex is not counted).
	 */
	public int howManyVertexPassed(V position, V target){
		if(position.equals(target)) return 0;
		
		DijkstraShortestPath<V, E> dsp= new DijkstraShortestPath<V,E>(this, position, target);
		List<E> list= dsp.getPathEdgeList();
		return list.size();
		
		
	}
	
	public void applyBonus(V newEmpVertex, Set<V> cities, Player player){
		if(newEmpVertex==null||cities==null||player==null){
			throw new NullPointerException("One of these parameters is null");
		}
		cities.add(newEmpVertex);
		Subgraph<V,E,ExtendedGraph<V,E>> sub=new Subgraph<V, E, ExtendedGraph<V,E>>(this, cities);
		Set<E> subEdgeSet=sub.edgeSet();
		Iterator<E> it=subEdgeSet.iterator();
		while(it.hasNext()){
			E edge=it.next();
			V obj;
			if(this.getEdgeSource(edge).equals(newEmpVertex)){
				obj=this.getEdgeTarget(edge);
			}else{
				obj=this.getEdgeSource(edge);
			}
			System.out.println("Bonus acquisiti");
			obj.stampBonusList();
			obj.applyBonus(player);
		}
		
	}

	
	
	
}
