package com.example.julianbritoreto1;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LocationAdapter extends RecyclerView.Adapter<LocationView> implements LocationView.ButtonEyeLister {

    private ArrayList<Location> lugaresList;
    private RowListener listener;


    public LocationAdapter(){
        lugaresList = new ArrayList<>();
    }

    public void setListener(RowListener listener) {
        this.listener = listener;
    }

    public void addLugar(Location lugares){
        lugaresList.add(lugares);
        this.notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LocationView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View row = inflater.inflate(R.layout.lugarrow, null);
        ConstraintLayout rowroot = (ConstraintLayout) row;
        LocationView lugarView = new LocationView(rowroot);
        lugarView.setLister(this);
        return lugarView;
    }

    @Override
    public void onBindViewHolder(@NonNull LocationView holder, int position) {
        holder.getNombre().setText(lugaresList.get(position).getNombre());
        if(lugaresList.get(position).getCalificaciones().isEmpty()==true){
            holder.getScore().setText("NC");
        }else {
            holder.getScore().setText(String.valueOf(lugaresList.get(position).getScore()));
        }
        holder.getLugarImage().setImageBitmap(lugaresList.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return lugaresList.size();
    }

    @Override
    public void clickEye(String nombre) {
        Log.e(">>>","ClickEye");
        listener.clickInSeeMore(nombre);
    }

    public void filterList(ArrayList<Location> listaFiltrada) {
        lugaresList = listaFiltrada;
        notifyDataSetChanged();
    }


    public interface RowListener{
        void clickInSeeMore(String nombreLugar);
    }







}
