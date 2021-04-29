package com.example.julianbritoreto1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

public class QualifyView extends DialogFragment implements View.OnClickListener {

    private Button calificar;
    private String nombre;
    private Button salir;
    private RatingBar calificacion;
    private TextView nombreLugar;
    private CalificacionListener calificacionListener;

    public QualifyView() {

    }

    public QualifyView(String nombre) {
        this.nombre = nombre;
    }

    public void setCalificacionListener(CalificacionListener calificacionListener) {
        this.calificacionListener = calificacionListener;
    }

    public static QualifyView newInstance() {
        QualifyView fragment = new QualifyView();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_qualify_view, container, false);
        calificar = root.findViewById(R.id.btnCalificar);
        salir = root.findViewById(R.id.btnCalificarDespues);
        calificacion = root.findViewById(R.id.calificacion);
        nombreLugar = root.findViewById(R.id.nombreLugarCalifi);
        nombreLugar.setText(nombre);

        calificar.setOnClickListener(this);
        salir.setOnClickListener(this);

        return root;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btnCalificar:
                calificacionListener.newQualify(nombreLugar.getText().toString(), calificacion.getRating());
                dismiss();
                break;

            case R.id.btnCalificarDespues:
                dismiss();
                break;
        }

    }

    public interface CalificacionListener{
        void newQualify(String nombre, float calificacion);
    }
}