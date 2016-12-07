package com.izv.dam.newquip.adaptadores;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dam on 04/11/2016.
 */

public class AdaptadorPaginaFragmento extends FragmentPagerAdapter {

    // List of fragments which are going to set in the view pager widget
    ArrayList<Fragment> fragments;
    private String tabTitles[] =
            new String[] { "Notas", "Listas","Papelera"};


    /**
     * Constructor
     *
     * @param fm
     *            interface for interacting with Fragment objects inside of an
     *            Activity
     */
    public AdaptadorPaginaFragmento(FragmentManager fm) {
        super(fm);
        this.fragments = new ArrayList<Fragment>();
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return tabTitles[position];
    }

    /**
     * Add a new fragment in the list.
     *
     * @param fragment
     *            a new fragment
     */
    public void addFragment(Fragment fragment) {
        this.fragments.add(fragment);
    }

    public void removeFragments(Fragment fragment){this.fragments.remove(fragment);}

    public void updateFragments(Bundle bundle1,Bundle bundle2, Bundle bundle3){
        this.fragments.get(0).setArguments(bundle1);
        this.fragments.get(1).setArguments(bundle1);
        this.fragments.get(2).setArguments(bundle1);
    }

    @Override
    public Fragment getItem(int arg0) {
        System.out.println(fragments.size());
        return this.fragments.get(arg0);
    }

    @Override
    public int getCount() {
        return this.fragments.size();
    }

}