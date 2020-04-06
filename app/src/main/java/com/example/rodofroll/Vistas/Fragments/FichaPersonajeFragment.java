package com.example.rodofroll.Vistas.Fragments;

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

import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.Objetos.onSelectedItemListener;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Adapters.PageAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import java.util.List;

public class FichaPersonajeFragment extends Fragment  {


    onSelectedItemListener milistener;
    TabLayout tabs;
    Personaje p=null;

    //codigo nuevo fragment
    List<Fragment> fragments;


    public FichaPersonajeFragment(Personaje p) {
        this.p=p;

    }

    public FichaPersonajeFragment() {

    }

    public Personaje getPersonaje() {
        return p;
    }

    public void setPersonaje(Personaje p) {
        this.p = p;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.layout_ficha, container, false);

        tabs = view.findViewById(R.id.fichatab);
        tabs.addTab(tabs.newTab().setText("Atributos").setIcon(R.drawable.shield_star));
        tabs.addTab(tabs.newTab().setText("Biografia").setIcon(R.drawable.book));
        tabs.addTab(tabs.newTab().setText("Inventario").setIcon(R.drawable.chest));
        tabs.addTab(tabs.newTab().setText("Combate").setIcon(R.drawable.sword_cross));

      /*  fragments.add(new AtributosFragment(p));
        fragments.add(new BiografiaFragment(p));
        fragments.add(new InventarioFragment(p));
        fragments.add(new CombatPersonajeFragment(p));
*/


        final ViewPager mviewPager = (ViewPager) view.findViewById(R.id.viewPager);
       // final PageAdapter adapter = new PageAdapter(getFragmentManager(),tabs.getTabCount(),tabs.getTabCount());
        final PageAdapter adapter = new PageAdapter(getChildFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);


        adapter.addFragment(new AtributosFragment(p));
        adapter.addFragment(new BiografiaFragment(p));
        adapter.addFragment(new InventarioFragment(p));
        adapter.addFragment(new CombatPersonajeFragment(p));


        mviewPager.setAdapter(adapter);
        mviewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabs));
        final FloatingActionButton button = view.findViewById(R.id.caractfloatButton);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener()
        {

            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mviewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==2){
                    button.setImageResource(R.drawable.plus);
                }
                else{
                    button.setImageResource(R.drawable.check);
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
