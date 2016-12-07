package com.izv.dam.newquip.vistas.fragmentos.borrados;

import android.content.Context;
import android.database.Cursor;

import com.izv.dam.newquip.contrato.ContratoMain;
import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.pojo.Nota;

/**
 * Created by javi_ on 07/11/2016.
 */

public class FragmentoPresentadorBorrados implements ContratoMain.InterfacePresentadorBorrado {

    private ContratoMain.InterfaceModeloBorrado modelo;
    private ContratoMain.InterfaceVista vista;
    private ContratoMain.InterfaceModelo.OnDataLoadListener oyente;

    public FragmentoPresentadorBorrados(ContratoMain.InterfaceVista vista) {
        this.vista = vista;
        this.modelo = new FragmentoModeloBorrados((Context) vista);
        oyente = new ContratoMain.InterfaceModelo.OnDataLoadListener() {
            @Override
            public void setCursor(Cursor c) {
                FragmentoPresentadorBorrados.this.vista.mostrarDatos(c);
            }
        };
    }



    @Override
    public void onPause() {
        this.modelo.loadData(oyente);
    }

    @Override
    public void onResume() {
        this.modelo.loadData(oyente);
    }

    @Override
    public void onShowBorrarObjeto(int position) {
        int tipo=this.modelo.getTipo(position);
        if(tipo==1){
            Nota n=this.modelo.getNota(position);
            this.vista.mostrarConfirmarBorrarNota(n);
        }else if(tipo==2){
            Lista l=this.modelo.getLista(position);
            this.vista.mostrarConfirmarBorrarLista(l);
        }
    }

    @Override
    public void onShowVaciarPapelera(int i) {
        Object o=null;
        this.vista.mostrarConfirmarVaciarPapelera(o);
    }

    public void changeCursor(Cursor c) {
        this.modelo.changeCursor(c);
    }

    public void vaciarPapelera() {
        this.modelo.vaciarPapelera();
    }

    @Override
    public void onDeleteNota(Nota n) {
        this.modelo.deleteNota(n);
        this.modelo.loadData(oyente);
    }

    @Override
    public void onDeleteLista(Lista l) {
        this.modelo.deleteLista(l);
        this.modelo.loadData(oyente);
    }

    public long onSaveNota(Nota n) {
        return this.modelo.saveNota(n);
    }

    public long onSaveLista(Lista n) {
        return this.modelo.saveLista(n);
    }

}
