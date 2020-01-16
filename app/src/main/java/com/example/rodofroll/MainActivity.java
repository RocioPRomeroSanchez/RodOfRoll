package com.example.rodofroll;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.rodofroll.Objetos.Combatiente;
import com.example.rodofroll.Objetos.ComunicateToTabsListener;
import com.example.rodofroll.Objetos.Dialogos;
import com.example.rodofroll.Objetos.Monstruo;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.Objetos.onSelectedItemListener;
import com.example.rodofroll.Vistas.CombateFragment;
import com.example.rodofroll.Vistas.DadosFragment;
import com.example.rodofroll.Vistas.FichaPersonajeFragment;
import com.example.rodofroll.Vistas.RecyclerViewFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Collections;

import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;

public class MainActivity extends AppCompatActivity implements onSelectedItemListener {


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    DrawerLayout drawerLayout;
    File fmontruos;
    File fpersonajes;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child(user.getUid()).child("Combates").setValue("rosa");*/


   /*   FireBaseUtils.CrearRef();
      String notifcaciones = FireBaseUtils.getUser().getUid()+"notif";
      FirebaseMessaging.getInstance().subscribeToTopic(notifcaciones);
*/
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        int i = getIntent().getIntExtra("rol",0);

        if ((i == 0)) {
            CrearMenus(R.menu.mastermenu,i);

        } else {
            CrearMenus(R.menu.jugadormenu,i);

        }
        fpersonajes= getDir("personajes.json", Context.MODE_PRIVATE);

        fmontruos=getDir("monstruos.json", Context.MODE_PRIVATE);

    }

    public void AnyadirCombatiente(Combatiente c){
        JSONObject jo;
        JsonWriter jw;
        if(c instanceof Personaje){
           jo = (JSONObject) ((Personaje) c).ToJson();
          //  jw.write(jo);
        }
        else if(c instanceof Monstruo){
            jo = (JSONObject) ((Personaje) c).ToJson();
        }

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CrearMenus(int tipomenu, int defecto){
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        actionBar.setDisplayHomeAsUpEnabled(true);


        Fragment fragment= new RecyclerViewFragment();

        RemplazarFragment(fragment,false);



        drawerLayout=findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.inflateMenu(tipomenu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                Fragment fragment= new Fragment();

                switch (menuItem.getItemId()){

                    case R.id.navigation_item_personajes:
                        fragment=new RecyclerViewFragment();

                        break;
                    case R.id.navigation_item_dados:
                        fragment=new DadosFragment();

                        break;
                    case R.id.navigation_item_combat:
                        fragment=new CombateFragment();
                        break;

                    case R.id.navigation_item_moster:
                        fragment=new RecyclerViewFragment();

                        break;


                }

                RemplazarFragment(fragment,false);

                return  true;

            }


        });



    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.opcionuser:

                Dialogos.showDialogoRol(this,this);

                break;



        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menuoptions,menu);



        return  true;
    }



    @Override
    public void onSelectedItemListener(int str, Fragment f) {

        if(f instanceof ComunicateToTabsListener ){

            for(Fragment f1 : fragmentManager.getFragments()){
                if(f1.getClass() == f.getClass()){

                    ComunicateToTabsListener inter = (ComunicateToTabsListener) f1;
                    inter.onClickParentTab();
                    break;
                }

            }
        }


    }

    public void RemplazarFragment(Fragment fragment,boolean apilar){

        fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction=fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container,fragment);
        if(apilar) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }




}
