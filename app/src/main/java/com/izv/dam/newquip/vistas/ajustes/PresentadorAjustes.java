package com.izv.dam.newquip.vistas.ajustes;

import android.content.Context;

import com.izv.dam.newquip.contrato.ContratoNota;
import com.izv.dam.newquip.vistas.notas.ModeloNota;

/**
 * Created by dam on 02/12/2016.
 */

public class PresentadorAjustes {

    private VistaAjustes vista;
    private ModeloAjustes modelo;

    public PresentadorAjustes(VistaAjustes vista) {
        this.vista = vista;
        this.modelo = new ModeloAjustes((Context) vista);
    }


    public void onPause() {

    }


    public void onResume() {
    }

    public int getColorNota(){
        return modelo.getColorNota();
    }

    public int getColorLista(){
        return modelo.getColorLista();
    }

    public int getColorPapelera(){
        return modelo.getColorPapelera();
    }

    public void saveAjustes(String colorNota,String colorLista,String colorPapelera){
        modelo.saveAjustes(colorNota,colorLista,colorPapelera);
    }
}
