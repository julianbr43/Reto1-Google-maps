package com.example.julianbritoreto1;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

public class LocationView extends RecyclerView.ViewHolder  {

    private ConstraintLayout root;
    private TextView nombre;
    private TextView score;
    private ImageView lugarImage;
    private Bitmap bitmap;
    private ImageButton seeInTheMap;
    private ButtonEyeLister lister;




    public LocationView(@NonNull View root) {
        super(root);
        this.root = (ConstraintLayout) root;
        nombre = root.findViewById(R.id.nombreLugar);
        score = root.findViewById(R.id.calificacionLugar);
        lugarImage = root.findViewById(R.id.imageLugar);
        seeInTheMap = root.findViewById(R.id.verMas);
        bitmap = null;


        seeInTheMap.setOnClickListener(
                (v)->{
                    lister.clickEye(this.nombre.getText().toString());
                }
        );

    }

    public void setLister(ButtonEyeLister lister) {
        this.lister = lister;
    }

    public ConstraintLayout getRoot() {
        return root;
    }

    public TextView getNombre() {
        return nombre;
    }

    public TextView getScore() {
        return score;
    }

    public ImageView getLugarImage() {
        return lugarImage;
    }


    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public interface ButtonEyeLister{
        void clickEye(String nombre);
    }



}
