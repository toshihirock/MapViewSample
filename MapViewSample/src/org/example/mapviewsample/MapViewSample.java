package org.example.mapviewsample;

import java.util.List;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.os.Bundle;

public class MapViewSample extends MapActivity {

	// add comment
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		MapView mapView = (MapView)findViewById(R.id.MapView01);
		
		// touch operaion
		mapView.setClickable(true);
		
		// When map tap, display zoom controller
		mapView.setBuiltInZoomControls(true);
		
	   ConcreteOverlay overlay = new ConcreteOverlay();
	   
	   List<Overlay> overlayList = mapView.getOverlays();
	   overlayList.add(overlay);
		
	}
	
	@Override
	protected boolean isRouteDisplayed() {
		// TODO Auto-generated method stub
		return false;
	}
	
	/**
	 * display overlay on map 
	 * @author toshihiro308
	 *
	 */
	private class ConcreteOverlay extends Overlay {
		

		// radius of circle
		private static final int CIRCLE_RADIUS = 16;
		
		// tap location point
		GeoPoint mGeopoint;
		
		Paint mCirclePaint;
		
		ConcreteOverlay() {
			mGeopoint = null;
			mCirclePaint = new Paint();
			mCirclePaint.setStyle(Paint.Style.FILL);
			mCirclePaint.setARGB(255, 255, 0, 0);
		}
		
		/**
		 * when mapview on tap, call this method
		 */
		public boolean onTap(GeoPoint point, MapView mapView) {
			mGeopoint = point;
			return super.onTap(point, mapView);
		}
		
		public void draw(Canvas canvas, MapView mapView, boolean shadow) {
			super.draw(canvas, mapView, shadow);
			
			if(!shadow) {
				if(mGeopoint != null) {
					Projection projection = mapView.getProjection();
					Point point = new Point();
					
					projection.toPixels(mGeopoint, point);
					canvas.drawCircle(point.x, point.y, CIRCLE_RADIUS, mCirclePaint);
				}
			}
		}
		
	}

}