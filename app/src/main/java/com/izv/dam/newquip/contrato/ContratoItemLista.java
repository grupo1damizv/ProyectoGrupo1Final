package com.izv.dam.newquip.contrato;

import com.izv.dam.newquip.pojo.ItemLista;
import com.izv.dam.newquip.pojo.Lista;

/**
 * Created by dam on 23/11/2016.
 */

public interface ContratoItemLista {

    interface InterfaceModelo {

        void close();

        long saveLista(Lista n);

        long saveItemLista(ItemLista item);

    }

    interface InterfacePresentador {

        void onPause();

        void onResume();

        long onSaveLista(Lista n);

        long saveItemLista (ItemLista item);

    }

    interface InterfaceVista {

        void mostrarLista(Lista n);

    }
}
