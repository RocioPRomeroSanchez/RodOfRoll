package com.example.rodofroll.Objetos;

import android.os.AsyncTask;
import android.widget.Toast;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.rodofroll.Firebase.FireBaseUtils;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Fragments.CargandoFragment;

public class AsynTarea extends AsyncTask<Void,Void,Void> {

    FragmentManager fm;
    CargandoFragment fragment;
    boolean fin = false;
    private OnTaskCompleted listener;
    long inicio;




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
        inicio=System.currentTimeMillis();

    }

    @Override
    protected Void doInBackground(Void... voids) {
      // FireBaseUtils.CrearRef();
// Math.abs(( new Timestamp(System.currentTimeMillis()).getTime() - inicio.getTime())) < 1)

        double tiempo;
        do{
             tiempo = (double) ((System.currentTimeMillis() - inicio)/1000);

        }while (!FireBaseUtils.isEstado()&&tiempo<20);

        if(tiempo>=20){
            Toast.makeText(fragment.getContext(),"Reinicie la aplicación",Toast.LENGTH_LONG).show();

        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        return null;

    }

}
