package com.izv.dam.newquip.vistas.fragmentos.notas;

import android.content.Context;
import android.database.Cursor;

import com.izv.dam.newquip.contrato.ContratoMain;
import com.izv.dam.newquip.pojo.Nota;

/**
 * Created by javi_ on 07/11/2016.
 */

public class FragmentoPresentadorNota implements ContratoMain.InterfacePresentador {

    private ContratoMain.InterfaceModelo modelo;
    private ContratoMain.InterfaceVista vista;
    private ContratoMain.InterfaceModelo.OnDataLoadListener oyente;

    public FragmentoPresentadorNota(ContratoMain.InterfaceVista vista) {
        this.vista = vista;
        this.modelo = new FragmentoModeloNota((Context) vista);
        oyente = new ContratoMain.InterfaceModelo.OnDataLoadListener() {
            @Override
            public void setCursor(Cursor c) {
                FragmentoPresentadorNota.this.vista.mostrarDatos(c);
            }
        };
    }

    @Override
    public void onAddNota() {
        this.vista.mostrarAgregarNota();
    }

    @Override
    public void onDeleteNota(Nota n) {
        this.modelo.deleteNota(n);
        this.modelo.loadData(oyente);
    }

    @Override
    public void onEditNota(Nota n) {
        this.vista.mostrarEditarNota(n);
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
    public void onShowBorrarNota(int position, int i) {
        Nota n = this.modelo.getNota(position);
        this.vista.mostrarConfirmarBorrarNota(n);
    }

    @Override
    public void onDeleteNota(int position) {
        this.modelo.deleteNota(position);
        this.modelo.loadData(oyente);
    }

    @Override
    public void onEditNota(int position) {
        Nota n = this.modelo.getNota(position);
        this.onEditNota(n);
    }

    public void changeCursor(Cursor c) {
        this.modelo.changeCursor(c);
    }

    public long onSaveNota(Nota n) {
       return this.modelo.saveNota(n);
    }

}
