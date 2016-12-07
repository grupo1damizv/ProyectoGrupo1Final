package com.izv.dam.newquip.vistas.fragmentos.listas;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.contrato.ContratoLista;
import com.izv.dam.newquip.pojo.ItemLista;
import com.izv.dam.newquip.pojo.Lista;

import java.util.ArrayList;

/**
 * Created by dam on 23/11/2016.
 */

public class FragmentoModeloLista implements ContratoLista.InterfaceModelo {

    private Cursor cursor;

    private ContentResolver resolver;
    Uri uri = Uri.withAppendedPath(ContratoBaseDatos.TablaLista.CONTENT_URI, ContratoBaseDatos.TablaLista.TABLA);
    Uri uriItem = Uri.withAppendedPath(ContratoBaseDatos.TablaItemLista.CONTENT_URI, ContratoBaseDatos.TablaItemLista.TABLA);

    public FragmentoModeloLista(Context c) {
        this.resolver = c.getContentResolver();
    }

    @Override
    public void close() {

    }

    @Override
    public long deleteLista(Lista l) {
        long i = deleteItemsLista(l.getId());
        if (i > 0) {
            this.resolver.delete(ContentUris.withAppendedId(uriItem, l.getId()), null, null);
        }

        return this.resolver.delete(ContentUris.withAppendedId(uri, l.getId()), null, null);
    }

    public long deleteItemsLista(long id) {
        Cursor c = this.resolver.query(ContentUris.withAppendedId(uriItem, id), null, null, null, null);
        return c.getCount();
    }

    @Override
    public long deleteLista(int position) {
        cursor.moveToPosition(position);
        Lista n = Lista.getLista(cursor);
        return this.deleteLista(n);
    }

    @Override
    public Lista getLista(int position) {
        ArrayList<ItemLista> datos = new ArrayList<>();
        cursor.moveToPosition(position);
        Lista n = Lista.getLista(cursor);
        Cursor c = this.resolver.query(ContentUris.withAppendedId(uriItem, n.getId()), null, null, null, null);

        while (c.moveToNext()) {
            ItemLista l = new ItemLista();
            l.setChecked(c.getInt(c.getColumnIndex(ContratoBaseDatos.TablaItemLista.SELECCIONADO)));
            l.setId(c.getLong(c.getColumnIndex(ContratoBaseDatos.TablaItemLista._ID)));
            l.setIdForaneo(c.getLong(c.getColumnIndex(ContratoBaseDatos.TablaItemLista.ID_FORANEO)));
            l.setTexto(c.getString(c.getColumnIndex(ContratoBaseDatos.TablaItemLista.TEXTO)));
            datos.add(l);
        }
        n.setDatos(datos);
        return n;
    }

    @Override
    public void loadData(OnDataLoadListener listener) {
        cursor = resolver.query(uri, null, null, null, null);
        listener.setCursor(cursor);
    }

    public void changeCursor(Cursor c) {
        this.cursor = c;
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

        ContentValues cv = new ContentValues();
        cv.put(ContratoBaseDatos.TablaLista.BORRADO, n.getBorrado());
        return this.resolver.update(ContentUris.withAppendedId(uri, n.getId()), cv, null, null);
    }


}