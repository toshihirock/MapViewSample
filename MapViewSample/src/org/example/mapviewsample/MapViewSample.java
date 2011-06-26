package org.example.mapviewsample;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapView;
import com.google.android.maps.Overlay;
import com.google.android.maps.Projection;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.widget.TextView;

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
		
	   ConcreteOverlay overlay = new ConcreteOverlay(this);
	   
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
		
		// exchange adress to location
		Geocoder mGeocoder;
		
		ConcreteOverlay(Context context) {
			mGeopoint = null;
			mCirclePaint = new Paint();
			mCirclePaint.setStyle(Paint.Style.FILL);
			mCirclePaint.setARGB(255, 255, 0, 0);
			
			mGeocoder = new Geocoder(context, Locale.JAPAN);
		}
		
		/**
		 * when mapview on tap, call this method
		 */
		public boolean onTap(GeoPoint point, MapView mapView) {
			mGeopoint = point;
			
			// 画面上部のTextViewを取得
			TextView textView = (TextView)findViewById(R.id.TextView01);
			
			// 市町村名まで取得出来たか
			boolean success = false;
			
			try {
				// 緯度経度から住所への変換処理
				List<Address> addressList = mGeocoder.getFromLocation(point.getLatitudeE6() / 1E6, point.getLongitudeE6() / 1E6, 5);
				
				for(Iterator<Address> it=addressList.iterator(); it.hasNext();) {
					Address address = it.next();
					
					String country = address.getCountryName();
					String admin = address.getAdminArea();
					String locality = address.getLocality();
					
					if(country != null && admin != null && locality != null) {
						textView.setText(country + admin + locality);
						success = true;
						break;
					}
				}
				
				if(!success) {
					textView.setText("Error");
				}
				
				textView.invalidate();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
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