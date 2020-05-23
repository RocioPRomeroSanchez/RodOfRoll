package com.example.rodofroll.Objetos;

import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.rodofroll.Firebase.FirebaseUtilsV1;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Fragments.CargandoFragment;
import com.google.android.material.snackbar.Snackbar;
//Es la tarea que gestiona la recuperacion de los datos del usuario mientras muestra una pantalla de carga
public class AsynTarea extends AsyncTask<Void,Void,Void> {


    FragmentManager fm;
    CargandoFragment fragment;
    //Es ina interfaz que nos servira para determinar si se ha completado la recurperacion de los datos del usuario
    private OnTaskCompleted listener;
    //Es el tiempo en el que se inicio la tarea
    long inicio;


    //Le pasaremos la interfaz y el fragment manager desde la MainActivity
    public AsynTarea(OnTaskCompleted listener,FragmentManager fm) throws InterruptedException {

        this.fm=fm;
        this.listener=listener;

    }

    //Despues de terminar la tarea se llama al metodo de la interfaz
    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (listener != null) {
            listener.onTaskCompleted();

        }
    }

    //Antes de ejecutar la tarea creamos el fragment CargandoFragment y remplazamos el fragment anterior por este , guardamos el tiempo en el que se inicia la app
    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        fragment= new CargandoFragment();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container,fragment);

        fragmentTransaction.commit();
        inicio=System.currentTimeMillis();

    }

    //La tarea espera a que los datos del usuario tengan un estado no nulo
    @Override
    protected Void doInBackground(Void... voids) {
        double tiempo;
        //Sirve para visualizar el contador una sola vez
        int contador_info=0;
        do{
            //Se cuenta el tiempo duracion entre el inicial y el actual
             tiempo = (double) ((System.currentTimeMillis() - inicio)/1000);

             //Si por algun motivo se sobrepasa mas de 20 segnudos se muestra un snackbar informando al usuario de que pruebe a reiniciar la aplicacion
            if(tiempo>=20&&contador_info==0){
                Snackbar.make(fragment.getView(),"Pruebe a reiniciar la aplicación",Snackbar.LENGTH_LONG).show();
                contador_info++;

            }

        }while (!FirebaseUtilsV1.isEstado());

        //Sirve para mostrar la animacion de incio por lo menos durante 3 segundos
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return null;

    }

}
