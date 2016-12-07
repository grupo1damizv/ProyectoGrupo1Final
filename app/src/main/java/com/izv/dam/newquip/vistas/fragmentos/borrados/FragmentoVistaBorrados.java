package com.izv.dam.newquip.vistas.fragmentos.borrados;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.adaptadores.AdaptadorBorrados;

/**
 * Created by dam on 16/11/2016.
 */

public class FragmentoVistaBorrados extends Fragment {

    private static AdaptadorBorrados adapt;
    private static FragmentoPresentadorBorrados present;
    ViewGroup rootView;

    private static final String BACKGROUND_COLOR = "color";
    private static final String INDEX = "index";
    private int color;
    private int index;

    public static FragmentoVistaBorrados newInstance(int index, AdaptadorBorrados adaptador,
                                                     FragmentoPresentadorBorrados presentador,
                                                     int color) {

        // Instantiate a new fragment
        FragmentoVistaBorrados fragment = new FragmentoVistaBorrados();
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
        rootView = (ViewGroup) inflater.inflate(
                R.layout.activity_borrados, container, false);

        RecyclerView recyclerView = (RecyclerView) rootView.findViewById(R.id.recyclerBorrados);
        recyclerView.setHasFixedSize(true);

        LinearLayoutManager linear = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(linear);
        recyclerView.setAdapter(adapt);
        rootView.setBackgroundColor(this.color);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        rootView.setBackgroundColor(this.color);
        super.onViewCreated(view, savedInstanceState);


    }
}