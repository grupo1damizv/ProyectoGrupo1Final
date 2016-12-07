package com.izv.dam.newquip.vistas.listas;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.contrato.ContratoItemLista;
import com.izv.dam.newquip.pojo.ItemLista;
import com.izv.dam.newquip.pojo.Lista;

/**
 * Created by dam on 23/11/2016.
 */

public class ModeloLista implements ContratoItemLista.InterfaceModelo {

    private Cursor cursor;

    private ContentResolver resolver;
    Uri uri = Uri.withAppendedPath(ContratoBaseDatos.TablaLista.CONTENT_URI, ContratoBaseDatos.TablaLista.TABLA);
    Uri uriItem = Uri.withAppendedPath(ContratoBaseDatos.TablaItemLista.CONTENT_URI, ContratoBaseDatos.TablaItemLista.TABLA);

    public ModeloLista(Context c) {
        this.resolver = c.getContentResolver();
    }

    @Override
    public void close() {

    }

    @Override
    public long saveLista(Lista n) {
        long r;
        if (n.getId() == 0) {
            r = this.insertLista(n);
        } else {
            r = this.updateLista(n);
        }
        return r;
    }

    public long deleteItemsLista(long id) {
        Cursor c = this.resolver.query(ContentUris.withAppendedId(uriItem, id), null, null, null, null);
        return c.getCount();
    }

    private long insertLista(Lista n) {
        if (n.getTitulo().trim().compareTo("") == 0) {
            n.setTitulo("Lista sin titulo");
        }
        ContentValues cv = new ContentValues();
        cv.put(ContratoBaseDatos.TablaLista.TITULO, n.getTitulo());
        cv.put(ContratoBaseDatos.TablaLista.BORRADO, n.getBorrado());
        return Long.parseLong(this.resolver.insert(uri, cv).getLastPathSegment());
    }

    private long updateLista(Lista n) {
        long i = deleteItemsLista(n.getId());
        if (i > 0) {
            this.resolver.delete(ContentUris.withAppendedId(uriItem, n.getId()), null, null);
        }

        if (n.getTitulo().trim().compareTo("") == 0) {
            n.setTitulo("Lista sin titulo");
        }
        ContentValues cv = new ContentValues();
        cv.put(ContratoBaseDatos.TablaLista.TITULO, n.getTitulo());
        return this.resolver.update(ContentUris.withAppendedId(uri, n.getId()), cv, null, null);
    }

    @Override
    public long saveItemLista(ItemLista n) {
        return insertItemLista(n);
    }

    public long insertItemLista(ItemLista item) {
        ContentValues cv = new ContentValues();
        cv.put(ContratoBaseDatos.TablaItemLista.TEXTO, item.getTexto());
        cv.put(ContratoBaseDatos.TablaItemLista.SELECCIONADO, item.getChecked());
        cv.put(ContratoBaseDatos.TablaItemLista.ID_FORANEO, item.getIdForaneo());
        return Long.parseLong(this.resolver.insert(uriItem, cv).getLastPathSegment());
    }
}
