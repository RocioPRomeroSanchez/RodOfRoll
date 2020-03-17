package com.example.rodofroll.Vistas.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.rodofroll.Vistas.Fragments.AtributosFragment;
import com.example.rodofroll.Vistas.Fragments.BiografiaFragment;
import com.example.rodofroll.Vistas.Fragments.CombatPersonajeFragment;
import com.example.rodofroll.Vistas.Fragments.InventarioFragment;

public class PageAdapter  extends FragmentStatePagerAdapter {

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


            case 2:
                Fragment tab3 = new InventarioFragment();
                return tab3;

            case 3:
                Fragment tab4 = new CombatPersonajeFragment();
                return  tab4;

            default:
                return new AtributosFragment();


        }
    }

    @Override
    public int getCount() {
        return numtabs;
    }
}
