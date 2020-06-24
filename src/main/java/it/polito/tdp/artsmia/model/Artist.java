package it.polito.tdp.artsmia.model;

public class Artist {

	private Integer id;
	private String nomeArtista;
	
	public Artist(Integer id, String nomeArtista) {
		super();
		this.id = id;
		this.nomeArtista = nomeArtista;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNomeArtista() {
		return nomeArtista;
	}

	public void setNomeArtista(String nomeArtista) {
		this.nomeArtista = nomeArtista;
	}
	
	
}
