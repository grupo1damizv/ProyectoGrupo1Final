package com.izv.dam.newquip.adaptadores;

import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.pojo.ItemLista;

import java.util.ArrayList;

/**
 * Created by dam on 23/11/2016.
 */

public class AdaptadorItemLista extends RecyclerView.Adapter<AdaptadorItemLista.ItemListaViewHolder> {

    public ArrayList<ItemLista> datos;

    public AdaptadorItemLista(ArrayList<ItemLista> datos) {
        this.datos = datos;
    }

    public void insert() {
        ItemLista il = new ItemLista(0, 0, 0, "");
        datos.add(il);
        notifyItemInserted(datos.size());
    }

    @Override
    public ItemListaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lista_item, parent, false);
        ItemListaViewHolder listaViewHolder = new ItemListaViewHolder(itemView);
        return listaViewHolder;
    }

    @Override
    public void onBindViewHolder(ItemListaViewHolder holder, int position) {
        ItemLista itemLista = datos.get(position);
        holder.bindItem(itemLista);
    }

    @Override
    public int getItemCount() {
        return datos.size();
    }

    public class ItemListaViewHolder extends RecyclerView.ViewHolder {
        private EditText itemListaEditText;
        private CheckBox itemListaCheckBox;
        private ImageButton itemListaBorrarBoton;


        public ItemListaViewHolder(View itemView) {
            super(itemView);
            itemListaCheckBox = (CheckBox) itemView.findViewById(R.id.ItemListaCheckBox);
            itemListaEditText = (EditText) itemView.findViewById(R.id.itemListaEditText);
            itemListaBorrarBoton = (ImageButton) itemView.findViewById(R.id.itemListaBorrarBoton);
            itemListaBorrarBoton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if ( position != -1 ) {
                        datos.remove(position);
                        notifyItemRemoved(getAdapterPosition());

                    }
                }
            });
            itemListaEditText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    datos.get(getAdapterPosition()).setTexto(itemListaEditText.getText().toString());
                }
            });
            itemListaCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        datos.get(getAdapterPosition()).setChecked(1);
                    } else {
                        datos.get(getAdapterPosition()).setChecked(0);
                    }
                }
            });
        }

        public void bindItem(ItemLista item) {
            itemListaEditText.setHint("");
            itemListaEditText.setText(item.getTexto());
            if (item.getChecked() == 0) {
                itemListaCheckBox.setChecked(false);
            } else {
                itemListaCheckBox.setChecked(true);
            }
        }
    }
}