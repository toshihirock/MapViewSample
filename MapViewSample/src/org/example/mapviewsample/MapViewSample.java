package org.example.mapviewsample;

import com.google.android.maps.MapActivity;

import android.app.Activity;
import android.os.Bundle;

public class MapViewSample extends MapActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}

}