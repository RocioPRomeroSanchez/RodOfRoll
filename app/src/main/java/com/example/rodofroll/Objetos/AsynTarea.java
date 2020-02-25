package com.example.rodofroll.Objetos;

import android.os.AsyncTask;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.rodofroll.Firebase.FireBaseUtils;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.CargandoFragment;
import com.google.android.gms.tasks.Task;

import java.sql.Time;
import java.sql.Timestamp;

public class AsynTarea extends AsyncTask<Void,Void,Void> {

    FragmentManager fm;
    CargandoFragment fragment;
    boolean fin = false;
    private OnTaskCompleted listener;



    public AsynTarea(OnTaskCompleted listener,FragmentManager fm) throws InterruptedException {
        this.fm=fm;
        this.listener=listener;



    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (listener != null) {
            listener.onTaskCompleted();



        }
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        fragment= new CargandoFragment();

        FragmentTransaction fragmentTransaction = fm.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container,fragment);

        fragmentTransaction.commit();

    }

    @Override
    protected Void doInBackground(Void... voids) {


      // FireBaseUtils.CrearRef();
// Math.abs(( new Timestamp(System.currentTimeMillis()).getTime() - inicio.getTime())) < 1)

        while (FireBaseUtils.isEstado()){

        };

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return null;

    }

}
