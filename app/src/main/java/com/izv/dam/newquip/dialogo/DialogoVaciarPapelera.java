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
 * Created by JAVIER on 29/11/2016.
 */

public class DialogoVaciarPapelera extends DialogFragment {
    private static Object ob;
    private static int tipo;
    // Interfaz de comunicación
    OnBorrarDialogListener.OnBorrarDialogListenerVaciar listener;

    public DialogoVaciarPapelera() {
    }

    public static DialogoVaciarPapelera newInstance(Object o) {
        DialogoVaciarPapelera fragment = new DialogoVaciarPapelera();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            if(tipo==1){
                ob=getArguments().getParcelable("nota");
            }else{
                ob=getArguments().getParcelable("lista");
            }
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogBorrar();
    }
    public AlertDialog createDialogBorrar() {
        AlertDialog alertBorrar=null;

            String titulo_dialogo= String.format("%s", getString(R.string.etiqueta_vaciar));
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setTitle(titulo_dialogo);
            builder.setMessage(R.string.mensaje_borra_todo);
            builder.setPositiveButton(R.string.borrar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    listener.onBorrarPossitiveButtonClick();
                }
            });
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    listener.onBorrarNegativeButtonClick();
                }
            });
            alertBorrar = builder.create();


        return alertBorrar;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OnBorrarDialogListener.OnBorrarDialogListenerVaciar) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnBorrarDialogListener");

        }
    }

}