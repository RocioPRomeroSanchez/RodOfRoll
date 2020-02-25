package com.example.rodofroll;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rodofroll.Firebase.FireBaseUtils;
import com.example.rodofroll.Objetos.AsynTarea;
import com.example.rodofroll.Objetos.ComunicateToTabsListener;
import com.example.rodofroll.Objetos.Dialogos;
import com.example.rodofroll.Objetos.MisMetodos;
import com.example.rodofroll.Objetos.OnTaskCompleted;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.Objetos.Usuario;
import com.example.rodofroll.Objetos.onSelectedItemListener;
import com.example.rodofroll.Vistas.Actividad;
import com.example.rodofroll.Vistas.AnimacionFragment;
import com.example.rodofroll.Vistas.BuscarCombateFragment;
import com.example.rodofroll.Vistas.CombateFragment;
import com.example.rodofroll.Vistas.DadosFragment;
import com.example.rodofroll.Vistas.MiCuentaFragment;
import com.example.rodofroll.Vistas.RecyclerViewFragment;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


public class MainActivity extends Actividad implements onSelectedItemListener {





    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    DrawerLayout drawerLayout;
    File personajes;
    File monstruos;
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


        /*
        String notifcaciones = FireBaseUtils.getUser().getUid()+"notif";
        FirebaseMessaging.getInstance().subscribeToTopic(notifcaciones);*/

        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        listener=null;




        listener = new OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {

                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);

                toolbar.setVisibility(View.VISIBLE);
                Intent intent = getIntent();

                int i = intent.getIntExtra("rol", 0);

                if ((i == 0)) {
                    CrearMenus(R.menu.mastermenu, i);

                } else {
                    CrearMenus(R.menu.jugadormenu, i);

                }




            }

            @Override
            public void onTaskFailure() {

            }
        };


        AsynTarea tarea = null;
        try {
            tarea = new AsynTarea(listener, fragmentManager);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        tarea.execute();

        FireBaseUtils.GetDatosUsuario();



    }





    public void AnyadirCombatiente(Personaje c) {


        if (c instanceof Personaje) {

            FireBaseUtils.getRef().child("usuarios").child(FireBaseUtils.getUser().getUid()).child("personajes").push().setValue(c.Map());

        }


     /*   if(c instanceof Personaje){
            boolean b = personajes.exists();
            //EscribirFichero(personajes,((Personaje) c).ToJson());

        }
        else if(c instanceof Monstruo){
        }
        */
    }
/*
    public void LeerFichero(File file){

    }
    public void EscribirFichero(File file, JSONObject jsonObject) throws IOException {
        FileWriter fileWriter = new FileWriter(personajes);
        fileWriter.write(jsonObject.toString());
        fileWriter.close();
    }*/


    public void CrearMenus(int tipomenu, int defecto) {
        Toolbar toolbar = findViewById(R.id.toolbar);


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        actionBar.setDisplayHomeAsUpEnabled(true);


        Fragment fragment = new RecyclerViewFragment();
        RemplazarFragment(fragment, false);


        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.inflateMenu(tipomenu);


        View hView = navigationView.getHeaderView(0);
        TextView correo = (TextView) hView.findViewById(R.id.identiftextView);
        ImageView imagen = hView.findViewById(R.id.usuarioimagen);


        String apodo = FireBaseUtils.getDatosUser().getNombre();
         String foto= FireBaseUtils.getDatosUser().getFoto();
         correo.setText(apodo);
          imagen.setImageBitmap(MisMetodos.convertirStringBitmap(foto));


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                Fragment fragment = new Fragment();

                switch (menuItem.getItemId()) {

                    case R.id.navigation_item_personajes:
                        fragment = new RecyclerViewFragment();

                        break;
                    case R.id.navigation_item_dados:
                        fragment = new DadosFragment();

                        break;


                    case R.id.navigation_item_moster:
                        fragment = new RecyclerViewFragment();

                        break;

                    case R.id.navigation_item_buscar_combate:
                        fragment = new BuscarCombateFragment();
                        break;

                    case R.id.micuenta:
                        fragment= new MiCuentaFragment();
                        break;
                }
                RemplazarFragment(fragment, false);
                return true;
            }


        });
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

                Intent intent = new Intent(this, LoginActivity.class);
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
                if (f1.getClass() == f.getClass()) {

                    ComunicateToTabsListener inter = (ComunicateToTabsListener) f1;
                    inter.onClickParentTab();
                    break;
                }

            }
        }


    }

    public void RemplazarFragment(Fragment fragment, boolean apilar) {

        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
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

        FireBaseUtils.Borrar();

    }
}


