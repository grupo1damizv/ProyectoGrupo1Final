package com.izv.dam.newquip.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.contrato.ContratoBaseDatos;

/**
 * Created by dam on 23/11/2016.
 */

public class AdaptadorLista extends RecyclerView.Adapter<AdaptadorLista.ListaViewHolder> {

    private Context context;
    private Cursor dataCursor;
    private static ClickItem click;


    public AdaptadorLista(Context context, Cursor cursor, ClickItem click) {
        this.context = context;
        this.dataCursor = cursor;
        this.click = click;
    }

    @Override
    public ListaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcard_lista, parent, false);
        ListaViewHolder lista = new ListaViewHolder(v);
        return lista;
    }

    @Override
    public void onBindViewHolder(ListaViewHolder holder, int position) {
        final Cursor cursor = getItem(position);
        holder.tvTitulo.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaLista.TITULO)));
        holder.item1.setText(cursor.getString(4));
    }

    public Cursor getItem(int position) {
        if (dataCursor != null) {
            dataCursor.moveToPosition(position);
            return dataCursor;
        } else {
            return null;
        }
    }

    public Cursor changeCursor(Cursor cursor) {
        if (dataCursor == cursor) {
            return null;
        }
        this.dataCursor = cursor;
        if (cursor != null) {
            this.notifyDataSetChanged();
        }
        return dataCursor;
    }

    @Override
    public int getItemCount() {
        if (dataCursor != null) {
            return dataCursor.getCount();
        }
        return 0;
    }

    static class ListaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        TextView tvTitulo, item1;

        public ListaViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tvTitulo = (TextView) itemView.findViewById(R.id.tvTituloListaCard);
            item1 = (TextView) itemView.findViewById(R.id.item1Card);
        }

        @Override
        public void onClick(View v) {
            int posicion = getAdapterPosition();
            click.onItemClickListener(posicion);
        }

        @Override
        public boolean onLongClick(View v) {
            int posicion = getAdapterPosition();
            click.onItemLongClickListener(posicion);
            return true;
        }

    }

}