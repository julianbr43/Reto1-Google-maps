package com.example.julianbritoreto1;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;

public class Model {

    private ArrayList<Location> items;
    private Location newItem;
    private Location shwItem;
    private LatLng userLocation;
    public static final int STATE_CREATING = 1;
    public static final int STATE_E_LOOKING = 2;
    public static final int STATE_G_LOOKING = 3;
    private int state;
    public int imgIdentifier;

    public Model() {
        newItem = new Location(null,"No seleccionada","No dir",0);
        shwItem = new Location(null,"No seleccionada","No dir",0);
        items = new ArrayList<>();
        state = STATE_G_LOOKING;
        imgIdentifier = 0;
        userLocation = new LatLng(0,0);
    }

    public ArrayList<Location> getItems() {
        return items;
    }

    public Location getNewItem() {
        return newItem;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public LatLng getUserLocation() {
        return userLocation;
    }

    public void setUserLocation(LatLng userLocation) {
        this.userLocation = userLocation;
    }

    public Location getShwItem() {
        return shwItem;
    }

    public void setShwItem(Location shwItem) {
        this.shwItem = shwItem;
    }

    public void addNewItem() {
        state = STATE_G_LOOKING;
        items.add(newItem);
        newItem = new Location(null,"No seleccionada","No dir",0);
    }

    public void updateDistance() {
    }
}
