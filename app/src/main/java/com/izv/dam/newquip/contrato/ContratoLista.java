package com.izv.dam.newquip.contrato;

import android.database.Cursor;

import com.izv.dam.newquip.pojo.Lista;

/**
 * Created by dam on 23/11/2016.
 */

public interface ContratoLista {
    interface InterfaceModelo {

        void close();

        long deleteLista(int position);

        long deleteLista(Lista n);

        Lista getLista(int position);

        void loadData(ContratoLista.InterfaceModelo.OnDataLoadListener listener);

        interface OnDataLoadListener {
            public void setCursor(Cursor c);
        }

        void changeCursor(Cursor c);
        long saveLista(Lista n);
    }

    interface InterfacePresentador {

        void onAddLista();

        void onDeleteLista(int position);

        void onDeleteLista(Lista n);

        void onEditLista(int position);

        void onEditLista(Lista n);

        void onPause();

        void onResume();

        void onShowBorrarLista(int position);

        void changeCursor(Cursor c);
        long onSaveLista(Lista n);

    }

    interface InterfaceVista {

        void mostrarAgregarLista();

        void mostrarLista(Cursor c);

        void mostrarEditarLista(Lista n);

        void mostrarConfirmarBorrarLista(Lista n);

        void mostrarDatos(Cursor c);
    }
}
