package com.izv.dam.newquip.vistas.fragmentos.notas;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.contrato.ContratoMain;
import com.izv.dam.newquip.pojo.Nota;

/**
 * Created by javi_ on 07/11/2016.
 */

public class FragmentoModeloNota implements ContratoMain.InterfaceModelo {

    private Cursor cursor;
    private ContentResolver resolver;
    Uri uri = Uri.withAppendedPath(ContratoBaseDatos.TablaNota.CONTENT_URI, ContratoBaseDatos.TablaNota.TABLA);


    public FragmentoModeloNota(Context c) {
        this.resolver = c.getContentResolver();
    }

    @Override
    public void close() {

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
    public Nota getNota(int position) {
        cursor.moveToPosition(position);
        Nota n = Nota.getNota(cursor);
        return n;
    }

    @Override
    public void loadData(OnDataLoadListener listener) {
        cursor = this.resolver.query(uri, null, null, null, null);
        listener.setCursor(cursor);
    }

    public void changeCursor(Cursor c) {
        this.cursor = c;
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
        if (n.getDescripcion().trim().compareTo("") == 0 && n.getTitulo().trim().compareTo("") == 0 && n.getImagen().trim().compareTo("")==0) {
            this.deleteNota(n);
            return 0;
        }
        r = this.resolver.update(uri, values, where, argumentos);
        return r;
    }

}