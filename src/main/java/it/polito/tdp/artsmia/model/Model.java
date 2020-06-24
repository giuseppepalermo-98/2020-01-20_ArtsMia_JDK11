package it.polito.tdp.artsmia.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {

	private ArtsmiaDAO dao;
	private Map<Integer, Artist> idMapArtist;
	private Graph<Artist, DefaultWeightedEdge> grafo;
	private List<Adiacenti> connessi;
	
	private List<Artist>bestPercorso;
	
	public Model() {
		dao = new ArtsmiaDAO();
		
	}
	
	public List<String> getCategorie(){
		return dao.getCategorie();
	}
	
	public void creaGrago (String categoria) {
		idMapArtist = new HashMap<>();
		dao.getArtistByCategory(categoria, idMapArtist);
		grafo= new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		
		Graphs.addAllVertices(this.grafo, idMapArtist.values());
		//System.out.println(grafo.vertexSet().size());
		connessi = dao.getAdiacenti(categoria, idMapArtist);
		
		for(Adiacenti a: connessi) {
			if(grafo.containsVertex(a.getArtista1()) && grafo.containsVertex(a.getArtista2()))
				Graphs.addEdgeWithVertices(grafo, a.getArtista1(), a.getArtista2(), a.getPeso());
		}
		//System.out.println(grafo.edgeSet().size());
	}
	
	public List<Adiacenti> getAdiacenti(){
		Collections.sort(connessi);
		return connessi;
	}
	
	public List<Artist> ricorsione(Integer id){
		bestPercorso= new ArrayList<>();
		List<Artist>parziale = new ArrayList<>();
		
		if(idMapArtist.containsKey(id))
			parziale.add(idMapArtist.get(id));
		
		cercaPercorso(parziale);
		
		return bestPercorso;
	}

	private void cercaPercorso(List<Artist> parziale) {
		
		if(parziale.size()>bestPercorso.size()) 
			bestPercorso = new ArrayList<>(parziale);
	
		
		List<Artist> vicini=Graphs.neighborListOf(grafo, parziale.get(parziale.size()-1));
		
		for(Artist a: vicini) {
			if(!parziale.contains(a) && vericaGrado(parziale, a)) {
				parziale.add(a);
				cercaPercorso(parziale);
				parziale.remove(a);
			}
		}
	}

	private boolean vericaGrado(List<Artist> parziale, Artist inserire) {
		if(parziale.size()<=1)
			return true;
		
		Double grado=grafo.getEdgeWeight(grafo.getEdge(parziale.get(0), parziale.get(1)));
		
		if(grado == grafo.getEdgeWeight(grafo.getEdge(parziale.get(parziale.size()-1), inserire)))
			return true;
		
		
		return false;
	}
} 
