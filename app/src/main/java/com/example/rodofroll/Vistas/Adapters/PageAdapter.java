package com.example.rodofroll.Vistas.Adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.rodofroll.Vistas.Fragments.AtributosFragment;
import com.example.rodofroll.Vistas.Fragments.BiografiaFragment;
import com.example.rodofroll.Vistas.Fragments.CombatPersonajeFragment;
import com.example.rodofroll.Vistas.Fragments.InventarioFragment;

import java.util.ArrayList;
import java.util.List;

public class PageAdapter  extends FragmentStatePagerAdapter {

 //   int numtabs;
    private List<Fragment> fragmentList = new ArrayList<>();

    public PageAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    public void addFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }



}
