package com.izv.dam.newquip.gestion;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.util.UtilCadenas;

import static com.izv.dam.newquip.contrato.ContratoBaseDatos.TablaNota.CONTENT_ITEM_TYPE;
import static com.izv.dam.newquip.contrato.ContratoBaseDatos.TablaNota.CONTENT_TYPE;

public class ProveedorBorrados extends ContentProvider {

    private static final UriMatcher URI_MATCHER;
    private static final int TODO = 0;
    private static final int CONCRETO = 1;
    private static final int TODO_LISTA = 2;
    private static final int CONCRETO_LISTA = 3;
    private static final int TODO_ITEM_LISTA = 4;
    private static final int CONCRETO_ITEM_LISTA = 5;
    private GestionNota gestor;
    private GestionLista gestorLista;
    private GestionItemLista gestionItemLista;

    //constructor estático
    static {
        URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
        URI_MATCHER.addURI(ContratoBaseDatos.TablaNota.AUTORIDADBORRADOS, ContratoBaseDatos.TablaNota.TABLA, TODO);
        URI_MATCHER.addURI(ContratoBaseDatos.TablaNota.AUTORIDADBORRADOS, ContratoBaseDatos.TablaNota.TABLA + "/#", CONCRETO);
        URI_MATCHER.addURI(ContratoBaseDatos.TablaLista.AUTORIDADBORRADOS, ContratoBaseDatos.TablaLista.TABLA, TODO_LISTA);
        URI_MATCHER.addURI(ContratoBaseDatos.TablaLista.AUTORIDADBORRADOS, ContratoBaseDatos.TablaLista.TABLA + "/#", CONCRETO_LISTA);
        URI_MATCHER.addURI(ContratoBaseDatos.TablaItemLista.AUTORIDADBORRADOS, ContratoBaseDatos.TablaItemLista.TABLA, TODO_ITEM_LISTA);
        URI_MATCHER.addURI(ContratoBaseDatos.TablaItemLista.AUTORIDADBORRADOS, ContratoBaseDatos.TablaItemLista.TABLA + "/#", CONCRETO_ITEM_LISTA);
    }

    public ProveedorBorrados() {
    }

    @Override
    public boolean onCreate() {
        gestor = new GestionNota(getContext());
        gestorLista=new GestionLista(getContext());
        gestionItemLista=new GestionItemLista(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor c = null;
        int tipo = URI_MATCHER.match(uri);
        if(tipo < 0){
            throw new IllegalArgumentException("Error");
        }
        c = gestorLista.getCursorJoinBorrados();
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (URI_MATCHER.match(uri)){
            case TODO: return CONTENT_TYPE;
            case CONCRETO: return CONTENT_ITEM_TYPE;
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        int tipo = URI_MATCHER.match(uri);
        long id;
        if(tipo == TODO){
            id = gestor.insert(values);
        }else{
            throw new IllegalArgumentException("Error");
        }
        if(id > 0){
            Uri uriGeller = ContentUris.withAppendedId(uri, id);
            getContext().getContentResolver().notifyChange(uriGeller, null);
            return uriGeller;
        }
        throw new IllegalArgumentException("Error");
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int borrados = 0;
        int tipo = URI_MATCHER.match(uri);

        switch (tipo){
            case CONCRETO:
                String id = uri.getLastPathSegment();
                borrados = gestor.deleteById(id);
                break;
            case TODO:
                borrados = gestor.delete(selection, selectionArgs);
                break;
            case TODO_LISTA:
                borrados = gestorLista.delete(selection,selectionArgs);
                break;
            case CONCRETO_LISTA:
                String where = ContratoBaseDatos.TablaLista._ID + " = ?";
                String[] args = new String[]{uri.getLastPathSegment()};
                gestorLista.delete(ContratoBaseDatos.TablaLista.TABLA, where, args);
                borrados = 1;
                break;
            case CONCRETO_ITEM_LISTA:
                long idItem = Long.parseLong(uri.getLastPathSegment());
                gestionItemLista.deleteItems(idItem);
                borrados=1;
                break;
            case TODO_ITEM_LISTA:
                borrados = gestionItemLista.delete(selection,selectionArgs);
                break;
            default:
                throw new IllegalArgumentException("Error");
        }

        if(borrados > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return borrados;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        int tipo = URI_MATCHER.match(uri);
        int valor=0;
        if(tipo == CONCRETO){
            String id = uri.getLastPathSegment();
            selection = UtilCadenas.getCondicionesSql(selection, ContratoBaseDatos.TablaNota._ID + " = ?");
            selectionArgs = UtilCadenas.getNewArray(selectionArgs, id);
            valor = gestor.update(values, selection, selectionArgs);
        }
        if(tipo== CONCRETO_LISTA){
            String id = uri.getLastPathSegment();
            selection = UtilCadenas.getCondicionesSql(selection, ContratoBaseDatos.TablaLista._ID + " = ?");
            selectionArgs = UtilCadenas.getNewArray(selectionArgs, id);
            valor=gestorLista.update(values,selection,selectionArgs);
        }

        if(valor > 0){
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return valor;
    }
}
