package com.example.rodofroll;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.rodofroll.Vistas.CombateFragment;
import com.example.rodofroll.Vistas.DadosFragment;
import com.example.rodofroll.Vistas.RecyclerViewFragment;
import com.example.rodofroll.Firebase.FireBaseUtils;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

public class MainActivity extends AppCompatActivity {


    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    DrawerLayout drawerLayout;
    boolean personajescombate;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

      /*  FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child(user.getUid()).child("Combates").setValue("rosa");*/


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
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void CrearMenus(int tipomenu, int defecto){
        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        if(defecto==0){
            Fragment fragment= new RecyclerViewFragment();
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();

        }
        else{
            Fragment fragment= new RecyclerViewFragment();
            fragmentTransaction.replace(R.id.fragment_container,fragment);
            fragmentTransaction.commit();
        }



        drawerLayout=findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.navigation_view);
        navigationView.inflateMenu(tipomenu);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                menuItem.setChecked(true);
                drawerLayout.closeDrawers();
                Fragment fragment= new Fragment();


                fragmentTransaction=fragmentManager.beginTransaction();
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
                fragmentTransaction.replace(R.id.fragment_container,fragment);
                fragmentTransaction.commit();

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
               showAlertDialog();

                break;


        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
      getMenuInflater().inflate(R.menu.menuoptions,menu);



        return  true;
    }

    public void showAlertDialog(){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(MainActivity.this);
        LayoutInflater inflater = MainActivity.this.getLayoutInflater();
        final View myView = inflater.inflate(R.layout.elegirolayout, null);

        final Spinner spin = myView.findViewById(R.id.elecrolnspinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spin.setAdapter(adapter);

        Button button =myView.findViewById(R.id.aceptarrolbutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spin.getSelectedItemPosition()==0){
                    getIntent().putExtra("rol",0);
                    recreate();

                }
                else{
                    getIntent().putExtra("rol",1);
                    recreate();

                }
            }
        });
        dialogBuilder.setView(myView);
        dialogBuilder.show();
    }


}
