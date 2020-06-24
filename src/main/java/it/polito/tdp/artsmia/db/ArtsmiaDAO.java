package it.polito.tdp.artsmia.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.artsmia.model.Adiacenti;
import it.polito.tdp.artsmia.model.ArtObject;
import it.polito.tdp.artsmia.model.Artist;
import it.polito.tdp.artsmia.model.Exhibition;

public class ArtsmiaDAO {

	public List<ArtObject> listObjects() {
		
		String sql = "SELECT * from objects";
		List<ArtObject> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				ArtObject artObj = new ArtObject(res.getInt("object_id"), res.getString("classification"), res.getString("continent"), 
						res.getString("country"), res.getInt("curator_approved"), res.getString("dated"), res.getString("department"), 
						res.getString("medium"), res.getString("nationality"), res.getString("object_name"), res.getInt("restricted"), 
						res.getString("rights_type"), res.getString("role"), res.getString("room"), res.getString("style"), res.getString("title"));
				
				result.add(artObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Exhibition> listExhibitions() {
		
		String sql = "SELECT * from exhibitions";
		List<Exhibition> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Exhibition exObj = new Exhibition(res.getInt("exhibition_id"), res.getString("exhibition_department"), res.getString("exhibition_title"), 
						res.getInt("begin"), res.getInt("end"));
				
				result.add(exObj);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> getCategorie(){
		String sql="SELECT DISTINCT(role) " + 
				"FROM authorship " + 
				"ORDER BY role ASC ";
		List<String>result= new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				result.add(res.getString("role"));
			}
			conn.close();
			return result;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public void getArtistByCategory(String categoria, Map<Integer, Artist> idMapArtist){
		String sql="SELECT ar.artist_id, ar.name " + 
				"FROM artists ar, authorship au " + 
				"WHERE ar.artist_id=au.artist_id AND au.role=? " + 
				"GROUP BY ar.artist_id, ar.name ";
		
		

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, categoria);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				idMapArtist.put(res.getInt("artist_id"),new Artist(res.getInt("artist_id"), res.getString("name")));
			}
			
			conn.close();
		
			
		}catch (SQLException e) {
			e.printStackTrace();
			return;
		}
	}
	
	public List<Adiacenti> getAdiacenti(String categoria, Map<Integer, Artist> idMapArtist){
		String sql="SELECT ar.artist_id as id1, ar2.artist_id as id2, COUNT(*) AS peso " + 
				"FROM artists ar, authorship au, authorship au2, artists ar2, " + 
				"	  exhibition_objects e1, exhibition_objects e2 " + 
				"WHERE ar.artist_id=au.artist_id AND ar2.artist_id=au2.artist_id AND " + 
				"		au.role=au2.role and au.role=? AND ar.artist_id>ar2.artist_id AND " + 
				"		e1.object_id=au.object_id AND e2.object_id=au2.object_id AND e1.exhibition_id=e2.exhibition_id " + 
				"GROUP BY ar.artist_id, ar.name, ar2.artist_id, ar2.name ";
		
		List<Adiacenti>result= new ArrayList<>();
		
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setString(1, categoria);
			ResultSet res = st.executeQuery();
			
			while(res.next()) {
				if(idMapArtist.containsKey(res.getInt("id1")) && idMapArtist.containsKey(res.getInt("id2")) ) {
					result.add(new Adiacenti(idMapArtist.get(res.getInt("id1")),
											 idMapArtist.get(res.getInt("id2")),
											  res.getInt("peso")));
				}
			}
			
			conn.close();
			return result;
			
		}catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
