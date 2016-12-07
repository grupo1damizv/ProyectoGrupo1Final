package com.izv.dam.newquip.util;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Environment;
import android.widget.Toast;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerHelper;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

/**
 * Created by dam on 9/11/16.
 */

public class CrearPDFs extends AsyncTask<String, String, String>{
    private static final String NOMBRE_CARPETA_APP = "PDFsQuip";
    private static final String NOMBRE_SUBCARPETA_APP = "MisArchivos";
    String titulo;
    String descripcion;
    String imagen;

    public CrearPDFs(String titulo, String descripcion, String imagen) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.imagen = imagen;
    }

    public CrearPDFs() {
    }

    @Override
    protected String doInBackground(String... params) {

        String nombreArchivo = UtilFecha.formatDate() + titulo + ".pdf";
        String tarjetaSD = Environment.getExternalStorageDirectory().toString();
        File pdfDir = new File(tarjetaSD + File.separator + NOMBRE_CARPETA_APP);
        Document document = new Document(PageSize.A4);

        if(!pdfDir.exists()){
            pdfDir.mkdir();
        }

        File pdfSubDir = new File(pdfDir.getPath() + File.separator + NOMBRE_SUBCARPETA_APP);
        if(!pdfSubDir.exists()){
            pdfSubDir.mkdir();
        }

        String nombre_completo = Environment.getExternalStorageDirectory() + File.separator + NOMBRE_CARPETA_APP +
                File.separator + NOMBRE_SUBCARPETA_APP + File.separator + nombreArchivo;

        File outputfile = new File(nombre_completo);
        if(outputfile.exists()){
            outputfile.delete();
        }

        try {
            PdfWriter pdfWriter = PdfWriter.getInstance(document, new FileOutputStream(nombre_completo));
            document.open();
            document.addTitle("PDF Quip");

            XMLWorkerHelper worker = XMLWorkerHelper.getInstance();
            String htmlToPDF = "<html><head></head><body><h1>" + titulo + "</h1><p>" + descripcion + "</p><img src='"+ imagen +"'/></body></html>";

            try {
                System.out.println(htmlToPDF);
                worker.parseXHtml(pdfWriter, document, new StringReader(htmlToPDF));
                document.close();

            } catch (IOException e) {
                e.printStackTrace();
            }

        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return nombreArchivo;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public void muestraPDF(String pdf, Context context){
        File file = new File(pdf);
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file), "aplication/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            context.startActivity(intent);

        }catch (ActivityNotFoundException e){
            Toast.makeText(context, "No tiene una aplicacion que abra PDF", Toast.LENGTH_SHORT).show();
        }
    }
}
