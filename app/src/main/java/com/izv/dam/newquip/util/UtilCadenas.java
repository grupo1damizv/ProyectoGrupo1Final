package com.izv.dam.newquip.util;

/**
 * Created by toniarcogarcia on 1/11/16.
 */

public class UtilCadenas {

    public static String getCondicionesSql(String condicionesIni, String newCondicion){
        return getCondicionesSql(condicionesIni, newCondicion, "and");
    }

    public static String getCondicionesSql(String condicionesIni, String newCondicion, String conector){
        if(condicionesIni == null || condicionesIni.trim().length() == 0){
            return newCondicion;
        }
        return condicionesIni + " " + conector + " " +newCondicion;
    }

    public static String[] getNewArray(String[] arrayInicial, String parametro){
        if(arrayInicial == null){
            return new String[]{parametro};
        }

        String[] newArray = new String[arrayInicial.length + 1];
        for (int i = 0; i < arrayInicial.length; i++) {
            newArray[i] = arrayInicial[i];
        }

        newArray[arrayInicial.length] = parametro;
        return newArray;
    }
}
