package com.imgsearch;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class imgSrchResult implements Serializable{
	private static final long serialVersionUID = 787060378764569534L;
	private String fullUrl;
	private String thumbUrl;

	public imgSrchResult(JSONObject json) {
		try {
			this.fullUrl = json.getString("url");
			this.thumbUrl = json.getString("tbUrl");
		}catch (JSONException e) {
			this.fullUrl = null;
			this.thumbUrl = null;
		}
		
		
	}
	public String getFullUrl() {
		return fullUrl;
	}
	public String getThumbUrl() {
		return thumbUrl;
	}
	public String toString(){
		return this.thumbUrl;
	}
	
	public static ArrayList<imgSrchResult> fromJSONArray(JSONArray imageJSONResults) {
		ArrayList <imgSrchResult> results = new ArrayList<imgSrchResult>();
		for (int x = 0; x< imageJSONResults.length(); x++){
			try{
				results.add(new imgSrchResult(imageJSONResults.getJSONObject(x)));
			}catch(JSONException e){
				e.printStackTrace();
			}
		}
		return results;
	}
}
