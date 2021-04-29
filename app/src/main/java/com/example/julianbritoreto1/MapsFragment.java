package com.example.julianbritoreto1;

import android.annotation.SuppressLint;
import android.content.Context;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsFragment extends Fragment implements LocationListener, com.example.julianbritoreto1.MenuFragment.MarkerListener, com.example.julianbritoreto1.HomeActivity.ListenerZoom, QualifyView.CalificacionListener {

    private GoogleMap mMap;
    private LocationManager manager;
    private Marker me;
    private boolean zoom;
    private String nombreLugarZoom;
    private ArrayList<com.example.julianbritoreto1.MarkerMyMap> markerList;
    private QualifyView qualifyView;
    private com.example.julianbritoreto1.SearchFragment searchFragment;



    @SuppressLint("MissingPermission")
    private OnMapReadyCallback callback = new OnMapReadyCallback() {


        @Override
        public void onMapReady(GoogleMap googleMap) {
           mMap = googleMap;
           me = null;
           makeMarker();
           initialPosition();

           manager.requestLocationUpdates(LocationManager.GPS_PROVIDER,1000,2, com.example.julianbritoreto1.MapsFragment.this::onLocationChanged);
        }
    };


    public MapsFragment(){
        markerList = new ArrayList<>();
    }

    public static com.example.julianbritoreto1.MapsFragment newInstance() {
        com.example.julianbritoreto1.MapsFragment fragment = new com.example.julianbritoreto1.MapsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public void setSearchFragment(com.example.julianbritoreto1.SearchFragment searchFragment) {
        this.searchFragment = searchFragment;
    }

    public void setZoom(boolean zoom) {
        this.zoom = zoom;
    }

    public Marker getMe() {
        return me;
    }

    public ArrayList<com.example.julianbritoreto1.MarkerMyMap> getMarkerList() {
        return markerList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_maps, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment mapFragment =
                (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(callback);
        }

        manager = (LocationManager) getContext().getSystemService(Context.LOCATION_SERVICE);
    }

    @SuppressLint("MissingPermission")
    public void initialPosition(){
        android.location.Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        if(location != null){
            updateMyLocation(location);
        }
    }


    @Override
    public void onLocationChanged(@NonNull android.location.Location location) {
        updateMyLocation(location);
        isNear(location);
    }

    public void updateMyLocation(android.location.Location location){
        LatLng myPos = new LatLng(location.getLatitude(),location.getLongitude());
        if(me == null) {
            me = mMap.addMarker(new MarkerOptions().position(myPos).title("actual"));
            me.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }else{
            me.setPosition(myPos);
            me.setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE));
        }
        if(zoom == false) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(myPos, 15));
        }else{
            zoom();
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {

    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {

    }

    @Override
    public void newMarker(String nombre, LatLng latLng) {
        markerList.add(new com.example.julianbritoreto1.MarkerMyMap(nombre,latLng));
    }

    public void makeMarker(){
        for(int i = 0;i<markerList.size();i++){
            mMap.addMarker(new MarkerOptions().position(markerList.get(i).getLatLng()).title(markerList.get(i).getNombre()));
        }
    }

    public com.example.julianbritoreto1.MarkerMyMap searchMarker(String nombre){
        com.example.julianbritoreto1.MarkerMyMap markerMyMap = null;
        boolean found = false;
        for(int i=0;i<markerList.size() && found == false ;i++){
            if(markerList.get(i).getNombre().toString().equalsIgnoreCase(nombre)){
                markerMyMap = markerList.get(i);
                found = true;
            }
        }
        return markerMyMap;
    }

    public void zoom(){
        com.example.julianbritoreto1.MarkerMyMap markerMyMap = searchMarker(nombreLugarZoom);
        Log.e(">>>",nombreLugarZoom);
        if(markerMyMap != null) {
            mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(markerMyMap.getLatLng(), 15));
        }
    }

    @Override
    public void zoomEnEspecifico(String nombre) {
       this.nombreLugarZoom = nombre;
    }

    public void isNear(android.location.Location location){
        boolean found = false;
        android.location.Location temp = new android.location.Location(LocationManager.GPS_PROVIDER);
        for(int i=0;i<markerList.size() && found == false ;i++){
            temp.setLongitude(markerList.get(i).getLatLng().longitude);
            temp.setLatitude(markerList.get(i).getLatLng().latitude);
            if(location.distanceTo(temp)<100){
                qualifyView = new QualifyView(markerList.get(i).getNombre());
                qualifyView.setCalificacionListener(this);
                qualifyView.show(getFragmentManager(),"calificacion");
                found = true;
            }
        }
    }

    @Override
    public void newQualify(String nombre, float qualify) {
        ArrayList<Location> locations = searchFragment.getListaLugaresAuxes();
        boolean found = false;
        for(int i = 0; i< locations.size() && found == false; i++){
            if(nombre.equalsIgnoreCase(locations.get(i).getNombre())){
                locations.get(i).addNewCalificacion(qualify);
                locations.get(i).changeScore();
                found = true;
            }
        }
        searchFragment.setListaLugaresAuxes(locations);
    }
}