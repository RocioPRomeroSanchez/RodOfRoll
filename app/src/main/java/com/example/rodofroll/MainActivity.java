package com.example.rodofroll;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
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
import com.example.rodofroll.Objetos.ComunicateToTabsListener;
import com.example.rodofroll.Objetos.Dialogos;
import com.example.rodofroll.Objetos.MisMetodos;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.Objetos.onSelectedItemListener;
import com.example.rodofroll.Vistas.CombateFragment;
import com.example.rodofroll.Vistas.DadosFragment;
import com.example.rodofroll.Vistas.RecyclerViewFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;


public class MainActivity extends AppCompatActivity implements onSelectedItemListener {


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    DrawerLayout drawerLayout;
    File personajes;
    File monstruos;
    public ImageView remp;


    private static int PHOTO_RESULT=0,PICK_IMAGE=1 ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        FireBaseUtils.CrearRef();
        String notifcaciones = FireBaseUtils.getUser().getUid()+"notif";
        FirebaseMessaging.getInstance().subscribeToTopic(notifcaciones);

        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();


        int i = getIntent().getIntExtra("rol",0);

        if ((i == 0)) {
            CrearMenus(R.menu.mastermenu,i);

        } else {
            CrearMenus(R.menu.jugadormenu,i);

        }


    }

    public void AnyadirCombatiente(Personaje c) {


        if(c instanceof Personaje){

            FireBaseUtils.getRef().child(FireBaseUtils.getUser().getUid()).child("personajes").push().setValue(c.Map());

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



    public void CrearMenus(int tipomenu, int defecto){
        Toolbar toolbar = findViewById(R.id.toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);


        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        actionBar.setDisplayHomeAsUpEnabled(true);


        Fragment fragment= new RecyclerViewFragment();
        RemplazarFragment(fragment,false);



        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.inflateMenu(tipomenu);
        View hView = navigationView.getHeaderView(0);
        TextView correo = (TextView) hView.findViewById(R.id.identiftextView);
        correo.setText(FireBaseUtils.getUser().getDisplayName());


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

    public Fragment CurrentFragment(){
        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
        List<Fragment> fragments = fragmentManager.getFragments();
        if(fragments != null){
            for(Fragment fragment : fragments){
                if(fragment != null && fragment.isVisible())
                    return fragment;
            }
        }
        return null;


    }


    public void MenuEmergenteImagen(final ImageView view){
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
        popupMenu.getMenuInflater().inflate(R.menu.menuimagen,popupMenu.getMenu());


        remp=view;

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.opfoto:
                        Intent intento = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intento,PHOTO_RESULT);
                        break;
                    case R.id.opgaleria:
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent.createChooser(galleryIntent, "Seleccione la aplicacion"), PICK_IMAGE);


                        break;
                    case R.id.opborrar:

                        view.setImageResource(R.drawable.mago);
                        break;

                }

                return true;
            }
        });
        popupMenu.show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap resultado;

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_RESULT && resultCode == RESULT_OK) {

            if(data.hasExtra("data"))
            {
                resultado = ((Bitmap) data.getParcelableExtra("data"));
                remp.setImageBitmap(resultado);

            }

        }
        if(requestCode==PICK_IMAGE&&resultCode==RESULT_OK){

            Uri selectedImage = data.getData();
            resultado= MisMetodos.getScaledBitmap(selectedImage,this);
            remp.setImageBitmap(resultado);
        }


    }


}
