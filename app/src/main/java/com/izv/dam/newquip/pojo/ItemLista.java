package com.izv.dam.newquip.pojo;

import android.content.ContentValues;
import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;

/**
 * Created by dam on 23/11/2016.
 */

public class ItemLista implements Parcelable {
    private long id, idForaneo;
    private int checked;
    private String texto;

    public ItemLista(long id, long idForaneo, int checked, String texto) {
        this.id = id;
        this.idForaneo = idForaneo;
        this.checked = checked;
        this.texto = texto;
    }

    public ItemLista() {

    }

    protected ItemLista(Parcel in) {
        id = in.readLong();
        idForaneo = in.readLong();
        checked = in.readInt();
        texto = in.readString();
    }

    public static final Creator<ItemLista> CREATOR = new Creator<ItemLista>() {
        @Override
        public ItemLista createFromParcel(Parcel in) {
            return new ItemLista(in);
        }

        @Override
        public ItemLista[] newArray(int size) {
            return new ItemLista[size];
        }
    };

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getIdForaneo() {
        return idForaneo;
    }

    public void setIdForaneo(long idForaneo) {
        this.idForaneo = idForaneo;
    }

    public int getChecked() {
        return checked;
    }

    public void setChecked(int checked) {
        this.checked = checked;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public ContentValues getContentValues() {
        return this.getContentValues(false);
    }

    public ContentValues getContentValues(boolean withId){
        ContentValues valores = new ContentValues();
        if(withId){
            valores.put(ContratoBaseDatos.TablaItemLista._ID, this.getId());
        }
        valores.put(ContratoBaseDatos.TablaItemLista.ID_FORANEO, this.getIdForaneo());
        valores.put(ContratoBaseDatos.TablaItemLista.TEXTO, this.getTexto());
        valores.put(ContratoBaseDatos.TablaItemLista.SELECCIONADO, this.getChecked());
        return valores;
    }

    public static ItemLista getItemLista(Cursor c){
        ItemLista objeto = new ItemLista();
        objeto.setId(c.getLong(c.getColumnIndex(ContratoBaseDatos.TablaItemLista._ID)));
        objeto.setIdForaneo(c.getLong(c.getColumnIndex(ContratoBaseDatos.TablaItemLista.ID_FORANEO)));
        objeto.setTexto(c.getString(c.getColumnIndex(ContratoBaseDatos.TablaItemLista.TEXTO)));
        objeto.setChecked(c.getInt(c.getColumnIndex(ContratoBaseDatos.TablaItemLista.SELECCIONADO)));
        return objeto;
    }

    @Override
    public String toString() {
        return "ItemLista{" +
                "id=" + id +
                ", idForaneo=" + idForaneo +
                ", checked=" + checked +
                ", texto='" + texto + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(id);
        dest.writeLong(idForaneo);
        dest.writeInt(checked);
        dest.writeString(texto);
    }
}