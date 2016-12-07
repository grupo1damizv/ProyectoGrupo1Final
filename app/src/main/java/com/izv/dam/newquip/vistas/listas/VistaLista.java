package com.izv.dam.newquip.vistas.listas;

import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.adaptadores.AdaptadorItemLista;
import com.izv.dam.newquip.contrato.ContratoItemLista;
import com.izv.dam.newquip.pojo.ItemLista;
import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.util.CrearPDFs;
import com.izv.dam.newquip.vistas.notas.VistaNota;

import java.util.ArrayList;

/**
 * Created by dam on 23/11/2016.
 */

public class VistaLista extends AppCompatActivity implements ContratoItemLista.InterfaceVista {
    private ArrayList<ItemLista> datos = new ArrayList<ItemLista>();
    private EditText editTextTitulo;
    private Lista lista = new Lista();
    private PresentadorLista presentador;
    private Button nuevoItem;
    private AdaptadorItemLista adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_lista);

        presentador = new PresentadorLista(this);

        editTextTitulo = (EditText) findViewById(R.id.tituloLista);

        if (savedInstanceState != null) {
            lista = savedInstanceState.getParcelable("lista");
            mostrarLista(lista);
        } else {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                lista = b.getParcelable("lista");
                mostrarLista(lista);
            }
        }
        nuevoItem = (Button) findViewById(R.id.nuevoItemButton);
        nuevoItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adaptador.insert();

            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewLista);

        LinearLayoutManager linear = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linear);
        adaptador = new AdaptadorItemLista(datos);
        recyclerView.setAdapter(adaptador);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.guardar) {
            if (datos.size() == 0) {
                Toast.makeText(VistaLista.this, "Introduzca algun item", Toast.LENGTH_LONG).show();
            } else {
                saveLista();
                finish();
                return true;
            }

        }
        return false;
    }

    @Override
    protected void onPause() {
        presentador.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        presentador.onResume();
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        lista.setDatos(datos);
        outState.putParcelable("lista", lista);
        super.onSaveInstanceState(outState);

    }

    @Override
    public void mostrarLista(Lista n) {
        editTextTitulo.setText(lista.getTitulo());
        datos = lista.getDatos();
    }

    private void saveLista() {
        lista.setTitulo(editTextTitulo.getText().toString());

        long r = presentador.onSaveLista(lista);
        if (r > 0 & lista.getId() == 0) {
            lista.setId(r);
        }

        for (ItemLista item : datos) {
            item.setIdForaneo(lista.getId());
            presentador.saveItemLista(item);
        }


    }
}