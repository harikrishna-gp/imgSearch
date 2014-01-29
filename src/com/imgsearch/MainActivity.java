package com.imgsearch;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
public class MainActivity extends Activity  implements OnItemClickListener{
	EditText srchQry;
	Button   srchBtn;
	GridView imgViewGrid;
	Preferences prefs = new Preferences();
	ArrayList <imgSrchResult> imgReslts = new ArrayList <imgSrchResult>();
	imageResultsArrayAdapter imageAdapter;
	private final int SETTINGS_CODE = 100;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		prologue();
		imageAdapter =  new imageResultsArrayAdapter(this, imgReslts);
		
		imgViewGrid.setAdapter(imageAdapter);
		imgViewGrid.setOnItemClickListener(this);
		imgViewGrid.setOnScrollListener(new EndlessScrollListener() {
		        public void onLoadMore(int page, int totalItemsCount) {
		        	Log.e ("Loading", "Loading More");
		        	loadMore(page,  totalItemsCount);
		        	return;
		        }
		        public void loadMore(int page, int totalItemsCount) {
                    Log.e("DEBUG", "Scroll loading page " + page);
            		String qryTxt = srchQry.getText().toString();
            		performSearch(qryTxt, page, totalItemsCount);
                }
        	});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.settings, menu);
        return super.onCreateOptionsMenu(menu);
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_settings:
				Intent intnt = new Intent(getApplicationContext(),SettingsActivity.class);
		        intnt.putExtra("preferences", this.prefs);
//				intnt.putExtra("size", "size");
		        startActivityForResult(intnt, SETTINGS_CODE);
				return true;
			default:
				return super.onOptionsItemSelected(item);
	    }
	}
	
	private void prologue(){
		srchQry = (EditText) findViewById(R.id.srchQryTxt);
		srchBtn = (Button) findViewById(R.id.srchBtn);
		imgViewGrid = (GridView) findViewById(R.id.imgView);
	}
	
	// This method is to clear the hint in the text 
	// field when the user clicks the text
	public void onSearchTxtClick(View v) {
	    srchQry.setText("");
	}

	public void onImageSearch (View v){
		String qryTxt = srchQry.getText().toString();
		if (isEmptyString(srchQry)){
			Toast.makeText(this, "Please enter valid search item ", Toast.LENGTH_LONG).show();		
		}
		else {
			Toast.makeText(this, "Searching for " +qryTxt, Toast.LENGTH_LONG).show();
		}
		performSearch(qryTxt, 0,0);
	}
	private boolean isEmptyString (EditText txt){
        return txt.getText().toString().trim().length() == 0;
    }

	private void performSearch(String qryTxt, int page, int total) {
		AsyncHttpClient client = new AsyncHttpClient();
		if (page == 0)imgReslts.clear();
		String options = this.prefs.toUrl();
		Log.e("OPTIONS", options);
		for (int count = 0; count<3; count++){
			Log.e("Error", "Sending page " + page);
			client.get("https://ajax.googleapis.com/ajax/services/search/images?rsz=8&" +
					"start="+(page*8)+"&v=1.08&q=" +Uri.encode(qryTxt)+options,
					new JsonHttpResponseHandler(){
						@Override
						public void onSuccess(JSONObject response) {
							JSONArray imageJSONResults = null;
							try{
								imageJSONResults = response.getJSONObject("responseData").
														getJSONArray("results");
								imageAdapter.addAll(imgSrchResult.fromJSONArray(imageJSONResults));
							} catch (JSONException e){
								e.printStackTrace();
							}
						}
						});
			page = page +1;
		}
	}
	@Override
	public void onItemClick(AdapterView<?> adapter, View parent, int position, long rowId) {
		Intent intnt = new Intent(getApplicationContext(),ImageDisplayActivity.class);
		imgSrchResult imageInfo = (imgSrchResult) imgReslts.get(position);
        
		intnt.putExtra("result", imageInfo);
        startActivity(intnt);
    }
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  // REQUEST_CODE is defined above
	  if (resultCode == RESULT_OK && requestCode == SETTINGS_CODE) {
	     // Extract name value from result extras
	     Preferences p  = (Preferences) data.getSerializableExtra("preferences");
	     this.prefs = p;
	  }
	}	
}
