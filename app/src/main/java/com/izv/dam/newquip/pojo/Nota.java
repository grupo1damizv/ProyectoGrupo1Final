package com.izv.dam.newquip.pojo;

import android.content.ContentValues;
import android.database.Cursor;
import android.databinding.BaseObservable;
import android.os.Parcel;
import android.os.Parcelable;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;



public class Nota extends BaseObservable implements Parcelable {

    private long id;
    private String titulo, descripcion, imagen;
    private int borrado;


    public Nota(long id, String titulo, String descripcion, String imagen) {
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
        this.borrado = 0;
    }

    public Nota() {

        this(0,"","","");
    }

    protected Nota(Parcel in) {
        id = in.readLong();
        titulo = in.readString();
        descripcion = in.readString();
        imagen = in.readString();
        borrado = in.readInt();
    }

    public static final Creator<Nota> CREATOR = new Creator<Nota>() {
        @Override
        public Nota createFromParcel(Parcel in) {
            return new Nota(in);
        }

        @Override
        public Nota[] newArray(int size) {
            return new Nota[size];
        }
    };

    public ContentValues getContentValues(){
        return this.getContentValues(false);
    }

    public ContentValues getContentValues(boolean withId){
        ContentValues valores = new ContentValues();
        if(withId){
            valores.put(ContratoBaseDatos.TablaNota._ID, this.getId());
        }
        valores.put(ContratoBaseDatos.TablaNota.TITULONOTA, this.getTitulo());
        valores.put(ContratoBaseDatos.TablaNota.DESCRIPCION, this.getDescripcion());
        valores.put(ContratoBaseDatos.TablaNota.IMAGEN, this.getImagen());
        valores.put(ContratoBaseDatos.TablaNota.BORRADO, this.getBorrado());
        return valores;
    }

    public static Nota getNota(Cursor c){
        Nota objeto = new Nota();
        objeto.setId(c.getLong(c.getColumnIndex(ContratoBaseDatos.TablaNota._ID)));
        objeto.setTitulo(c.getString(c.getColumnIndex(ContratoBaseDatos.TablaNota.TITULONOTA)));
        objeto.setDescripcion(c.getString(c.getColumnIndex(ContratoBaseDatos.TablaNota.DESCRIPCION)));
        objeto.setImagen(c.getString(c.getColumnIndex(ContratoBaseDatos.TablaNota.IMAGEN)));
        objeto.setBorrado(c.getInt(c.getColumnIndex(ContratoBaseDatos.TablaNota.BORRADO)));
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

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {

        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {

        this.imagen = imagen;
    }

    public int getBorrado() {
        return borrado;
    }

    public void setBorrado(int borrado) {

        this.borrado = borrado;
    }

    @Override
    public String toString() {
        return "Nota{" +
                "id=" + id +
                ", titulo='" + titulo + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", imagen=" + imagen +
                ", borrado=" + borrado +
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
        dest.writeString(descripcion);
        dest.writeString(imagen);
        dest.writeInt(borrado);
    }
}