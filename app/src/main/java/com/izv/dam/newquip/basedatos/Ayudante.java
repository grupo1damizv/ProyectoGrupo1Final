package com.izv.dam.newquip.basedatos;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.pojo.Nota;

import java.util.ArrayList;
import java.util.List;

public class Ayudante extends SQLiteOpenHelper {

    //sqlite
    //tipos de datos https://www.sqlite.org/datatype3.html
    //fechas https://www.sqlite.org/lang_datefunc.html
    //trigger https://www.sqlite.org/lang_createtrigger.html

    private static final int VERSION = 4;

    public Ayudante(Context context) {
        super(context, ContratoBaseDatos.BASEDATOS, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        crearTablas(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        //Crear tablas temporales
        crearTemporales(db);
        //Llevar datos a las tablas temporales
        String consulta = "select * from "+ContratoBaseDatos.TablaNota.TABLA;
        Cursor c=db.rawQuery(consulta,null);
        String insertar;
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
               insertar= "insert into Temporal"+ContratoBaseDatos.TablaNota.TABLA+" values("+c.getString(0)+",'"+c.getString(1)+"','"+c.getString(2)+"','"
                       +c.getString(3)+"',"+c.getString(4)+");";
               db.execSQL(insertar);
            } while(c.moveToNext());
        }

        consulta = "select * from "+ContratoBaseDatos.TablaLista.TABLA;
        c=db.rawQuery(consulta,null);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                insertar= "insert into Temporal"+ContratoBaseDatos.TablaLista.TABLA+" values("+c.getString(0)+",'"+c.getString(1)+"',"+c.getString(2)+");";
                db.execSQL(insertar);
            } while(c.moveToNext());
        }

        consulta = "select * from "+ContratoBaseDatos.TablaItemLista.TABLA;
        c=db.rawQuery(consulta,null);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                insertar= "insert into Temporal"+ContratoBaseDatos.TablaItemLista.TABLA+" values("+c.getString(0)+","+c.getString(1)+",'"+c.getString(2)+"',"+
                        c.getString(3)+");";
                db.execSQL(insertar);
            } while(c.moveToNext());
        }
        //Borrar tablas viejas
        consulta="drop table if exists " + ContratoBaseDatos.TablaNota.TABLA;
        db.execSQL(consulta);
        Log.v("sql",consulta);

        consulta="drop table if exists " + ContratoBaseDatos.TablaLista.TABLA;
        db.execSQL(consulta);
        Log.v("sql",consulta);

        consulta="drop table if exists " + ContratoBaseDatos.TablaItemLista.TABLA;
        db.execSQL(consulta);
        Log.v("sql",consulta);

        //Crear tablas nuevas
        crearTablas(db);
        //Llevar datos de tablas temporales a las tablas nuevas
        consulta = "select * from Temporal"+ContratoBaseDatos.TablaNota.TABLA;
        c=db.rawQuery(consulta,null);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                insertar= "insert into "+ContratoBaseDatos.TablaNota.TABLA+" values("+c.getString(0)+",'"+c.getString(1)+"','"+c.getString(2)+"','"
                        +c.getString(3)+"',"+c.getString(4)+");";
                db.execSQL(insertar);
            } while(c.moveToNext());
        }

        consulta = "select * from Temporal"+ContratoBaseDatos.TablaLista.TABLA;
        c=db.rawQuery(consulta,null);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                insertar= "insert into "+ContratoBaseDatos.TablaLista.TABLA+" values("+c.getString(0)+",'"+c.getString(1)+"',"+c.getString(2)+");";
                db.execSQL(insertar);
            } while(c.moveToNext());
        }

        consulta = "select * from Temporal"+ContratoBaseDatos.TablaItemLista.TABLA;
        c=db.rawQuery(consulta,null);
        if (c.moveToFirst()) {
            //Recorremos el cursor hasta que no haya más registros
            do {
                insertar= "insert into "+ContratoBaseDatos.TablaItemLista.TABLA+" values("+c.getString(0)+","+c.getString(1)+",'"+c.getString(2)+"',"+
                        c.getString(3)+");";
                db.execSQL(insertar);
            } while(c.moveToNext());
        }

        //Borrar las tablas temporales
        consulta="drop table if exists Temporal" + ContratoBaseDatos.TablaNota.TABLA;
        db.execSQL(consulta);
        Log.v("sql",consulta);

        consulta="drop table if exists Temporal" + ContratoBaseDatos.TablaLista.TABLA;
        db.execSQL(consulta);
        Log.v("sql",consulta);

        consulta="drop table if exists Temporal" + ContratoBaseDatos.TablaItemLista.TABLA;
        db.execSQL(consulta);
        Log.v("sql",consulta);

    }

    public void crearTemporales(SQLiteDatabase db){
        String sqlNota;
        sqlNota="create table if not exists Temporal" + ContratoBaseDatos.TablaNota.TABLA +
                " (" +
                ContratoBaseDatos.TablaNota._ID + " integer primary key autoincrement , " +
                ContratoBaseDatos.TablaNota.TITULONOTA + " text(150), " +
                ContratoBaseDatos.TablaNota.DESCRIPCION + " text (5000), " +
                ContratoBaseDatos.TablaNota.IMAGEN + " text(128), " +
                ContratoBaseDatos.TablaNota.BORRADO + " integer " +
                ")";
        Log.v("sql",sqlNota);
        db.execSQL(sqlNota);

        String sqlLista;
        sqlLista="create table if not exists Temporal" + ContratoBaseDatos.TablaLista.TABLA +
                " (" +
                ContratoBaseDatos.TablaLista._ID + " integer primary key autoincrement , " +
                ContratoBaseDatos.TablaLista.TITULO + " text (150), " +
                ContratoBaseDatos.TablaLista.BORRADO + " integer " +
                ")";
        Log.v("sql",sqlLista);
        db.execSQL(sqlLista);

        String sqlItem;
        sqlItem="create table if not exists Temporal" + ContratoBaseDatos.TablaItemLista.TABLA +
                " (" +
                ContratoBaseDatos.TablaItemLista._ID + " integer primary key autoincrement , " +
                ContratoBaseDatos.TablaItemLista.ID_FORANEO + " integer , " +
                ContratoBaseDatos.TablaItemLista.TEXTO + " text (150), " +
                ContratoBaseDatos.TablaItemLista.SELECCIONADO + " integer, " +
                "foreign key (" + ContratoBaseDatos.TablaItemLista.ID_FORANEO + ") references " + ContratoBaseDatos.TablaLista.TABLA + "(" + ContratoBaseDatos.TablaLista._ID +")"+
                ")";
        Log.v("sql",sqlItem);
        db.execSQL(sqlItem);

    }

    public void crearTablas(SQLiteDatabase db){
        String sqlNota;
        sqlNota="create table if not exists " + ContratoBaseDatos.TablaNota.TABLA +
                " (" +
                ContratoBaseDatos.TablaNota._ID + " integer primary key autoincrement , " +
                ContratoBaseDatos.TablaNota.TITULONOTA + " text(150), " +
                ContratoBaseDatos.TablaNota.DESCRIPCION + " text (5000), " +
                ContratoBaseDatos.TablaNota.IMAGEN + " text(128), " +
                ContratoBaseDatos.TablaNota.BORRADO + " integer " +
                ")";
        Log.v("sql",sqlNota);
        db.execSQL(sqlNota);

        String sqlLista;
        sqlLista="create table if not exists " + ContratoBaseDatos.TablaLista.TABLA +
                " (" +
                ContratoBaseDatos.TablaLista._ID + " integer primary key autoincrement , " +
                ContratoBaseDatos.TablaLista.TITULO + " text (150), " +
                ContratoBaseDatos.TablaLista.BORRADO + " integer " +
                ")";
        Log.v("sql",sqlLista);
        db.execSQL(sqlLista);

        String sqlItem;
        sqlItem="create table if not exists " + ContratoBaseDatos.TablaItemLista.TABLA +
                " (" +
                ContratoBaseDatos.TablaItemLista._ID + " integer primary key autoincrement , " +
                ContratoBaseDatos.TablaItemLista.ID_FORANEO + " integer , " +
                ContratoBaseDatos.TablaItemLista.TEXTO + " text (150), " +
                ContratoBaseDatos.TablaItemLista.SELECCIONADO + " integer, " +
                "foreign key (" + ContratoBaseDatos.TablaItemLista.ID_FORANEO + ") references " + ContratoBaseDatos.TablaLista.TABLA + "(" + ContratoBaseDatos.TablaLista._ID +")"+
                ")";
        Log.v("sql",sqlItem);
        db.execSQL(sqlItem);
    }
}