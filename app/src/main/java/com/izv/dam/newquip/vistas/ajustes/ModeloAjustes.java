package com.izv.dam.newquip.vistas.ajustes;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

/**
 * Created by dam on 02/12/2016.
 */

public class ModeloAjustes {

    SharedPreferences prefs;
    SharedPreferences.Editor editor;

    private Context vistaContexto;
    public ModeloAjustes(Context c){
        vistaContexto=c;
        prefs = vistaContexto.getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);
        editor = prefs.edit();
    }

    public int getColorNota(){
        System.out.println(prefs.getInt("colorNota",0));
        return prefs.getInt("colorNota",0);
    }

    public int getColorLista(){
        System.out.println(prefs.getInt("colorNota",0));
        return prefs.getInt("colorLista",0);
    }

    public int getColorPapelera(){
        System.out.println(prefs.getInt("colorNota",0));
        return prefs.getInt("colorPapelera",0);
    }

    public void saveAjustes(String colorNota,String colorLista,String colorPapelera){
        editor.putInt("colorNota",Integer.parseInt(colorNota));
        editor.putInt("colorLista",Integer.parseInt(colorLista));
        editor.putInt("colorPapelera", Integer.parseInt(colorPapelera));
        editor.commit();
    }

}
