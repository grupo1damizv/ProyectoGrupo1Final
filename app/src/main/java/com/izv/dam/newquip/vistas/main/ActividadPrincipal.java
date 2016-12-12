package com.izv.dam.newquip.vistas.main;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.CursorJoiner;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ContentFrameLayout;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.izv.dam.newquip.R;
import com.izv.dam.newquip.adaptadores.AdaptadorBorrados;
import com.izv.dam.newquip.adaptadores.AdaptadorLista;
import com.izv.dam.newquip.adaptadores.AdaptadorNota;
import com.izv.dam.newquip.adaptadores.AdaptadorPaginaFragmento;
import com.izv.dam.newquip.adaptadores.ClickItem;
import com.izv.dam.newquip.contrato.ContratoBaseDatos;
import com.izv.dam.newquip.contrato.ContratoItemLista;
import com.izv.dam.newquip.contrato.ContratoLista;
import com.izv.dam.newquip.contrato.ContratoMain;
import com.izv.dam.newquip.dialogo.Dialogo;
import com.izv.dam.newquip.dialogo.DialogoBorrarLista;
import com.izv.dam.newquip.dialogo.DialogoRecuperarLista;
import com.izv.dam.newquip.dialogo.DialogoRecuperarNota;
import com.izv.dam.newquip.dialogo.DialogoVaciarPapelera;
import com.izv.dam.newquip.dialogo.OnBorrarDialogListener;
import com.izv.dam.newquip.pojo.Lista;
import com.izv.dam.newquip.pojo.Nota;
import com.izv.dam.newquip.vistas.ajustes.VistaAjustes;
import com.izv.dam.newquip.vistas.fragmentos.borrados.FragmentoPresentadorBorrados;
import com.izv.dam.newquip.vistas.fragmentos.listas.FragmentoPresentadorLista;
import com.izv.dam.newquip.vistas.fragmentos.listas.FragmentoVistaLista;
import com.izv.dam.newquip.vistas.fragmentos.notas.FragmentoPresentadorNota;
import com.izv.dam.newquip.vistas.fragmentos.borrados.FragmentoVistaBorrados;
import com.izv.dam.newquip.vistas.fragmentos.notas.FragmentoVistaNota;
import com.izv.dam.newquip.vistas.listas.VistaLista;
import com.izv.dam.newquip.vistas.notas.VistaNota;

/**
 * Created by dam on 04/11/2016.
 */

public class ActividadPrincipal extends AppCompatActivity implements
        ContratoMain.InterfaceVista, OnBorrarDialogListener,
        ClickItem, LoaderManager.LoaderCallbacks<Cursor>,
        ContratoLista.InterfaceVista, OnBorrarDialogListener.OnBorrarDialogListenerLista,
        OnBorrarDialogListener.OnBorrarDialogListenerVaciar {

    /**
     * The pager widget, which handles animation and allows swiping horizontally
     * to access previous and next pages.
     */

    ViewPager pager = null;
    AdaptadorPaginaFragmento adapter;
    private FloatingActionButton fab;
    private Animation fabOpen, fabClose;
    private FragmentoPresentadorNota presentador;
    private FragmentoPresentadorBorrados presentadorBorrados;
    private FragmentoPresentadorLista presentadorLista;
    private AdaptadorNota adaptador;
    private AdaptadorBorrados adaptadorBorrados;
    private AdaptadorLista adaptadorLista;
    private int pagina = 0;
    private boolean borradoNota = true;
    SharedPreferences prefs;
    /**
     * The pager adapter, which provides the pages to the view pager widget.
     */
    AdaptadorPaginaFragmento pagerAdapter;
    private final static int AJUSTES = 1;

    @Override
    protected void onCreate(final Bundle arg0) {
        super.onCreate(arg0);
        this.setContentView(R.layout.main);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Create an adapter with the fragments we show on the ViewPager
        adapter = new AdaptadorPaginaFragmento(getSupportFragmentManager());

        prefs = getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

        // Instantiate a ViewPager
        this.pager = (ViewPager) this.findViewById(R.id.viewpager);

        presentador = new FragmentoPresentadorNota(this);
        presentadorBorrados = new FragmentoPresentadorBorrados(this);
        presentadorLista = new FragmentoPresentadorLista(this);
        adaptador = new AdaptadorNota(this, null, this);
        adaptadorBorrados = new AdaptadorBorrados(this, null, this);
        adaptadorLista = new AdaptadorLista(this, null, this);

        getSupportLoaderManager().initLoader(1, null, this);
        getSupportLoaderManager().initLoader(2,null,this);
        getSupportLoaderManager().initLoader(3,null,this);

        adapter.addFragment(FragmentoVistaNota.newInstance(0,
                adaptador, presentador,obtenerColor(prefs.getInt("colorNota",Color.WHITE))));
        adapter.addFragment(FragmentoVistaLista.newInstance(1, adaptadorLista,
                presentadorLista,obtenerColor(prefs.getInt("colorLista",Color.WHITE))));
        adapter.addFragment(FragmentoVistaBorrados.newInstance(2, adaptadorBorrados,
                presentadorBorrados,obtenerColor(prefs.getInt("colorPapelera",Color.WHITE))));

        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayat);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        tabLayout.setupWithViewPager(pager);
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton3);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pagina == 0) {
                    presentador.onAddNota();
                } else if (pagina == 1) {
                    presentadorLista.onAddLista();
                } else {
                    presentadorBorrados.onShowVaciarPapelera(0);

                }

            }
        });
        fabOpen = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop_up);
        fabClose = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.pop_down);

        pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int j) {

                switch (i) {
                    case 0:
                        fab.setImageResource(R.drawable.ic_action_crear);
                        fab.startAnimation(fabOpen);
                        break;
                    case 1:
                        fab.setImageResource(R.drawable.ic_action_crear_lista);
                        fab.startAnimation(fabOpen);
                        fab.show();
                        break;
                    default:
                        fab.setImageResource(R.drawable.ic_action_vaciar_white);
                        fab.startAnimation(fabOpen);
                        fab.show();
                        break;
                }

            }


            @Override
            public void onPageSelected(int position) {
                 pagina=position;
                if (position == 0) {
                    getSupportLoaderManager().restartLoader(1, null, ActividadPrincipal.this);
                } else if (position == 1) {
                    getSupportLoaderManager().restartLoader(2, null, ActividadPrincipal.this);
                } else if (position == 2) {
                    getSupportLoaderManager().restartLoader(3, null, ActividadPrincipal.this);

                }


            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        this.pager.setAdapter(adapter);
        actualizaCursores();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_ajustes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.ajustes) {
            Intent intent= new Intent(ActividadPrincipal.this, VistaAjustes.class);
            startActivity(intent);
            return true;
        }
        return false;
    }

    @Override
    protected void onPause() {

        super.onPause();
        actualizaCursores();
    }

    @Override
    protected void onResume() {

        actualizaColores();
        super.onResume();
        actualizaCursores();

    }

    @Override
    public void mostrarAgregarNota() {
        Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, VistaNota.class);
        startActivity(i);
    }

    @Override
    public void mostrarAgregarLista() {
        Toast.makeText(this, "add", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, VistaLista.class);
        startActivity(i);
    }

    @Override
    public void mostrarLista(Cursor c) {

    }

    @Override
    public void mostrarEditarLista(Lista n) {
        Intent i = new Intent(ActividadPrincipal.this, VistaLista.class);
        Bundle b = new Bundle();
        b.putParcelable("lista", n);
        i.putExtras(b);
        startActivity(i);
    }

    @Override
    public void mostrarConfirmarBorrarLista(Lista n) {
        if (pagina == 1) {
            DialogoBorrarLista fragmentBorrar = DialogoBorrarLista.newInstance(n);
            fragmentBorrar.show(getSupportFragmentManager(), "Dialogo borrar");
        } else if (pagina == 2) {
            DialogoRecuperarLista fragmentBorrar = DialogoRecuperarLista.newInstance(n);
            fragmentBorrar.show(getSupportFragmentManager(), "Dialogo recuperar lista");
        }

    }

    @Override
    public void mostrarDatos(Cursor c) {
        if (pagina == 0) {
            adaptador.changeCursor(c);
        } else if (pagina == 1) {
            adaptadorLista.changeCursor(c);
        } else if (pagina == 2) {
            adaptadorBorrados.changeCursor(c);
        }

    }

    @Override
    public void mostrarEditarNota(Nota n) {
        Toast.makeText(this, "edit", Toast.LENGTH_SHORT).show();
        Intent i = new Intent(this, VistaNota.class);
        Bundle b = new Bundle();
        b.putParcelable("nota", n);
        i.putExtras(b);
        startActivity(i);
    }

    @Override
    public void mostrarConfirmarBorrarNota(Nota n) {
        if (pagina == 0) {
            Dialogo fragmentBorrar = Dialogo.newInstance(n);
            fragmentBorrar.show(getSupportFragmentManager(), "Dialogo borrar");
        } else if (pagina == 2) {
            DialogoRecuperarNota fragmentBorrar = DialogoRecuperarNota.newInstance(n);
            fragmentBorrar.show(getSupportFragmentManager(), "Dialogo recuperar");
        }


    }

    @Override
    public void mostrarConfirmarVaciarPapelera(Object o) {
        DialogoVaciarPapelera fragmentBorrar = DialogoVaciarPapelera.newInstance(o);
        fragmentBorrar.show(getSupportFragmentManager(), "Dialogo borrar");
    }


    @Override
    public void onItemClickListener(int pos) {
        if (pagina == 0) {
            presentador.onEditNota(pos);
        } else if (pagina == 1) {
            presentadorLista.onEditLista(pos);
        }
    }

    @Override
    public void onItemLongClickListener(int pos) {
        if (pagina == 0) {
            presentador.onShowBorrarNota(pos, pagina);
        } else if (pagina == 1) {
            presentadorLista.onShowBorrarLista(pos);
        } else if (pagina == 2) {
            presentadorBorrados.onShowBorrarObjeto(pos);
        }

    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        Uri uri = null;
        if (id == 1) {
            uri = Uri.withAppendedPath(ContratoBaseDatos.TablaNota.CONTENT_URI, ContratoBaseDatos.TablaNota.TABLA);
        } else if (id == 2) {
            uri = Uri.withAppendedPath(ContratoBaseDatos.TablaLista.CONTENT_URI, ContratoBaseDatos.TablaLista.TABLA);
        } else if (id == 3) {
            uri = Uri.withAppendedPath(ContratoBaseDatos.TablaNota.CONTENT_URI_BORRADOS, ContratoBaseDatos.TablaNota.TABLA);
        }
        CursorLoader cursorLoader = new CursorLoader(this, uri, null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        if (pagina == 0) {
            adaptador.changeCursor(data);
            presentador.changeCursor(data);
        } else if (pagina == 1) {
            adaptadorLista.changeCursor(data);
            presentadorLista.changeCursor(data);
        } else if (pagina == 2) {
            adaptadorBorrados.changeCursor(data);
            presentadorBorrados.changeCursor(data);

        }

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        if (pagina == 0) {
            adaptador.changeCursor(null);
        } else if (pagina == 1) {
            adaptadorLista.changeCursor(null);
        } else if (pagina == 2) {
            adaptadorBorrados.changeCursor(null);
        }

    }

    @Override
    public void onBorrarPossitiveButtonClick(Nota n) {
        if (pagina == 0) {
            n.setBorrado(1);
            presentador.onSaveNota(n);
        } else if (pagina == 2) {
            presentadorBorrados.onDeleteNota(n);
        }
        actualizaCursores();
    }

    @Override
    public void onBorrarNegativeButtonClick(Nota n) {
        if (pagina == 0) {

        } else if (pagina == 2) {
            n.setBorrado(0);
            presentadorBorrados.onSaveNota(n);
            actualizaCursores();
        }
    }

    @Override
    public void onBorrarPossitiveButtonClick(Lista l) {
        if (pagina == 1) {
            l.setBorrado(1);
            presentadorLista.onSaveLista(l);
            actualizaCursores();
        } else if (pagina == 2) {
            presentadorBorrados.onDeleteLista(l);
        }

    }

    @Override
    public void onBorrarNegativeButtonClick(Lista l) {
        if (pagina == 1) {

        } else if (pagina == 2) {
            l.setBorrado(0);
            presentadorBorrados.onSaveLista(l);
            actualizaCursores();
        }
    }

    public void actualizaCursores() {

        if (pagina == 0) {
            getSupportLoaderManager().restartLoader(1, null, ActividadPrincipal.this);
        } else if (pagina == 1) {
            getSupportLoaderManager().restartLoader(2, null, ActividadPrincipal.this);
        } else if (pagina == 2) {
            getSupportLoaderManager().restartLoader(3, null, ActividadPrincipal.this);
        }
    }

    @Override
    public void onBorrarPossitiveButtonClick() {
        presentadorBorrados.vaciarPapelera();
        actualizaCursores();
    }

    @Override
    public void onBorrarNegativeButtonClick() {

    }

    public int obtenerColor(int valorPreferencias){
        switch(valorPreferencias){
            case 0:
                return Color.WHITE;
            case 1:
                return Color.BLACK;
            case 2:
                return Color.BLUE;
            case 3:
                return Color.CYAN;
            case 4:
                return Color.GRAY;
            case 5:
                return Color.MAGENTA;
            case 6:
                return Color.RED;
            case 7:
                return Color.YELLOW;
            case 8:
                return Color.DKGRAY;
            case 9:
                return Color.LTGRAY;
        }
        return -1;
    }

    public void actualizaColores(){

        adapter.removeFragments(adapter.getItem(0));
        adapter.removeFragments(adapter.getItem(0));
        adapter.removeFragments(adapter.getItem(0));
        System.out.println("agrego el fragmento 1");
        adapter.addFragment(FragmentoVistaNota.newInstance(0,
               adaptador, presentador,obtenerColor(prefs.getInt("colorNota",Color.WHITE))));
        System.out.println("agrego el fragmento 2");
        adapter.addFragment(FragmentoVistaLista.newInstance(1,
                adaptadorLista, presentadorLista,obtenerColor(prefs.getInt("colorLista",Color.WHITE))));
        System.out.println("agrego el fragmento 3");
        adapter.addFragment(FragmentoVistaBorrados.newInstance(2,
               adaptadorBorrados, presentadorBorrados,obtenerColor(prefs.getInt("colorPapelera",Color.WHITE))));

        adapter.notifyDataSetChanged();

    }

}

