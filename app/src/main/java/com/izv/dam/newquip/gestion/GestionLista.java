package com.izv.dam.newquip.gestion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.pojo.Lista;

/**
 * Created by dam on 19/10/16.
 */

public class GestionLista extends Gestion<Lista>{

    public GestionLista(Context c) {
        super(c);
    }

    public GestionLista(Context c, boolean write) {
        super(c, write);
    }

    @Override
    public int deleteAll() {
        return this.deleteAll(ContratoBaseDatos.TablaLista.TABLA);
    }

    public int delete(String condicion, String[] argumentos) {
        return this.delete(ContratoBaseDatos.TablaLista.TABLA, condicion, argumentos);
    }

    @Override
    public int delete(Lista objeto) {
        String condicion = ContratoBaseDatos.TablaLista._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        return this.delete(ContratoBaseDatos.TablaLista.TABLA, condicion, argumentos);
    }

    public Lista get(long id) {
        String where = ContratoBaseDatos.TablaLista._ID + " = ? ";
        String[] parametros = {id + ""};
        Cursor c = this.getCursor(ContratoBaseDatos.TablaLista.PROJECTION_ALL, where, parametros, null, null, ContratoBaseDatos.TablaLista.SORT_ORDER_DEFAULT);
        if(c.getCount() > 0) {
            c.moveToFirst();
            Lista lista = Lista.getLista(c);
            return lista;
        }
        return null;
    }

    @Override
    public Cursor getCursor() {
        return this.getCursor(ContratoBaseDatos.TablaLista.TABLA, ContratoBaseDatos.TablaLista.PROJECTION_ALL, ContratoBaseDatos.TablaLista.SORT_ORDER_DEFAULT);
    }

    @Override
    public Cursor getCursor(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return this.getCursor(ContratoBaseDatos.TablaLista.TABLA, columns, selection, selectionArgs, groupBy, having, orderBy);
    }

    public Cursor getCursorJoin(){
        String sql = "SELECT l._id, l.titulo, l.borrado, i.foraneo, min(i.texto), i.seleccionado FROM lista l left join item i on l._id = i.foraneo where l.borrado=0 group by i.foraneo";
        Cursor c = this.getBasedatos().rawQuery(sql, null);
        return c;
    }

    public Cursor getCursorJoinBorrados(){
        String sql = "SELECT *,1 as tipo FROM nota where borrado=1 union select *,null,null,2 as tipo from lista where borrado=1";
        Cursor c = this.getBasedatos().rawQuery(sql, null);
        return c;
    }

    @Override
    public long insert(ContentValues objeto) {
        return this.insert(ContratoBaseDatos.TablaLista.TABLA, objeto);
    }

    @Override
    public long insert(Lista objeto) {
        return this.insert(ContratoBaseDatos.TablaLista.TABLA, objeto.getContentValues());
    }

    @Override
    public int update(ContentValues valores, String condicion, String[] argumentos){
        return this.update(ContratoBaseDatos.TablaLista.TABLA, valores, condicion, argumentos);
    }

    @Override
    public int update(Lista objeto) {
        ContentValues valores = objeto.getContentValues();
        String condicion = ContratoBaseDatos.TablaLista._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        return this.update(ContratoBaseDatos.TablaLista.TABLA, valores, condicion, argumentos);
    }
}
