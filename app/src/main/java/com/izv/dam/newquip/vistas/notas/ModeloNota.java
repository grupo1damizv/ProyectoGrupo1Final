package com.izv.dam.newquip.vistas.notas;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.contrato.ContratoNota;
import com.izv.dam.newquip.gestion.GestionNota;
import com.izv.dam.newquip.pojo.Nota;

public class ModeloNota implements ContratoNota.InterfaceModelo {

    private ContentResolver resolver;
    Uri uri = Uri.withAppendedPath(ContratoBaseDatos.TablaNota.CONTENT_URI, ContratoBaseDatos.TablaNota.TABLA);

    public ModeloNota(Context c) {
        this.resolver = c.getContentResolver();
    }

    @Override
    public void close() {

    }

    @Override
    public Nota getNota(long id) {
        String where = ContratoBaseDatos.TablaNota._ID + " = ?";
        String[] argumentos = {id + ""};
        Cursor c = this.resolver.query(uri, null, where, argumentos, null);
        if (c.getCount() > 0) {
            c.moveToFirst();
            Nota nota = Nota.getNota(c);
            return nota;
        }
        return null;

    }

    @Override
    public long saveNota(Nota n) {
        long r;
        if (n.getId() == 0) {
            r = this.insertNota(n);
        } else {
            r = this.updateNota(n);
        }
        return r;
    }

    private long deleteNota(Nota n) {
        String where = ContratoBaseDatos.TablaNota._ID + " = ?";
        String[] argumentos = {n.getId() + ""};
        return this.resolver.delete(uri, where, argumentos);
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
        if (n.getDescripcion().trim().compareTo("") == 0 && n.getTitulo().trim().compareTo("") == 0) {
            this.deleteNota(n);
            return 0;
        }
        r = this.resolver.update(uri, values, where, argumentos);
        return r;
    }
}