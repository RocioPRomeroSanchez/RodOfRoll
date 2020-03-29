package com.example.rodofroll.Vistas.Fragments;

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

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.atributos_layout, container, false);

       FichaPersonajeFragment f = (FichaPersonajeFragment) ((MainActivity) getActivity()).CurrentFragment();
       p= f.getPersonaje();



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


      /*  Animation anim = new ScaleAnimation(
                1f, 1.03f, // Start and end values for the X axis scaling
                1f,1.03f // Start and end values for the Y axis scaling
        );
        anim.setFillAfter(true); // Needed to keep the result of the animation
        anim.setDuration(1500);
        anim.setRepeatCount(Animation.INFINITE);
        imageView.startAnimation(anim);*/



        return v;
    }

    public void InicializarDatos(Personaje p){
        vidaTextView.setText(String.valueOf(p.getVida()));
        caTextView.setText(String.valueOf(p.getArmadura()));
        iniciativaTextView.setText(String.valueOf(p.getModiniciativa()));
        ataquebaseTextView.setText(String.valueOf(p.getAtaque()));
        velocidadTextView.setText(String.valueOf(p.getVelocidad()));

        fuerzaTextView.setText(String.valueOf(p.getFuerza()));
        destrezaTextView.setText(String.valueOf(p.getDestreza()));
        concentracionTextView.setText(String.valueOf(p.getConcentracion()));
        inteligenciaTextView.setText(String.valueOf(p.getInteligencia()));
        sabiduriaTextView.setText(String.valueOf(p.getSabiduria()));
        carismaTextView.setText(String.valueOf(p.getCarisma()));

        fortalezaTextView.setText(String.valueOf(p.getFortaleza()));
        reflejosTextView.setText(String.valueOf(p.getReflejos()));
        voluntadTextView.setText(String.valueOf(p.getVoluntad()));
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

              f = CrearFuncion("Vida",p);
              dialogoDatos= DialogoCambiarDatos.newInstance(vidaTextView,900,p,f,getActivity());
                dialogoDatos.show(getFragmentManager(),"Vida");

               break;


            case R.id.caimageview:

                f = CrearFuncion("Armadura",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(caTextView,900,p,f,getActivity());
                dialogoDatos.show(getFragmentManager(),"Armadura");

                break;

           case R.id.iniciativalayout:

                f= CrearFuncion("Iniciativa",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(iniciativaTextView,100,p,f,getActivity());
                dialogoDatos.show(getFragmentManager(),"Iniciativa");


                break;

            case R.id.ataquebaselayout:

                f= CrearFuncion("Ataque",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(ataquebaseTextView,100,p,f,getActivity());
                dialogoDatos.show(getFragmentManager(),"Ataque");

                break;

           case R.id.velocidadlayout:

               f= CrearFuncion("Velocidad",p);
               dialogoDatos= DialogoCambiarDatos.newInstance(velocidadTextView,100,p,f,getActivity());
               dialogoDatos.show(getFragmentManager(),"Velocidad");

                break;
            case R.id.fuerzalayout:
                f= CrearFuncion("Fuerza",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(fuerzaTextView,900,p,f,getActivity());
                dialogoDatos.show(getFragmentManager(),"Fuerza");

                break;
            case R.id.destrezalayout:
                f=CrearFuncion("Destreza",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(destrezaTextView,900,p,f,getActivity());
                dialogoDatos.show(getFragmentManager(),"Destreza");

                break;

            case R.id.concentracionlayout:
                f=CrearFuncion("Concentracion",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(concentracionTextView,900,p,f,getActivity());
                dialogoDatos.show(getFragmentManager(),"Concentracion");

                break;

            case R.id.inteligencialayout:
                f=CrearFuncion("Inteligencia",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(inteligenciaTextView,900,p,f,getActivity());
                dialogoDatos.show(getFragmentManager(),"Inteligencia");

                break;

            case R.id.sabidurialayout:
                f=CrearFuncion("Sabiduria",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(sabiduriaTextView,900,p,f,getActivity());
                dialogoDatos.show(getFragmentManager(),"Sabiduria");


                break;

            case R.id.carismalayout:
                f=CrearFuncion("Carisma",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(caTextView,900,p,f,getActivity());
                dialogoDatos.show(getFragmentManager(),"Carisma");
                break;

            case R.id.fortalezalayout:
                f=CrearFuncion("Fortaleza",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(fortalezaTextView,900,p,f,getActivity());
                dialogoDatos.show(getFragmentManager(),"Fortaleza");

                break;

            case R.id.reflejoslayout:
                f=CrearFuncion("Reflejos",p);
                dialogoDatos= DialogoCambiarDatos.newInstance(reflejosTextView,900,p,f,getActivity());
                dialogoDatos.show(getFragmentManager(),"Reflejos");

                break;

           case  R.id.voluntadlayout:
               f=CrearFuncion("Voluntad",p);
               dialogoDatos= DialogoCambiarDatos.newInstance(voluntadTextView,900,p,f,getActivity());
               dialogoDatos.show(getFragmentManager(),"Voluntad");

                break;
        }




    }

    public Function CrearFuncion(final String atributo, final Personaje p){

        Function function = new Function() {

            @Override
            public Object apply(Object input) {

                try {

                    p.ModificarAtributosPersonaje(atributo.toLowerCase(),(int)input,p);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }
                return null;
            }
        };

        return  function;
    }
}





