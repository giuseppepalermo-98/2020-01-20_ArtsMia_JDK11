package it.polito.tdp.artsmia.model;

public class Adiacenti implements Comparable <Adiacenti> {

	private Artist artista1;
	private Artist artista2;
	private Integer peso;
	
	
	public Adiacenti(Artist artista1, Artist artista2, Integer peso) {
		super();
		this.artista1 = artista1;
		this.artista2 = artista2;
		this.peso = peso;
	}


	public Artist getArtista1() {
		return artista1;
	}


	public void setArtista1(Artist artista1) {
		this.artista1 = artista1;
	}


	public Artist getArtista2() {
		return artista2;
	}


	public void setArtista2(Artist artista2) {
		this.artista2 = artista2;
	}


	public Integer getPeso() {
		return peso;
	}


	public void setPeso(Integer peso) {
		this.peso = peso;
	}


	@Override
	public int compareTo(Adiacenti o) {
		return -(this.peso-o.getPeso());
	}
	
	
	
}
