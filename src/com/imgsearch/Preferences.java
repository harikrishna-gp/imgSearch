package com.imgsearch;

import java.io.Serializable;


public class Preferences implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1456579343182814968L;
	public String size;
	public String color;
	public String site;
	public String type;
	public Preferences() {
		size= "All";
		color = "All";
		site = "All";
		type = "All";
	}
	public String toUrl(){
		String urlOptions = "";
        if(!size.equals("All"))
        	urlOptions += "&imgsz=" + size;
        
        if(!color.equals("All"))
        	urlOptions += "&imgcolor=" + color;
        
        if(!type.equals("All"))
        	urlOptions += "&imgtype=" + type;

        if(!site.equals("All"))
        	urlOptions += "&as_sitesearch=" + site;
        return urlOptions;
    }
}
