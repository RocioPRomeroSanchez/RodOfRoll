package com.example.rodofroll.Vistas;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

class PageAdapter  extends FragmentStatePagerAdapter {

    int numtabs;

    public PageAdapter(@NonNull FragmentManager fm, int behavior, int numtabs) {
        super(fm, behavior);
        this.numtabs = numtabs;
    }

    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                Fragment tab1 = new AtributosFragment();
                return tab1;

            case 1:
                Fragment tab2 = new BiografiaFragment();
                return tab2;


            default:
                return new AtributosFragment();


        }
    }

    @Override
    public int getCount() {
        return numtabs;
    }
}