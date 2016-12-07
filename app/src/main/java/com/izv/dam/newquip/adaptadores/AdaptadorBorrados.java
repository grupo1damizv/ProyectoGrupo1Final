package com.izv.dam.newquip.adaptadores;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.contrato.ContratoBaseDatos;


public class AdaptadorBorrados extends RecyclerView.Adapter<AdaptadorBorrados.NotaViewHolder> {

    private Context context;
    private Cursor dataCursor;
    private static ClickItem click;


    public AdaptadorBorrados(Context context, Cursor cursor, ClickItem click) {
        this.context=context;
        this.dataCursor = cursor;
        this.click = click;
    }

    @Override
    public NotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcard_borrado, parent, false);
        NotaViewHolder item = new NotaViewHolder(v);
        return item;
    }

    @Override
    public void onBindViewHolder(NotaViewHolder holder, int position) {
        final Cursor cursor = getItem(position);
        if((cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.TITULONOTA)).trim().compareTo("")==0)
                && (cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.DESCRIPCION)).trim().compareTo("")==0)){
            System.out.println("entro en solo imagen");
            holder.tvTituloBorrado.setText("Imagen");
        }else if(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.TITULONOTA)).trim().compareTo("")==0){
            holder.tvTituloBorrado.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.DESCRIPCION)));
        }else{
            holder.tvTituloBorrado.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.TITULONOTA)));
        }

        if(cursor.getInt(cursor.getColumnIndex("tipo"))==1){
            holder.ivTipo.setImageResource(R.drawable.ic_action_crear_black);
        }else{
            holder.ivTipo.setImageResource(R.drawable.ic_action_crear_lista_black);
        }
    }

    public Cursor getItem(int position) {
        if (dataCursor != null) {
            dataCursor.moveToPosition(position);
            return dataCursor;
        } else {
            return null;
        }
    }

    public Cursor changeCursor(Cursor cursor){

        if(dataCursor == cursor){
            return null;
        }
        this.dataCursor = cursor;
        if(cursor != null){
            this.notifyDataSetChanged();
        }
        return dataCursor;
    }

    @Override
    public int getItemCount() {
        if(dataCursor != null){
            return dataCursor.getCount();
        }
        return 0;
    }

    static class NotaViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener{

        TextView tvTituloBorrado;
        ImageView ivTipo;

        public NotaViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tvTituloBorrado = (TextView) itemView.findViewById(R.id.tvTituloBorrado);
            ivTipo = (ImageView) itemView.findViewById(R.id.imagenTipo);
        }

        @Override
        public void onClick(View v) {

        }

        @Override
        public boolean onLongClick(View v) {
            int posicion = getAdapterPosition();
            click.onItemLongClickListener(posicion);
            return true;
        }
    }


}