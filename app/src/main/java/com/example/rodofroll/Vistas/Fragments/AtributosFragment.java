package com.example.rodofroll.Vistas.Fragments;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.rodofroll.Firebase.FirebaseUtilsV1;
import com.example.rodofroll.MainActivity;
import com.example.rodofroll.Objetos.ComunicateToTabsListener;

import com.example.rodofroll.Vistas.Dialogos.DialogoCambiarDatos;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;

import androidx.arch.core.util.Function;


public class AtributosFragment extends Fragment  implements ComunicateToTabsListener , View.OnClickListener {


    View v;
    Personaje p;

    //Vistas
    TextView vidaTextView;
    TextView caTextView;
    TextView iniciativaTextView;
    TextView ataquebaseTextView;
    TextView velocidadTextView;
    TextView fuerzaTextView;
    TextView destrezaTextView;
    TextView concentracionTextView;
    TextView inteligenciaTextView;
    TextView sabiduriaTextView;
    TextView carismaTextView;
    TextView fortalezaTextView;
    TextView reflejosTextView;
    TextView voluntadTextView;
    ImageView vidaImageView;
    ImageView caImageView;
    LinearLayout iniciativaLayout;
    LinearLayout ataquebaseLayout;
    LinearLayout velocidadLayout;
    LinearLayout fuerzaLayout;
    LinearLayout destrezaLayout;
    LinearLayout concentracionLayout;
    LinearLayout inteligenciaLayout;
    LinearLayout sabiduriaLayout;
    LinearLayout carismaLayout;
    LinearLayout fortalezaLayout;
    LinearLayout reflejosLayout;
    LinearLayout voluntadLayout;

    public AtributosFragment(Personaje p){
        this.p=p;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.atributos_layout, container, false);

        vidaTextView= v.findViewById(R.id.VidaTextView);
        caTextView = v.findViewById(R.id.CATextView);
        iniciativaTextView = v.findViewById(R.id.IniciativaTextView);
        ataquebaseTextView = v.findViewById(R.id.AtaqueBaseTextView);
        velocidadTextView = v.findViewById(R.id.VelocidadTextView);
        fuerzaTextView = v.findViewById(R.id.FuerzaTextView);
        destrezaTextView = v.findViewById(R.id.DestrezaTextView);
        concentracionTextView = v.findViewById(R.id.ConcentracionTextView);
        inteligenciaTextView = v.findViewById(R.id.InteligenciaTextView);
        sabiduriaTextView = v.findViewById(R.id.SabiduriaTextView);
        carismaTextView =v.findViewById(R.id.CarismaTextView);
        fortalezaTextView = v.findViewById(R.id.FortalezaTextView);
        reflejosTextView = v.findViewById(R.id.ReflejosTextView);
        voluntadTextView = v.findViewById(R.id.VoluntadTextView);

        vidaImageView = v.findViewById(R.id.vidaimageview);
        caImageView = v.findViewById(R.id.caimageview);
        iniciativaLayout = v.findViewById(R.id.iniciativalayout);
        ataquebaseLayout = v.findViewById(R.id.ataquebaselayout);
        velocidadLayout= v.findViewById(R.id.velocidadlayout);
        fuerzaLayout= v.findViewById(R.id.fuerzalayout);
        destrezaLayout= v.findViewById(R.id.destrezalayout);
        concentracionLayout=v.findViewById(R.id.concentracionlayout);
        inteligenciaLayout=v.findViewById(R.id.inteligencialayout);
        sabiduriaLayout= v.findViewById(R.id.sabidurialayout);
        carismaLayout=v.findViewById(R.id.carismalayout);
        fortalezaLayout= v.findViewById(R.id.fortalezalayout);
        reflejosLayout=v.findViewById(R.id.reflejoslayout);
        voluntadLayout=v.findViewById(R.id.voluntadlayout);

        vidaImageView.setOnClickListener(this);
        caImageView.setOnClickListener(this);
        iniciativaLayout.setOnClickListener(this);
        ataquebaseLayout.setOnClickListener(this);
        velocidadLayout.setOnClickListener(this);
        fuerzaLayout.setOnClickListener(this);
        destrezaLayout.setOnClickListener(this);
        concentracionLayout.setOnClickListener(this);
        inteligenciaLayout.setOnClickListener(this);
        sabiduriaLayout.setOnClickListener(this);
        carismaLayout.setOnClickListener(this);
        fortalezaLayout.setOnClickListener(this);
        reflejosLayout.setOnClickListener(this);
        voluntadLayout.setOnClickListener(this);



        InicializarDatos(p);

        vidaTextView.setOnClickListener(this);

        return v;
    }

    @SuppressLint("DefaultLocale")
    public void InicializarDatos(Personaje p){
        vidaTextView.setText(String.format("%.0f",p.getVida()));
        caTextView.setText(String.format("%.0f",p.getArmadura()));
        iniciativaTextView.setText(String.format("%.0f",p.getModiniciativa()));
        ataquebaseTextView.setText(String.format("%.0f",p.getAtaque()));
        velocidadTextView.setText(String.format("%.0f",p.getVelocidad()));

        fuerzaTextView.setText(String.format("%.0f",p.getFuerza()));
        destrezaTextView.setText(String.format("%.0f",p.getDestreza()));
        concentracionTextView.setText(String.format("%.0f",p.getConcentracion()));
        inteligenciaTextView.setText(String.format("%.0f",p.getInteligencia()));
        sabiduriaTextView.setText(String.format("%.0f",p.getSabiduria()));
        carismaTextView.setText(String.format("%.0f",p.getCarisma()));

        fortalezaTextView.setText(String.format("%.0f",p.getFortaleza()));
        reflejosTextView.setText(String.format("%.0f",p.getReflejos()));
        voluntadTextView.setText(String.format("%.0f",p.getVoluntad()));
    }


    @Override
    public void onClickParentTab() {
        Toast.makeText(getContext(),"Hola",Toast.LENGTH_LONG).show();
        }


    public void onClick(View v) {
        DialogoCambiarDatos dialogoDatos;
        Function f;

        switch (v.getId()){
            case R.id.vidaimageview:

              f = CrearFuncion("vida",p);
              dialogoDatos= DialogoCambiarDatos.newInstance(vidaTextView,900,f,getActivity(),true);
                dialogoDatos.show(getFragmentManager(),"vida");

               break;


            case R.id.caimageview:

                f = CrearFuncion("armadura",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(caTextView,900,f,getActivity(),true);
                dialogoDatos.show(getFragmentManager(),"armadura");

                break;

           case R.id.iniciativalayout:

                f= CrearFuncion("iniciativa",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(iniciativaTextView,100,f,getActivity(),true);
                dialogoDatos.show(getFragmentManager(),"iniciativa");


                break;

            case R.id.ataquebaselayout:

                f= CrearFuncion("ataque",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(ataquebaseTextView,100,f,getActivity(),true);
                dialogoDatos.show(getFragmentManager(),"ataque");

                break;

           case R.id.velocidadlayout:

               f= CrearFuncion("velocidad",p);
               dialogoDatos= DialogoCambiarDatos.newInstance(velocidadTextView,100,f,getActivity(),true);
               dialogoDatos.show(getFragmentManager(),"velocidad");

                break;
            case R.id.fuerzalayout:
                f= CrearFuncion("fuerza",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(fuerzaTextView,900,f,getActivity(),true);
                dialogoDatos.show(getFragmentManager(),"fuerza");

                break;
            case R.id.destrezalayout:
                f=CrearFuncion("destreza",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(destrezaTextView,900,f,getActivity(),true);
                dialogoDatos.show(getFragmentManager(),"destreza");

                break;

            case R.id.concentracionlayout:
                f=CrearFuncion("concentracion",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(concentracionTextView,900,f,getActivity(),true);
                dialogoDatos.show(getFragmentManager(),"concentracion");

                break;

            case R.id.inteligencialayout:
                f=CrearFuncion("inteligencia",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(inteligenciaTextView,900,f,getActivity(),true);
                dialogoDatos.show(getFragmentManager(),"inteligencia");

                break;

            case R.id.sabidurialayout:
                f=CrearFuncion("sabiduria",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(sabiduriaTextView,900,f,getActivity(),true);
                dialogoDatos.show(getFragmentManager(),"sabiduria");


                break;

            case R.id.carismalayout:
                f=CrearFuncion("carisma",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(carismaTextView,900,f,getActivity(),true);
                dialogoDatos.show(getFragmentManager(),"carisma");
                break;

            case R.id.fortalezalayout:
                f=CrearFuncion("fortaleza",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(fortalezaTextView,900,f,getActivity(),true);
                dialogoDatos.show(getFragmentManager(),"fortaleza");

                break;

            case R.id.reflejoslayout:
                f=CrearFuncion("reflejos",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(reflejosTextView,900,f,getActivity(),true);
                dialogoDatos.show(getFragmentManager(),"reflejos");

                break;

           case  R.id.voluntadlayout:
               f=CrearFuncion("voluntad",p);
               dialogoDatos= DialogoCambiarDatos.newInstance(voluntadTextView,900,f,getActivity(),true);
               dialogoDatos.show(getFragmentManager(),"voluntad");

                break;
        }




    }

    public Function CrearFuncion(final String atributo, final Personaje p){

        Function function = new Function() {

            @Override
            public Object apply(Object input) {
                FirebaseUtilsV1.SET_Atributo(atributo,(double)input,p);

                return null;
            }
        };

        return  function;
    }
}





