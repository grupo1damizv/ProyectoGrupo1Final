package com.izv.dam.newquip.vistas.fragmentos.notas;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.adaptadores.AdaptadorNota;

import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

public class FragmentoVistaNota extends Fragment {

    private static AdaptadorNota adapt;
    private static FragmentoPresentadorNota present;

    private static final String BACKGROUND_COLOR = "color";
    private static final String INDEX = "index";
    private int color;
    private int index;

    public static FragmentoVistaNota newInstance(int index, AdaptadorNota adaptador, FragmentoPresentadorNota presentador,int color) {

        // Instantiate a new fragment
        FragmentoVistaNota fragment = new FragmentoVistaNota();
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
                R.layout.activity_quip, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linear = new LinearLayoutManager(getActivity().getBaseContext());
        recyclerView.setLayoutManager(linear);
        recyclerView.setAdapter(adapt);
        rootView.setBackgroundColor(this.color);

        return rootView;
    }


}