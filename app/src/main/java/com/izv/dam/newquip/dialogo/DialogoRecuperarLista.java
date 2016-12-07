package com.izv.dam.newquip.dialogo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.pojo.Nota;

/**
 * Created by dam on 30/11/2016.
 */

public class DialogoRecuperarLista extends DialogFragment {
    private static Lista lista;
    // Interfaz de comunicación
    OnBorrarDialogListener.OnBorrarDialogListenerLista listener;

    public DialogoRecuperarLista() {
    }

    public static DialogoRecuperarLista newInstance(Lista l) {

        lista=l;
        DialogoRecuperarLista fragment = new DialogoRecuperarLista();
        Bundle args = new Bundle();
        args.putParcelable("lista",l);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            lista=getArguments().getParcelable("lista");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogBorrar();
    }
    public AlertDialog createDialogBorrar() {
        AlertDialog alertBorrar=null;

        String titulo_dialogo= String.format("%s %s", getString(R.string.etiqueta_dialogo_lista),lista.getTitulo());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(titulo_dialogo);
        builder.setMessage(R.string.mensaje_recuperar_lista);
        builder.setPositiveButton(R.string.borrar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onBorrarPossitiveButtonClick(lista);
            }
        });
        builder.setNegativeButton(R.string.recuperar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onBorrarNegativeButtonClick(lista);
            }
        });
        alertBorrar = builder.create();




        return alertBorrar;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OnBorrarDialogListener.OnBorrarDialogListenerLista) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnBorrarDialogListener");

        }
    }

}
