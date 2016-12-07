package com.izv.dam.newquip.pojo;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;

import java.util.ArrayList;

/**
 * Created by dam on 23/11/2016.
 */

public class Lista implements Parcelable {

    private long id;
    private String titulo;
    private long borrado;
    private ArrayList<ItemLista> datos;

    public Lista(long id, String titulo, long borrado) {

        this.id = id;
        this.titulo = titulo;
        this.borrado = 0;
    }

    public Lista() {

        this(0,"",0);
    }


    protected Lista(Parcel in) {
        id = in.readLong();
        titulo = in.readString();
        borrado = in.readLong();
        datos = in.createTypedArrayList(ItemLista.CREATOR);
    }

    public static final Creator<Lista> CREATOR = new Creator<Lista>() {
        @Override
        public Lista createFromParcel(Parcel in) {
            return new Lista(in);
        }

        @Override
        public Lista[] newArray(int size) {
            return new Lista[size];
        }
    };

    public ContentValues getContentValues(){
        return this.getContentValues(false);
    }

    public ContentValues getContentValues(boolean withId){
        ContentValues valores = new ContentValues();
        if(withId){
            valores.put(ContratoBaseDatos.TablaLista._ID, this.getId());
        }
        valores.put(ContratoBaseDatos.TablaLista.TITULO, this.getTitulo());
        return valores;
    }

    public static Lista getLista(Cursor c){
        Lista objeto = new Lista();
        objeto.setId(c.getLong(c.getColumnIndex(ContratoBaseDatos.TablaLista._ID)));
        objeto.setTitulo(c.getString(c.getColumnIndex(ContratoBaseDatos.TablaLista.TITULO)));
        return objeto;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public long getBorrado() {
        return borrado;
    }

    public void setBorrado(long borrado) {
        this.borrado = borrado;
    }

    public ArrayList<ItemLista> getDatos() {
        return datos;
    }

    public void setDatos(ArrayList<ItemLista> datos) {
        this.datos = datos;
    }

    @Override
    public String toString() {
        return "Lista{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", borrado=" + borrado +
                ", datos=" + datos +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeString(titulo);
        dest.writeLong(borrado);
        dest.writeTypedList(datos);
    }
}
