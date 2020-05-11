package com.example.rodofroll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rodofroll.Firebase.FirebaseUtilsV1;
import com.example.rodofroll.Objetos.ListenUserService;
import com.example.rodofroll.Objetos.AsynTarea;
import com.example.rodofroll.Objetos.ComunicateToTabsListener;
import com.example.rodofroll.Objetos.ConversorImagenes;
import com.example.rodofroll.Objetos.OnTaskCompleted;
import com.example.rodofroll.Objetos.onSelectedItemListener;
import com.example.rodofroll.Vistas.Fragments.BuscarCombateFragment;
import com.example.rodofroll.Vistas.Fragments.CombateRecyclerFragment;
import com.example.rodofroll.Vistas.Fragments.DadosFragment;
import com.example.rodofroll.Vistas.Fragments.FichaPersonajeFragment;
import com.example.rodofroll.Vistas.Fragments.MiCuentaFragment;
import com.example.rodofroll.Vistas.Fragments.MonsRecyclerViewFragment;
import com.example.rodofroll.Vistas.Fragments.NotificacionesFragment;
import com.example.rodofroll.Vistas.Fragments.PerRecyclerViewFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;
import java.util.List;


public class MainActivity extends Actividad implements onSelectedItemListener {





    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    NavigationView navigationView;




    private OnTaskCompleted listener;
    AsynTarea tarea;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        listener=null;

        listener = new OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {

                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);

                toolbar.setVisibility(View.VISIBLE);
                Intent intent = getIntent();

                CrearMenus(R.menu.mastermenu);



            }

            @Override
            public void onTaskFailure() {

            }
        };


        tarea = null;
        try {
            tarea = new AsynTarea(listener, fragmentManager);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tarea.execute();

        FirebaseUtilsV1.GetDatosUsuario();



    }

    public void CrearMenus(int tipomenu) {
        startService(new Intent(getApplicationContext(), ListenUserService.class));
        Toolbar toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        actionBar.setDisplayHomeAsUpEnabled(true);


        Fragment fragment = new MiCuentaFragment();
        RemplazarFragment(fragment, false);


        final NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.inflateMenu(tipomenu);


        View hView = navigationView.getHeaderView(0);
        TextView correo = (TextView) hView.findViewById(R.id.identiftextView);
        ImageView imagen = hView.findViewById(R.id.usuarioimagen);


        String apodo = FirebaseUtilsV1.getDatosUser().getNombre();
         String foto= FirebaseUtilsV1.getDatosUser().getFoto();
         correo.setText(apodo);
          imagen.setImageBitmap(ConversorImagenes.convertirStringBitmap(foto));




        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                Fragment fragment = new Fragment();



                switch (menuItem.getItemId()) {

                    case R.id.navigation_item_personajes:
                        fragment = new PerRecyclerViewFragment();

                        break;
                    case R.id.navigation_item_dados:
                        fragment = new DadosFragment();

                        break;


                    case R.id.navigation_item_moster:
                        fragment = new MonsRecyclerViewFragment();

                        break;

                    case R.id.navigation_item_buscar_combate:
                        fragment = new BuscarCombateFragment();
                        break;

                    case R.id.micuenta:
                        fragment= new MiCuentaFragment();
                        break;

                    case R.id.navigation_item_crear_combate:
                        fragment= new CombateRecyclerFragment();
                        break;
                    case R.id.navigation_item_notifications:
                        fragment = new NotificacionesFragment();
                        break;
                }
                RemplazarFragment(fragment, false);
                return true;
            }




        });

        navigationView.setCheckedItem(R.id.micuenta);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.logout:
                FirebaseAuth.getInstance().signOut();

                FirebaseUtilsV1.SET_TOKEN(null);


                Intent intent = new Intent(this, LoginActivity.class);

                FirebaseUtilsV1.Borrar();
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuoptions, menu);

        return true;
    }


    @Override
    public void onSelectedItemListener(int str, Fragment f) {

        if (f instanceof ComunicateToTabsListener) {

            for (Fragment f1 : fragmentManager.getFragments()) {
                if (f1.getClass() == FichaPersonajeFragment.class) {

                    ComunicateToTabsListener inter = (ComunicateToTabsListener) f;
                    inter.onClickParentTab();
                    break;
                }

            }
        }


    }

    public void RemplazarFragment(Fragment fragment, boolean apilar) {

       // fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (apilar) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    public Fragment CurrentFragment() {
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if (fragments != null) {
            for (Fragment fragment : fragments) {
                if (fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseUtilsV1.Borrar();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        tarea.cancel(true);
    }


}


