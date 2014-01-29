package com.imgsearch;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

public class SettingsActivity extends Activity implements OnClickListener{
	
	private String[] sizeStrs = { "All",
					"Icon",
					"Medium",
					"XX-Large",
		 			"Huge"};
	
	private String[] colorStrs = { "All",
					"Black",
			 		"Blue",
			 		"Brown",
			 		"Gray",
			 		"Green",
			 		"Orange",
			 		"Pink",
			 		"Purple",
			 		"Red",
			 		"Teal",
			 		"White",
	 				"Yellow"};

	private String[] imgTypeStrs = { "All", 
								 "Face",
								 "Photo",
								 "Clipart",  
								 "lineart"
								};

	public Spinner  szSpin;
	public Spinner  clrSpin;
	public Spinner  imgTypeSpin;
	public EditText siteEt;
	public Button   svBtn;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setupInputHdlrs();
	}
	private void setupInputHdlrs() {
		this.siteEt = (EditText)findViewById(R.id.siteFlterEt);
		setupSzSpinner();
		setupColorSpinner();
		setupImgTypeSpinner();
		setupSvBtn();
	}
	
	private void setupSvBtn() {
		this.svBtn= (Button) findViewById(R.id.svBtn);
		svBtn.setOnClickListener(this);
	}

	private void setupSzSpinner() {
		szSpin = (Spinner) findViewById(R.id.imgSzSpnr);
		List<String> szList = new ArrayList<String>();
		for (int count = 0; count < sizeStrs.length; count++)
			szList.add(sizeStrs[count]);
	 	ArrayAdapter<String> szSpnAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, szList);
	 	szSpnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	 	szSpin.setAdapter(szSpnAdapter);		
	}

	private void setupColorSpinner() {
		clrSpin = (Spinner) findViewById(R.id.clrSpnr);
		List<String> clrList = new ArrayList<String>();
		for (int count = 0; count < colorStrs.length; count++)
			clrList.add(colorStrs[count]);
	 	ArrayAdapter<String> clrSpnAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, clrList);
	 	clrSpnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	 	clrSpin.setAdapter(clrSpnAdapter);		
	}

	 
	private void setupImgTypeSpinner() {
		imgTypeSpin = (Spinner) findViewById(R.id.imgTypeSpn);
		List<String> imgTypeList = new ArrayList<String>();
		for (int count = 0; count < imgTypeStrs.length; count++)
			imgTypeList.add(imgTypeStrs[count]);
	 	ArrayAdapter<String> imgTypeSpnAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, imgTypeList);
	 	imgTypeSpnAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
	 	imgTypeSpin.setAdapter(imgTypeSpnAdapter);		
	}
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.settings, menu);
		return true;
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
			case R.id.svBtn:
				Preferences prefs = (Preferences) getIntent().getSerializableExtra("preferences");
				Intent data = new Intent();
				// Pass relevant data back as a result
				prefs.color = this.colorStrs[clrSpin.getSelectedItemPosition()];
				prefs.type  = this.imgTypeStrs[imgTypeSpin.getSelectedItemPosition()];
				prefs.site  = this.siteEt.getText().toString();
				prefs.size  = this.sizeStrs[szSpin.getSelectedItemPosition()];
				data.putExtra("preferences", prefs);
				setResult(RESULT_OK, data); // set result code and bundle data for response
				finish();				
				break;
		}
	}
}