package com.izv.dam.newquip.vistas.listas;

import android.content.Context;

import com.izv.dam.newquip.contrato.ContratoItemLista;
import com.izv.dam.newquip.pojo.ItemLista;
import com.izv.dam.newquip.pojo.Lista;

/**
 * Created by dam on 23/11/2016.
 */

public class PresentadorLista implements ContratoItemLista.InterfacePresentador {

    private ContratoItemLista.InterfaceModelo modelo;
    private ContratoItemLista.InterfaceVista vista;

    public PresentadorLista(ContratoItemLista.InterfaceVista vista) {
        this.vista = vista;
        this.modelo = (ContratoItemLista.InterfaceModelo) new ModeloLista((Context) vista);
    }

    public void onAddItemLista() {
        ItemLista il = new ItemLista();

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onResume() {
    }

    @Override
    public long onSaveLista(Lista n) {
        return this.modelo.saveLista(n);
    }

    @Override
    public long saveItemLista(ItemLista item) {
        return this.modelo.saveItemLista(item);
    }
}