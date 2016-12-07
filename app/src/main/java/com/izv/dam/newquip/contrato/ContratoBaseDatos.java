package com.izv.dam.newquip.contrato;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;


public class ContratoBaseDatos {

    public final static String BASEDATOS = "quiip.sqlite";

    private ContratoBaseDatos(){
    }

    public static abstract class TablaNota implements BaseColumns {
        //BaseColumns incluye de forma predeterminada el campo _id
        public static final String TABLA = "nota";
        public static final String TITULONOTA = "titulo";
        public static final String DESCRIPCION = "descripcion";
        public static final String IMAGEN = "imagen";
        public static final String BORRADO = "borrado";
        public static final String[] PROJECTION_ALL = {_ID, TITULONOTA, DESCRIPCION, IMAGEN, BORRADO};
        public static final String SORT_ORDER_DEFAULT = _ID + " desc";

        //proveedor
        public final static String AUTORIDAD = "com.izv.dam.newquip.gestion";
        public final static Uri CONTENT_URI = Uri.parse("content://"+AUTORIDAD);

        public final static String AUTORIDADBORRADOS = "com.izv.dam.newquip.gestion3";
        public final static Uri CONTENT_URI_BORRADOS = Uri.parse("content://"+AUTORIDADBORRADOS);



        //tipos mime
        public final static String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
        public final static String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
    }

    public static abstract class TablaLista implements BaseColumns {
        //BaseColumns incluye de forma predeterminada el campo _id
        public static final String TABLA = "lista";
        public static final String TITULO = "titulo";
        public static final String BORRADO = "borrado";
        public static final String[] PROJECTION_ALL = {_ID, TITULO, BORRADO};
        public static final String SORT_ORDER_DEFAULT = _ID + " desc";

        //proveedor
        public final static String AUTORIDAD = "com.izv.dam.newquip.gestion1";
        public final static Uri CONTENT_URI = Uri.parse("content://"+AUTORIDAD);

        public final static String AUTORIDADBORRADOS = "com.izv.dam.newquip.gestion3";
        public final static Uri CONTENT_URI_BORRADOS = Uri.parse("content://"+AUTORIDADBORRADOS);

        //tipos mime
        public final static String CONTENT_ITEM_TYPE = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
        public final static String CONTENT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
    }

    public static abstract class TablaItemLista implements BaseColumns {
        //BaseColumns incluye de forma predeterminada el campo _id
        public static final String TABLA = "item";
        public static final String TEXTO = "texto";
        public static final String SELECCIONADO = "seleccionado";
        public static final String ID_FORANEO = "foraneo";
        public static final String[] PROJECTION_ALL = {_ID, TEXTO, SELECCIONADO, ID_FORANEO};
        public static final String SORT_ORDER_DEFAULT = _ID + " desc";

        //proveedor
        public final static String AUTORIDAD = "com.izv.dam.newquip.gestion1";
        public final static Uri CONTENT_URI = Uri.parse("content://"+AUTORIDAD);
        public final static String AUTORIDADBORRADOS = "com.izv.dam.newquip.gestion3";
        public final static Uri CONTENT_URI_BORRADOS = Uri.parse("content://"+AUTORIDADBORRADOS);

        //tipos mime
        public final static String CONTENT_ITEM_TYPE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
        public final static String CONTENT_TYPE_ITEM = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
    }
    public static abstract class TablaAjustes implements BaseColumns {
        //BaseColumns incluye de forma predeterminada el campo _id
        public static final String TABLA = "ajustes";
        public static final String COLORNOTAS = "colorNotas";
        public static final String COLORLISTAS = "seleccionado";
        public static final String COLORPAPELERA = "foraneo";
        public static final String[] PROJECTION_ALL = {_ID, COLORNOTAS, COLORLISTAS, COLORPAPELERA};
        public static final String SORT_ORDER_DEFAULT = _ID + " desc";

        //proveedor
        public final static String AUTORIDAD = "com.izv.dam.newquip.gestion2";
        public final static Uri CONTENT_URI = Uri.parse("content://"+AUTORIDAD);

        //tipos mime
        public final static String CONTENT_ITEM_TYPE_ITEM = ContentResolver.CURSOR_ITEM_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
        public final static String CONTENT_TYPE_ITEM = ContentResolver.CURSOR_DIR_BASE_TYPE + "/vnd." + AUTORIDAD + "." + TABLA;
    }
}