package com.izv.dam.newquip.dialogo;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.pojo.Nota;

/**
 * Created by dam on 30/11/2016.
 */

public class DialogoRecuperarNota extends DialogFragment {
    private static Nota nota;
    // Interfaz de comunicación
    OnBorrarDialogListener listener;

    public DialogoRecuperarNota() {
    }

    public static DialogoRecuperarNota newInstance(Nota n) {

        nota=n;
        DialogoRecuperarNota fragment = new DialogoRecuperarNota();
        Bundle args = new Bundle();
        args.putParcelable("nota",n);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            nota=getArguments().getParcelable("nota");
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return createDialogBorrar();
    }
    public AlertDialog createDialogBorrar() {
        AlertDialog alertBorrar=null;

        String titulo_dialogo= String.format("%s %s", getString(R.string.etiqueta_dialogo),nota.getTitulo());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(titulo_dialogo);
        builder.setMessage(R.string.mensaje_recuperar_nota);
        builder.setPositiveButton(R.string.borrar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onBorrarPossitiveButtonClick(nota);
            }
        });
        builder.setNegativeButton(R.string.recuperar, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                listener.onBorrarNegativeButtonClick(nota);
            }
        });
        alertBorrar = builder.create();




        return alertBorrar;
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (OnBorrarDialogListener) context;

        } catch (ClassCastException e) {
            throw new ClassCastException(
                    context.toString() +
                            " no implementó OnBorrarDialogListener");

        }
    }

}
