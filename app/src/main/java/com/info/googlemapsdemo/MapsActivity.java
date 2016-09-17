package com.info.googlemapsdemo;

import android.animation.ValueAnimator;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.net.Uri;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.Projection;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(final GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        final LatLng sydney = new LatLng(-34, 151);
       /* mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney")
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker)));*/

        final Marker TP = googleMap.addMarker(new MarkerOptions()
                .position(sydney)
                .title("Marker in Sydney")
                /*.icon(BitmapDescriptorFactory.fromResource(R.drawable.map_marker))*/);

        final Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.map_marker);
        final Bitmap target = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
        final Canvas canvas = new Canvas(target);
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setDuration(500);
        animator.setStartDelay(1000);
        final Rect originalRect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF scaledRect = new RectF();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float scale = (float) animation.getAnimatedValue();
                scaledRect.set(0, 0, originalRect.right * scale, originalRect.bottom * scale);
                canvas.drawBitmap(bitmap, originalRect, scaledRect, null);
                TP.setIcon(BitmapDescriptorFactory.fromBitmap(target));
            }
        });

        animator.start();
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }



}
