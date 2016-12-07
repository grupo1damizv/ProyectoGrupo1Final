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


public class AdaptadorNota extends RecyclerView.Adapter<AdaptadorNota.NotaViewHolder> {

    private Context context;
    private Cursor dataCursor;
    private static ClickItem click;


    public AdaptadorNota(Context context,Cursor cursor, ClickItem click) {
        this.context=context;
        this.dataCursor = cursor;
        this.click = click;
    }

    @Override
    public NotaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.itemcard_nota, parent, false);
        NotaViewHolder nota = new NotaViewHolder(v);
        return nota;
    }

    @Override
    public void onBindViewHolder(NotaViewHolder holder, int position) {

        final Cursor cursor = getItem(position);
        if(cursor==null){

        }else{
            holder.iView.setImageResource(0);
            if(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.TITULONOTA)).trim().compareTo("")==0){
                holder.tvTitulo.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.DESCRIPCION)));
            }else{
                holder.tvTitulo.setText(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.TITULONOTA)));
            }
            if(cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.IMAGEN))!=null && cursor.getString(cursor.getColumnIndex(ContratoBaseDatos.TablaNota.IMAGEN)).trim().compareTo("")!=0 ){
                holder.iView.setImageResource(R.drawable.ic_action_imagen_card);

            }
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

        TextView tvTitulo;
        ImageView iView;

        public NotaViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            itemView.setOnLongClickListener(this);
            tvTitulo = (TextView) itemView.findViewById(R.id.tvTituloNota);
            iView=(ImageView) itemView.findViewById(R.id.imagenCard);
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