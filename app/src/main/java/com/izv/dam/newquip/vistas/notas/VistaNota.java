package com.izv.dam.newquip.vistas.notas;

import android.annotation.TargetApi;
import android.app.NotificationManager;
import android.content.ClipData;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.NotificationCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.contrato.ContratoNota;

import com.izv.dam.newquip.databinding.ActivityNotaBinding;
import com.izv.dam.newquip.pojo.Nota;
import com.izv.dam.newquip.util.CrearPDFs;
import com.izv.dam.newquip.util.UtilFecha;
import com.izv.dam.newquip.vistas.main.ActividadPrincipal;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.Manifest.permission.CAMERA;

public class VistaNota extends AppCompatActivity implements ContratoNota.InterfaceVista {

    private Nota nota = new Nota();
    private ActivityNotaBinding binding;
    private PresentadorNota presentador;
    private ImageView imageView;
    private Uri uriImagen;
    private final int MY_PERMISSIONS = 100;
    private LinearLayout rl;


    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int SELECT_PICTURE = 300;
    static final int NOTIFICATION_ID = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nota);
        nota = new Nota();
        presentador = new PresentadorNota(this);

        imageView = (ImageView) findViewById(R.id.imageView);

        if (savedInstanceState != null) {
            nota = savedInstanceState.getParcelable("nota");
        } else {
            Bundle b = getIntent().getExtras();
            if (b != null) {
                nota = b.getParcelable("nota");
                uriImagen = Uri.parse(nota.getImagen());

            }
        }
        binding.setNota(nota);
        mostrarNota(nota);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_nota, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.guardar) {
            saveNota();
            finish();
            return true;
        }
        if (id == R.id.camara) {
            mayRequestStoragePermission();
            mostrarOpciones();
            return true;
        }
        if (id == R.id.imprimir) {
            if (nota.getTitulo().isEmpty() && nota.getDescripcion().isEmpty() && uriImagen.toString().trim().compareTo("") == 0) {
                Toast.makeText(VistaNota.this, "Debes rellenar algún campo para imprimir la nota", Toast.LENGTH_SHORT).show();
            } else {
                CrearPDFs pdf = new CrearPDFs(nota.getTitulo(), nota.getDescripcion(), nota.getImagen());

                pdf.execute();

                NotificationCompat.Builder builder = new NotificationCompat.Builder(VistaNota.this);
                builder.setSmallIcon(android.R.drawable.stat_sys_download_done);
                builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_pdf));
                builder.setContentTitle("Archivo creado");
                builder.setContentText("PDF");
                NotificationManager notificacion = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
                Toast.makeText(VistaNota.this, "Imprimiendo PDF", Toast.LENGTH_SHORT).show();
                notificacion.notify(NOTIFICATION_ID, builder.build());
            }
        }
        return false;
    }

    @Override
    protected void onPause() {
        presentador.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        presentador.onResume();
        super.onResume();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable("nota", nota);

    }

    @Override
    public void mostrarNota(Nota n) {
        uriImagen = Uri.parse(nota.getImagen());
        Picasso.with(this).load(uriImagen).placeholder(android.R.drawable.ic_menu_camera).into(imageView);
    }

    private void saveNota() {
        nota.setImagen(uriImagen.toString());
        long r = presentador.onSaveNota(nota);
        if (r > 0 & nota.getId() == 0) {
            nota.setId(r);
        }
    }

    public void mostrarOpciones() {
        final CharSequence[] option = {"Hacer foto", "Abrir galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(VistaNota.this);
        builder.setTitle("Elige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (option[which] == "Hacer foto") {

                    hacerfoto();
                } else if (option[which] == "Abrir galeria") {
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                } else {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    public void hacerfoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }

    public void guardarImagen(Bitmap imagen) {

        File carpeta = new File(getFilesDir().getPath(), "misfotos");

        if (!carpeta.exists()) {
            carpeta.mkdirs();
        }

        File imagenActual = new File(carpeta, UtilFecha.formatDate() + ".jpg");

        try {
            FileOutputStream fOut = new FileOutputStream(imagenActual);
            imagen.compress(Bitmap.CompressFormat.JPEG, 85, fOut);

            fOut.flush();
            fOut.close();

        } catch (IOException e) {

        }

        uriImagen = Uri.fromFile(imagenActual);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case REQUEST_IMAGE_CAPTURE: {

                    guardarImagen((Bitmap) data.getExtras().get("data"));
                    break;

                }
                case SELECT_PICTURE: {

                    try {
                        Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
                        guardarImagen(bmp);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    break;

                }
            }

            if (uriImagen != null) {

                nota.setImagen(uriImagen.toString());
                try {
                    Bitmap bmp = MediaStore.Images.Media.getBitmap(getContentResolver(), uriImagen);
                    imageView.setImageBitmap(bmp);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Picasso.with(this).load(uriImagen).placeholder(android.R.drawable.ic_menu_camera).into(imageView);
            }
        }
    }

    private boolean mayRequestStoragePermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        if ((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) && (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }

        if ((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))) {
            Snackbar.make(rl, "Los permisos son necesarios para poder usar la aplicación", Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            }).show();
        } else {
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == MY_PERMISSIONS) {
            if (grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permisos aceptados", Toast.LENGTH_SHORT).show();
                imageView.setEnabled(true);
            } else {
                showExplanation();
            }
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(VistaNota.this);
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }

}