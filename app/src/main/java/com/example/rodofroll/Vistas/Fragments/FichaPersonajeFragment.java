package com.example.rodofroll.Vistas.Fragments;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.rodofroll.Actividad;
import com.example.rodofroll.Objetos.Combatiente;
import com.example.rodofroll.Objetos.ComunicateToTabsListener;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.Objetos.onSelectedItemListener;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Adapters.PageAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class FichaPersonajeFragment extends Fragment {


    onSelectedItemListener milistener;
    TabLayout tabs;
    Combatiente p;

    //codigo nuevo fragment
    List<Fragment> fragments;


    public FichaPersonajeFragment(Combatiente p) {
        this.p=p;

    }

    public FichaPersonajeFragment() {

    }

    public Combatiente getPersonaje() {
        return p;
    }

    public void setPersonaje(Combatiente p) {
        this.p = p;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.layout_ficha, container, false);

        tabs = view.findViewById(R.id.fichatab);
        tabs.addTab(tabs.newTab().setText("Atributos").setIcon(R.drawable.shield_star));
        tabs.addTab(tabs.newTab().setText("Biografia").setIcon(R.drawable.book));

        final ViewPager mviewPager = (ViewPager) view.findViewById(R.id.viewPager);
        final PageAdapter adapter = new PageAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        if(p instanceof  Personaje){
            tabs.addTab(tabs.newTab().setText("Inventario").setIcon(R.drawable.chest));
            tabs.addTab(tabs.newTab().setText("Combate").setIcon(R.drawable.sword_cross));
        }




        adapter.addFragment(new AtributosFragment(p));
        adapter.addFragment(new BiografiaFragment(p,(Actividad) getActivity()));
        if(p instanceof  Personaje){
            adapter.addFragment(new InventarioFragment(p,getActivity()));
            adapter.addFragment(new CombatPersonajeFragment(p));
        }




        mviewPager.setAdapter(adapter);
        mviewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        final FloatingActionButton button = view.findViewById(R.id.caractfloatButton);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {

            @SuppressLint("RestrictedApi")
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mviewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==2){
                    button.setVisibility(View.VISIBLE);
                    button.setImageResource(R.drawable.plus);
                }
                else{
                    button.setVisibility(View.GONE);
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(milistener!=null)milistener.onSelectedItemListener(1,adapter.getItem(mviewPager.getCurrentItem()));


            }
        });


        TabLayout.Tab tab = tabs.getTabAt(1);
        tab.select();



        return  view;

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try
        {
            milistener=(onSelectedItemListener)context;



        }catch (ClassCastException e){}
    }


}
