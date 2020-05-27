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
import android.graphics.Bitmap;
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

//Actividad principal es la que se encarga de gestionar los Fragments de la aplicacion
public class MainActivity extends Actividad implements onSelectedItemListener {


    //Manejador de los fragmentos
    FragmentManager fragmentManager;
    //Objeto que maneja las transiciones de los fragmentos
    FragmentTransaction fragmentTransaction;
    //Conetendor del menu lateral
    DrawerLayout drawerLayout;
    //Barra Tollbar
    Toolbar toolbar;
    //Representa un menu de navegacion estandar
    NavigationView navigationView;

    //Interfaz que escucha cuando una tarea se ha completado
    private OnTaskCompleted listener;
    //Tarea
    AsynTarea tarea;

    ImageView imagen;
    TextView nombre;



    //Evento que ocurre al iniciar esta actividad
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inicializacion Componentes
        toolbar = findViewById(R.id.toolbar);
        toolbar.setVisibility(View.GONE);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        //Al inicio de la aplicacion cerramos el menu lateral porque primero se reproducira la animacion de carga
        drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        //El listener OnTask lo inicializamos a nulo
        listener=null;
        //Sobrescribimos su metodo , cuando el listener se dispare abriremos el drawer layout y mostraremos el toolbar
        listener = new OnTaskCompleted() {
            @Override
            public void onTaskCompleted() {

                drawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNDEFINED);
                toolbar.setVisibility(View.VISIBLE);
                //Llamaremos al metodo CrearMenus y le pasaremos el tipo de menu principal
                CrearMenus(R.menu.principalmenu);

            }
            @Override
            public void onTaskFailure() {

            }
        };

        //inicializamos la tarea a nula y le pasamos el fragmentmanager y el listener
        tarea = null;
        try {
            tarea = new AsynTarea(listener, fragmentManager);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //Ejecutamos la Tarea
        tarea.execute();
        //Intentamos obtener los datos del usuario principales el Nombre y su Foto
        FirebaseUtilsV1.GetDatosUsuario();



    }

    public void CambiarDatosMenu(String nombre, Bitmap imagen){
        if(this.nombre !=null&& this.imagen!=null){
            this.nombre.setText(nombre);
            this.imagen.setImageBitmap(imagen);
        }


    }
    //Metodo que crea el menu lateral
    private void CrearMenus(int tipomenu) {
        //Inicializamos el servicio que escucha el cambio de usuario
        startService(new Intent(getApplicationContext(), ListenUserService.class));

        //Inicializamos la toolbar y le añadimos el simbolo del menu
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setHomeAsUpIndicator(R.drawable.menu);
        actionBar.setDisplayHomeAsUpEnabled(true);

        //Creamos el primer Fragment que se va a ver
        Fragment fragment = new MiCuentaFragment();
        //Llamamos al metodo remplazar fragment y le decimos que no lo vamos a apilar
        RemplazarFragment(fragment, false);
        //Inflamos el tipo de menu el el navigationView
        navigationView.inflateMenu(tipomenu);


        //Inicializamos el Nombre y la foto que se muestra en el menu
        View hView = navigationView.getHeaderView(0);
        nombre = (TextView) hView.findViewById(R.id.identiftextView);
         imagen = hView.findViewById(R.id.usuarioimagen);

        String apodo = FirebaseUtilsV1.getDatosUser().getNombre();
        String foto= FirebaseUtilsV1.getDatosUser().getFoto();
        nombre.setText(apodo);
        imagen.setImageBitmap(ConversorImagenes.convertirStringBitmap(foto));


        //Escuchamos las distintas respuestas que se pueden dar al seleccionar una de las opciones del menu
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
        //Al principio de la app la primea opcion seleccionada sera la de micuenta
        navigationView.setCheckedItem(R.id.micuenta);
    }

    //Detecta las opciones del toolbar
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case android.R.id.home:
                //Abre el menu lateral manualmente
                drawerLayout.openDrawer(GravityCompat.START);
                break;
            case R.id.logout:
                //Sale de la aplicacion

                //El atributo token de la base de datos se pone a nulo
                FirebaseUtilsV1.SET_TOKEN(null);
                //Nos deslogueamos en firebase
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(this, LoginActivity.class);

                //Se borran los datos del usuario temporales de la app
                FirebaseUtilsV1.Borrar();
                //Se vuelve atras
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                finish();

                break;

        }
        return super.onOptionsItemSelected(item);
    }

    //Añadimos en el toolbar el logout
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menuoptions, menu);

        return true;
    }


    //Recibe datos del Fragment actual

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

    //Remplaza un fragment por otro tiene la opcion de apilar un booleano que sirve para saber si el antiguo fragment se queda detras
    public void RemplazarFragment(Fragment fragment, boolean apilar) {

       // fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        if (apilar) fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

//Al destruir esta actividad se borran los datos del usuario temporal
    @Override
    protected void onDestroy() {
        super.onDestroy();
        FirebaseUtilsV1.Borrar();

    }

    //Al salir de la aplicacion la tarea asincrona se cancela para que la aplicacion no pete
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        tarea.cancel(true);
    }


}


