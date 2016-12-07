package com.izv.dam.newquip.vistas.fragmentos.borrados;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.contrato.ContratoMain;
import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.pojo.Nota;

/**
 * Created by javi_ on 07/11/2016.
 */

public class FragmentoModeloBorrados implements ContratoMain.InterfaceModeloBorrado {

    private Cursor cursor;
    private ContentResolver resolver;
    Uri uri = Uri.withAppendedPath(ContratoBaseDatos.TablaNota.CONTENT_URI_BORRADOS, ContratoBaseDatos.TablaNota.TABLA);
    Uri uriListas=Uri.withAppendedPath(ContratoBaseDatos.TablaLista.CONTENT_URI_BORRADOS, ContratoBaseDatos.TablaLista.TABLA);
    Uri uriItem=Uri.withAppendedPath(ContratoBaseDatos.TablaItemLista.CONTENT_URI_BORRADOS, ContratoBaseDatos.TablaItemLista.TABLA);

    public FragmentoModeloBorrados(Context c) {
        this.resolver = c.getContentResolver();
    }

    @Override
    public void close() {

    }

    @Override
    public void loadData(ContratoMain.InterfaceModelo.OnDataLoadListener listener) {
        cursor = this.resolver.query(uri, null, null, null, null);
        listener.setCursor(cursor);
    }

    public void changeCursor(Cursor c) {
        this.cursor = c;
    }


    public int getTipo(int pos){
        cursor.moveToFirst();
        cursor.moveToPosition(pos);
        int tipo=cursor.getInt(cursor.getColumnIndex("tipo"));
        return tipo;
    }

    @Override
    public Nota getNota(int pos) {
        cursor.moveToFirst();
        cursor.moveToPosition(pos);
        Nota n = Nota.getNota(cursor);
        return n;
    }

    @Override
    public Lista getLista(int pos) {
        cursor.moveToFirst();
        cursor.moveToPosition(pos);
        Lista l = Lista.getLista(cursor);
        return l;
    }

    public long saveNota(Nota n) {
        long r;
        if (n.getId() == 0) {
            r = this.insertNota(n);
        } else {
            r = this.updateNota(n);
        }
        return r;
    }

    private long insertNota(Nota n) {
        if ((n.getTitulo().trim().compareTo("") == 0) && (n.getDescripcion().trim().compareTo("") == 0)) {
            if (n.getImagen().isEmpty()) {
                return 0;
            }
        }
        ContentValues cv = new ContentValues();
        cv.put(ContratoBaseDatos.TablaNota.TITULONOTA, n.getTitulo());
        cv.put(ContratoBaseDatos.TablaNota.DESCRIPCION, n.getDescripcion());
        cv.put(ContratoBaseDatos.TablaNota.IMAGEN, n.getImagen());
        cv.put(ContratoBaseDatos.TablaNota.BORRADO, n.getBorrado());
        return Long.parseLong(this.resolver.insert(uri, cv).getLastPathSegment());

    }

    private long updateNota(Nota n) {

        long r;
        ContentValues values = new ContentValues();
        values.put(ContratoBaseDatos.TablaNota.TITULONOTA, n.getTitulo());
        values.put(ContratoBaseDatos.TablaNota.DESCRIPCION, n.getDescripcion());
        values.put(ContratoBaseDatos.TablaNota.IMAGEN, n.getImagen());
        values.put(ContratoBaseDatos.TablaNota.BORRADO, n.getBorrado());
        String where = ContratoBaseDatos.TablaNota._ID + " = ?";
        String[] argumentos = {n.getId() + ""};

        r = this.resolver.update(ContentUris.withAppendedId(uri,n.getId()), values, where, argumentos);
        return r;
    }

    @Override
    public void vaciarPapelera() {
        String where = ContratoBaseDatos.TablaNota.BORRADO + " = ? ";
        String[] args = new String[]{"1"};
        resolver.delete(uri, where, args);
        cursor.moveToFirst();
        if(cursor.getCount()==1){
            where=ContratoBaseDatos.TablaItemLista.ID_FORANEO+" = ? ";
            args= new String[]{cursor.getString(0)};
            resolver.delete(uriItem,where,args);
            where = ContratoBaseDatos.TablaLista.BORRADO+" = ? ";
            args= new String[]{"1"};
            resolver.delete(uriListas,where,args);
        }else{
            while(cursor.moveToNext()){
                where=ContratoBaseDatos.TablaItemLista.ID_FORANEO+" = ? ";
                args= new String[]{cursor.getString(0)};
                resolver.delete(uriItem,where,args);
                where = ContratoBaseDatos.TablaLista.BORRADO+" = ? ";
                args= new String[]{"1"};
                resolver.delete(uriListas,where,args);
            }
        }
    }

    @Override
    public long deleteNota(int position) {
        cursor.moveToPosition(position);
        Nota n = Nota.getNota(cursor);
        return this.deleteNota(n);
    }

    @Override
    public long deleteNota(Nota n) {
        Uri nueva = Uri.withAppendedPath(uri, n.getId() + "");
        return this.resolver.delete(nueva, null, null);
    }

    @Override
    public long deleteLista(Lista l) {
        long i = deleteItemsLista(l.getId());
        if (i > 0) {
            this.resolver.delete(ContentUris.withAppendedId(uriItem, l.getId()), null, null);
        }

        return this.resolver.delete(ContentUris.withAppendedId(uriListas, l.getId()), null, null);
    }

    public long deleteItemsLista(long id) {
        Cursor c = this.resolver.query(ContentUris.withAppendedId(uriItem, id), null, null, null, null);
        return c.getCount();
    }

    @Override
    public long deleteLista(int position) {
        cursor.moveToFirst();
        cursor.moveToPosition(position);
        Lista n = Lista.getLista(cursor);
        return this.deleteLista(n);
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
        return Long.parseLong(this.resolver.insert(uriListas, cv).getLastPathSegment());
    }

    private long updateLista(Lista n) {
        ContentValues cv = new ContentValues();
        cv.put(ContratoBaseDatos.TablaLista.BORRADO, n.getBorrado());
        return this.resolver.update(ContentUris.withAppendedId(uriListas, n.getId()), cv, null, null);
    }



}