package pmf.novak101.visitedplacesmapspractice;

import androidx.fragment.app.FragmentActivity;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.KeyEvent;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import pmf.novak101.visitedplacesmapspractice.databinding.ActivityMapsPlacesSelectorBinding;
import pmf.novak101.visitedplacesmapspractice.shared.ListItemPlace;

public class MapsPlacesSelector extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private ActivityMapsPlacesSelectorBinding binding;
    private List<ListItemPlace> places;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsPlacesSelectorBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        Bundle extras = getIntent().getExtras();
        places =  extras.getParcelableArrayList("items");
        addMarkers(mMap, places);

        ListItemPlace selectedPlace = extras.getParcelable("selected");
        if(selectedPlace == null && places.size() > 0) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(places.get(0).getLatitude(), places.get(0).getLongitude())));
        } else if (selectedPlace != null) {
            // LatLng noviSad = new LatLng(	45.267136,	19.833549);
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(selectedPlace.getLatitude(), selectedPlace.getLongitude())));
            mMap.animateCamera(CameraUpdateFactory.zoomTo(10));
        }

        mMap.setOnMapLongClickListener(marker -> {
            Geocoder geocoder = new Geocoder(MapsPlacesSelector.this);
            String address = "";
            try {
                List<Address> addresses = geocoder.getFromLocation(marker.latitude,marker.longitude ,1);
                if(addresses != null && addresses.get(0) != null){
                    address = addresses.get(0).getAddressLine(0);
                } else {
                    address = "Unknown address";
                }
            } catch (IOException e) {
                Log.i("IOException", " Geocoder getFromLocation made an exception");
                e.printStackTrace();
            }
            mMap.addMarker(new MarkerOptions().position( new LatLng(	marker.latitude,	marker.longitude) ).title(address));
           places.add(new ListItemPlace(address,marker.latitude, marker.longitude));


        });
    }

    @Override
    public void onBackPressed() {
        Log.i("onBackPressed", places.toString());
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra("items", (ArrayList<? extends Parcelable>) places);
        setResult(RESULT_OK, intent);
        finish();
    }


    public void addMarkers(GoogleMap mMap, List<ListItemPlace> places) {
        for (ListItemPlace place:places) {
            LatLng latLng = new LatLng(place.getLatitude(), place.getLongitude());
            mMap.addMarker(new MarkerOptions().position(latLng).title(place.getAddress()));
        }
    }
}