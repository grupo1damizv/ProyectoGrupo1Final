package com.izv.dam.newquip.vistas.ajustes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.vistas.main.ActividadPrincipal;

/**
 * Created by dam on 02/12/2016.
 */

public class VistaAjustes extends AppCompatActivity {

    // http://www.sgoliver.net/blog/preferencias-en-android-i-shared-preferences/
    private ImageButton upNota;
    private ImageButton downNota;
    private ImageButton upLista;
    private ImageButton downLista;
    private ImageButton upPapelera;
    private ImageButton downPapelera;

    private TextView numNota;
    private TextView numLista;
    private TextView numPapelera;

    private ImageView muestraNota;
    private ImageView muestraLista;
    private ImageView muestraPapelera;

    SharedPreferences prefs;
    SharedPreferences.Editor editor;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ajustes);



        upNota = (ImageButton) findViewById(R.id.upNota);
        upLista = (ImageButton) findViewById(R.id.upLista);
        upPapelera = (ImageButton) findViewById(R.id.upPapelera);
        downNota = (ImageButton) findViewById(R.id.downNota);
        downLista = (ImageButton) findViewById(R.id.downLista);
        downPapelera = (ImageButton) findViewById(R.id.downPapelera);

        numNota = (TextView) findViewById(R.id.numeroColorNota);
        numLista = (TextView) findViewById(R.id.numeroColorLista);
        numPapelera = (TextView) findViewById(R.id.numeroColorPapelera);

        muestraNota = (ImageView) findViewById(R.id.muestraNotas);
        muestraLista = (ImageView) findViewById(R.id.muestraListas);
        muestraPapelera = (ImageView) findViewById(R.id.muestraPapelera);

        prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        numNota.setText(String.valueOf(prefs.getInt("colorNota",0)));
        actualizaColorNota();
        numLista.setText(String.valueOf(prefs.getInt("colorLista",0)));
        actualizaColorLista();
        numPapelera.setText(String.valueOf(prefs.getInt("colorPapelera",0)));
        actualizaColorPapelera();

        editor = prefs.edit();

        upNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valor;
                valor = incrementar(Integer.parseInt(numNota.getText().toString()));
                numNota.setText(String.valueOf(valor));
                actualizaColorNota();
            }
        });
        upLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valor;
                valor = incrementar(Integer.parseInt(numLista.getText().toString()));
                numLista.setText(String.valueOf(valor));
                actualizaColorLista();
            }
        });
        upPapelera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valor;
                valor = incrementar(Integer.parseInt(numPapelera.getText().toString()));
                numPapelera.setText(String.valueOf(valor));
                actualizaColorPapelera();
            }
        });
        downNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valor;
                valor = reducir(Integer.parseInt(numNota.getText().toString()));
                numNota.setText(String.valueOf(valor));
                actualizaColorNota();
            }
        });
        downLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valor;
                valor = reducir(Integer.parseInt(numLista.getText().toString()));
                numLista.setText(String.valueOf(valor));
                actualizaColorLista();
            }
        });
        downPapelera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int valor;
                valor = reducir(Integer.parseInt(numPapelera.getText().toString()));
                numPapelera.setText(String.valueOf(valor));
                actualizaColorPapelera();
            }
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista, menu);
        return true;
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.guardar) {
            saveAjustes();
            Intent upIntent = NavUtils.getParentActivityIntent(this);
            if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                // This activity is NOT part of this app's task, so create a new task
                // when navigating up, with a synthesized back stack.
                TaskStackBuilder.create(this)
                        // Add all of this activity's parents to the back stack
                        .addNextIntentWithParentStack(upIntent)
                        // Navigate up to the closest parent
                        .startActivities();
            } else {
                // This activity is part of this app's task, so simply
                // navigate up to the logical parent activity.
                NavUtils.navigateUpTo(this, upIntent);
            }
            finish();
            return true;
        }
        return false;
    }

    void saveAjustes() {
        editor.putInt("colorNota",Integer.parseInt(numNota.getText().toString()));
        editor.putInt("colorLista",Integer.parseInt(numLista.getText().toString()));
        editor.putInt("colorPapelera",Integer.parseInt(numPapelera.getText().toString()));
        editor.commit();
    }

    int incrementar(int valorActual) {
        int valor = valorActual + 1;
        if (valor > 9) {
            valor = 0;
        }
        return valor;
    }

    int reducir(int valorActual) {
        int valor = valorActual - 1;
        if (valor < 0) {
            valor = 9;
        }
        return valor;
    }
    void actualizaColorNota(){
        int color=Integer.parseInt(numNota.getText().toString());
        switch(color){
            default:
                muestraNota.setColorFilter(Color.WHITE);
                break;
            case 1:
                muestraNota.setColorFilter(Color.BLACK);
                break;
            case 2:
                muestraNota.setColorFilter(Color.BLUE);
                break;
            case 3:
                muestraNota.setColorFilter(Color.CYAN);
                break;
            case 4:
                muestraNota.setColorFilter(Color.GRAY);
                break;
            case 5:
                muestraNota.setColorFilter(Color.MAGENTA);
                break;
            case 6:
                muestraNota.setColorFilter(Color.RED);
                break;
            case 7:
                muestraNota.setColorFilter(Color.YELLOW);
                break;
            case 8:
                muestraNota.setColorFilter(Color.DKGRAY);
                break;
            case 9:
                muestraNota.setColorFilter(Color.LTGRAY);
                break;
        }
    }
    void actualizaColorLista(){
        int color=Integer.parseInt(numLista.getText().toString());
        switch(color){
            default:
                muestraLista.setColorFilter(Color.WHITE);
                break;
            case 1:
                muestraLista.setColorFilter(Color.BLACK);
                break;
            case 2:
                muestraLista.setColorFilter(Color.BLUE);
                break;
            case 3:
                muestraLista.setColorFilter(Color.CYAN);
                break;
            case 4:
                muestraLista.setColorFilter(Color.GRAY);
                break;
            case 5:
                muestraLista.setColorFilter(Color.MAGENTA);
                break;
            case 6:
                muestraLista.setColorFilter(Color.RED);
                break;
            case 7:
                muestraLista.setColorFilter(Color.YELLOW);
                break;
            case 8:
                muestraLista.setColorFilter(Color.DKGRAY);
                break;
            case 9:
                muestraLista.setColorFilter(Color.LTGRAY);
                break;
        }
    }
    void actualizaColorPapelera(){
        int color=Integer.parseInt(numPapelera.getText().toString());
        switch(color){
            default:
                muestraPapelera.setColorFilter(Color.WHITE);
                break;
            case 1:
                muestraPapelera.setColorFilter(Color.BLACK);
                break;
            case 2:
                muestraPapelera.setColorFilter(Color.BLUE);
                break;
            case 3:
                muestraPapelera.setColorFilter(Color.CYAN);
                break;
            case 4:
                muestraPapelera.setColorFilter(Color.GRAY);
                break;
            case 5:
                muestraPapelera.setColorFilter(Color.MAGENTA);
                break;
            case 6:
                muestraPapelera.setColorFilter(Color.RED);
                break;
            case 7:
                muestraPapelera.setColorFilter(Color.YELLOW);
                break;
            case 8:
                muestraPapelera.setColorFilter(Color.DKGRAY);
                break;
            case 9:
                muestraPapelera.setColorFilter(Color.LTGRAY);
                break;
        }
    }
}

