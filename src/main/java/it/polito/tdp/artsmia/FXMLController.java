package it.polito.tdp.artsmia;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import it.polito.tdp.artsmia.model.Adiacenti;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private Button btnArtistiConnessi;

    @FXML
    private Button btnCalcolaPercorso;

    @FXML
    private ComboBox<String> boxRuolo;

    @FXML
    private TextField txtArtista;

    @FXML
    private TextArea txtResult;

    @FXML
    void doArtistiConnessi(ActionEvent event) {
    	this.txtResult.clear();
    	List<Adiacenti> result = model.getAdiacenti();
    	
    	this.txtResult.appendText("Gli artisti che hanno fatto esposizioni comuni sono: \n");
    	for(Adiacenti a: result) {
    		this.txtResult.appendText(a.getArtista1().getNomeArtista()+" - "+a.getArtista2().getNomeArtista()+" ==> "+a.getPeso()+"\n");
    	}
    }

    @FXML
    void doCalcolaPercorso(ActionEvent event) {
    	this.txtResult.clear();
    	Integer id;
    	
    	try {
    	id= Integer.parseInt(this.txtArtista.getText());
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Devi inserire un numero valido");
    		return;
    	}
    	
    	List<Artist>result=model.ricorsione(id);
    	
    	this.txtResult.appendText("Il cammino ottenuto è: \n");
    	
    	for(Artist a: result) {
    		this.txtResult.appendText(a.getNomeArtista()+"\n");
    	}
    }

    @FXML
    void doCreaGrafo(ActionEvent event) {
    	this.txtResult.clear();
    	
    	String categoria= this.boxRuolo.getValue();
    	
    	if(categoria == null) {
    		this.txtResult.appendText("ERRORE SELEZIONA UNA CATEGORIA!");
    		return;
    	}
    	
    	model.creaGrago(categoria);
    	this.txtResult.appendText("GRAFO CREATO!");
    }

    @FXML
    void initialize() {
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnArtistiConnessi != null : "fx:id=\"btnArtistiConnessi\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert btnCalcolaPercorso != null : "fx:id=\"btnCalcolaPercorso\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert boxRuolo != null : "fx:id=\"boxRuolo\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtArtista != null : "fx:id=\"txtArtista\" was not injected: check your FXML file 'Artsmia.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Artsmia.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		
		this.boxRuolo.getItems().addAll(this.model.getCategorie());
	}
}

