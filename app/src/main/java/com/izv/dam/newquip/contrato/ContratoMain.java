package com.izv.dam.newquip.contrato;

import android.database.Cursor;

import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.pojo.Nota;

public interface ContratoMain {

    interface InterfaceModelo {

        void close();

        long deleteNota(int position);

        long deleteNota(Nota n);

        Nota getNota(int position);

        void loadData(OnDataLoadListener listener);

        interface OnDataLoadListener {
            public void setCursor(Cursor c);
        }
        void changeCursor(Cursor c);
        long saveNota(Nota n);

    }
    interface InterfaceModeloBorrado{

        void close();

        void loadData(InterfaceModelo.OnDataLoadListener listener);

        interface OnDataLoadListener {
            public void setCursor(Cursor c);
        }
        void changeCursor(Cursor c);
        void vaciarPapelera();
        int getTipo(int pos);
        Nota getNota(int pos);
        Lista getLista(int pos);
        long deleteNota(int position);
        long deleteNota(Nota n);
        long deleteLista(Lista l);
        long deleteLista(int position);
        long saveNota(Nota n);
        long saveLista(Lista l);
    }

    interface InterfacePresentador {

        void onAddNota();

        void onDeleteNota(int position);

        void onDeleteNota(Nota n);

        void onEditNota(int position);

        void onEditNota(Nota n);

        void onPause();

        void onResume();

        void onShowBorrarNota(int position,int i);

        void changeCursor(Cursor c);
        long onSaveNota(Nota n);

    }
    interface InterfacePresentadorBorrado {

        void onPause();

        void onResume();

        void onShowBorrarObjeto(int position);
        void onShowVaciarPapelera(int i);
        void changeCursor(Cursor c);
        void onDeleteNota(Nota n);
        void onDeleteLista(Lista l);
        long onSaveNota(Nota n);
        long onSaveLista(Lista l);


    }

    interface InterfaceVista {

       void mostrarAgregarNota();

        void mostrarDatos(Cursor c);

       void mostrarEditarNota(Nota n);

        void mostrarConfirmarBorrarNota(Nota n);
        void mostrarConfirmarBorrarLista(Lista n);

        void mostrarConfirmarVaciarPapelera(Object o);

    }

}