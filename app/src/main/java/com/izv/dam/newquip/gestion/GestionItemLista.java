package com.izv.dam.newquip.gestion;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.pojo.ItemLista;

/**
 * Created by dam on 23/11/2016.
 */

public class GestionItemLista extends Gestion<ItemLista>{

public GestionItemLista(Context c) {
        super(c);
        }

public GestionItemLista(Context c, boolean write) {
        super(c, write);
        }

@Override
public int deleteAll() {
        return this.deleteAll(ContratoBaseDatos.TablaLista.TABLA);
        }

public int delete(String condicion, String[] argumentos) {
        return this.delete(ContratoBaseDatos.TablaItemLista.TABLA, condicion, argumentos);
        }

public void deleteItems(long id){
        String sql = "delete from item where foraneo = " + id;
        this.getBasedatos().execSQL(sql);
        }

@Override
public int delete(ItemLista objeto) {
        String condicion = ContratoBaseDatos.TablaItemLista._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        return this.delete(ContratoBaseDatos.TablaItemLista.TABLA, condicion, argumentos);
        }

public ItemLista get(long id) {
        String where = ContratoBaseDatos.TablaItemLista.ID_FORANEO + " = ? ";
        String[] parametros = {id + ""};
        Cursor c = this.getCursor(ContratoBaseDatos.TablaItemLista.PROJECTION_ALL, where, parametros, null, null, ContratoBaseDatos.TablaItemLista.SORT_ORDER_DEFAULT);
        if(c.getCount() > 0) {
        c.moveToFirst();
        ItemLista item = ItemLista.getItemLista(c);
        return item;
        }
        return null;
        }

@Override
public Cursor getCursor() {
        return this.getCursor(ContratoBaseDatos.TablaItemLista.TABLA, ContratoBaseDatos.TablaItemLista.PROJECTION_ALL, ContratoBaseDatos.TablaItemLista.SORT_ORDER_DEFAULT);
        }

public Cursor getCursorItems(long id){
        String sql = "select * from item where foraneo = " + id;
        Cursor c = this.getBasedatos().rawQuery(sql, null);
        return c;
        }

@Override
public Cursor getCursor(String[] columns, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        return this.getCursor(ContratoBaseDatos.TablaItemLista.TABLA, columns, selection, selectionArgs, groupBy, having, orderBy);
        }

@Override
public long insert(ContentValues objeto) {
        return this.insert(ContratoBaseDatos.TablaItemLista.TABLA, objeto);
        }

@Override
public long insert(ItemLista objeto) {
        return this.insert(ContratoBaseDatos.TablaItemLista.TABLA, objeto.getContentValues());
        }

@Override
public int update(ContentValues valores, String condicion, String[] argumentos){
        return this.update(ContratoBaseDatos.TablaItemLista.TABLA, valores, condicion, argumentos);
        }

@Override
public int update(ItemLista objeto) {
        ContentValues valores = objeto.getContentValues();
        String condicion = ContratoBaseDatos.TablaItemLista._ID + " = ?";
        String[] argumentos = { objeto.getId() + "" };
        return this.update(ContratoBaseDatos.TablaItemLista.TABLA, valores, condicion, argumentos);
        }
}
