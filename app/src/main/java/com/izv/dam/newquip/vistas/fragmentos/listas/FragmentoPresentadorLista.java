package com.izv.dam.newquip.vistas.fragmentos.listas;

import android.content.Context;
import android.database.Cursor;

import com.izv.dam.newquip.contrato.ContratoLista;
import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.pojo.Nota;

/**
 * Created by dam on 23/11/2016.
 */

public class FragmentoPresentadorLista implements ContratoLista.InterfacePresentador {

    private ContratoLista.InterfaceModelo modelo;
    private ContratoLista.InterfaceVista vista;
    private ContratoLista.InterfaceModelo.OnDataLoadListener oyente;

    public FragmentoPresentadorLista(ContratoLista.InterfaceVista vista) {
        this.vista = vista;
        this.modelo = new FragmentoModeloLista((Context) vista);
        oyente = new ContratoLista.InterfaceModelo.OnDataLoadListener() {
            @Override
            public void setCursor(Cursor c) {
                FragmentoPresentadorLista.this.vista.mostrarDatos(c);
            }
        };
    }

    @Override
    public void onAddLista() {
        this.vista.mostrarAgregarLista();
    }

    @Override
    public void onDeleteLista(Lista l) {
        this.modelo.deleteLista(l);
        this.modelo.loadData(oyente);
    }

    @Override
    public void onEditLista(Lista n) {
        this.vista.mostrarEditarLista(n);
    }

    @Override
    public void onPause() {
    }

    @Override
    public void onResume() {
        this.modelo.loadData(oyente);
    }

    @Override
    public void onShowBorrarLista(int position) {
        Lista n = this.modelo.getLista(position);
        this.vista.mostrarConfirmarBorrarLista(n);
    }

    @Override
    public void onDeleteLista(int position) {
        this.modelo.deleteLista(position);
        this.modelo.loadData(oyente);
    }

    @Override
    public void onEditLista(int position) {
        Lista n = this.modelo.getLista(position);
        this.onEditLista(n);
    }

    public void changeCursor(Cursor c) {
        this.modelo.changeCursor(c);
    }

    @Override
    public long onSaveLista(Lista n) {
        return this.modelo.saveLista(n);
    }


}
