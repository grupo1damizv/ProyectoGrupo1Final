package com.izv.dam.newquip.pojo;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by dam on 02/12/2016.
 */

public class Ajustes implements Parcelable {

   private String fondoNota,fondoLista,fondoPapelera;


    public Ajustes(String fondoNota, String fondoLista, String fondoPapelera) {
        this.fondoNota = fondoNota;
        this.fondoLista = fondoLista;
        this.fondoPapelera = fondoPapelera;
    }

    public Ajustes(){

    }


    protected Ajustes(Parcel in) {
    }

    public static final Creator<Ajustes> CREATOR = new Creator<Ajustes>() {
        @Override
        public Ajustes createFromParcel(Parcel in) {
            return new Ajustes(in);
        }

        @Override
        public Ajustes[] newArray(int size) {
            return new Ajustes[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
    }

    public String getFondoNota() {
        return fondoNota;
    }

    public void setFondoNota(String fondoNota) {
        this.fondoNota = fondoNota;
    }

    public String getFondoLista() {
        return fondoLista;
    }

    public void setFondoLista(String fondoLista) {
        this.fondoLista = fondoLista;
    }

    public String getFondoPapelera() {
        return fondoPapelera;
    }

    public void setFondoPapelera(String fondoPapelera) {
        this.fondoPapelera = fondoPapelera;
    }
}
