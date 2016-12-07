package com.izv.dam.newquip.vistas.fragmentos.listas;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.support.v4.app.Fragment;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.adaptadores.AdaptadorLista;
import com.izv.dam.newquip.dialogo.DialogoBorrarLista;
import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.vistas.listas.VistaLista;

/**
 * Created by dam on 23/11/2016.
 */

public class FragmentoVistaLista extends Fragment {

    private static AdaptadorLista adapt;
    private static FragmentoPresentadorLista present;
    private Button btLista;

    private static final String BACKGROUND_COLOR = "color";
    private static final String INDEX = "index";
    private int color;
    private int index;

    public static FragmentoVistaLista newInstance(int index,
                                                  AdaptadorLista adaptador,
                                                  FragmentoPresentadorLista presentador,
                                                  int color) {

        // Instantiate a new fragment
        FragmentoVistaLista fragment = new FragmentoVistaLista();
        adapt = adaptador;
        present = presentador;

        // Save the parameters
        Bundle bundle = new Bundle();
        bundle.putInt(BACKGROUND_COLOR,color);
        bundle.putInt(INDEX, index);
        fragment.setArguments(bundle);
        fragment.setRetainInstance(true);

        return fragment;

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.color = (getArguments() != null) ? getArguments().getInt(
                BACKGROUND_COLOR) : Color.WHITE;
        this.index = (getArguments() != null) ? getArguments().getInt(INDEX)
                : -1;


    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ViewGroup rootView = (ViewGroup) inflater.inflate(
                R.layout.activity_lista, container, false);

        //RecyclerView

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerLista);

        LinearLayoutManager linear = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linear);
        recyclerView.setAdapter(adapt);
        rootView.setBackgroundColor(this.color);
        return rootView;

    }

}