package com.example.julianbritoreto1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationView> implements OnLocationAction {

    private ArrayList<Location> items;
    private ArrayList<Location> shcItems;

    private OnLocationAction obsever;

    public LocationAdapter() {
        items = new ArrayList<>();
        shcItems = new ArrayList<>();
    }

    public void addItem(Location item){
        items.add(item);
    }

    @NonNull
    @Override
    public LocationView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.location_data, parent,false);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        LocationView locItemView = new LocationView(rowroot);
        locItemView.setObserver(this);
        return locItemView;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationView holder, int position) {
        Location item = items.get(position);
        /*if(shcItems.size() == 0){
            item = shcItems.get(position);
        }*/
        holder.setItem(item);
        holder.getLocationName().setText(item.getName());
        holder.getLocationScore().setText(String.valueOf(item.getScore()));
        Bitmap image = BitmapFactory.decodeFile(item.getImageSrc());
        Bitmap thumbnail = Bitmap.createScaledBitmap(
                image,image.getWidth()/4, image.getHeight()/4,true
        );
        holder.getLocationImg().setImageBitmap(thumbnail);
        holder.getDistance().setText(String.format("%.2f",item.getUserDistance()));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(ArrayList<Location> items) {
        this.items = items;
        this.notifyDataSetChanged();
    }

    public void searchPlace(String searching){

        for (int i = 0; i < items.size(); i++) {
            for (int j = 0; j < searching.length(); j++) {
            }
        }
    }

    public void setObsever(OnLocationAction obsever) {
        this.obsever = obsever;
    }

    @Override
    public void onViewLocation(Location item) {
        obsever.onViewLocation(item);
    }
}
