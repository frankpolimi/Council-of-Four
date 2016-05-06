package cg2.game;

import java.io.*;
import java.util.*;


import org.jdom2.*;
import org.jdom2.input.SAXBuilder;
import org.jgrapht.*;
import org.jgrapht.generate.RandomGraphGenerator;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

import cg2.model.City;


public class MapMaker {
	
	public static void main(String[] args)throws IOException, JDOMException {
		SAXBuilder builder=new SAXBuilder();
		
		Document document= builder.build(new File("C://Users/Carmen/Desktop/Università/INGEGNERIA DEL SOFTWARE/UtilizzoFILE2/src/main/resources/NewFile.xml"));
		Element root=document.getRootElement();
		List<Element> children=root.getChildren();
		Iterator<Element> iterator=children.iterator();
		UndirectedGraph<City, DefaultEdge> grafo2=new SimpleGraph<City, DefaultEdge>(DefaultEdge.class);
		while(iterator.hasNext()){
			Element region=iterator.next();
			System.out.println(region.toString());
			System.out.println("Regione:"+region.getAttributeValue("name"));
			System.out.println();
			List<Element> cities=region.getChildren();
			Iterator<Element> cityIt=cities.iterator();
			while(cityIt.hasNext()){
				Element city=cityIt.next();
				System.out.println("Nome "+city.getAttributeValue("name"));
				System.out.println("Iniziale "+city.getAttributeValue("initial"));
				System.out.println("Colore: "+city.getAttributeValue("color"));	
			}
			
		}
		
		
	}

}
