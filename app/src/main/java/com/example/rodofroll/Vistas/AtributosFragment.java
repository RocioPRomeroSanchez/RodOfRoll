package com.example.rodofroll.Vistas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.rodofroll.MainActivity;
import com.example.rodofroll.Objetos.ComunicateToTabsListener;
import com.example.rodofroll.Objetos.DialogoDatos;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;


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


    @Override
    public void onClick(View v) {
        DialogoDatos dialogoDatos;
        switch (v.getId()){
            case R.id.vidaimageview:

              dialogoDatos= DialogoDatos.newInstance(vidaTextView,p,"Vida",900,getActivity());
                dialogoDatos.show(getFragmentManager(),"Vida");

                break;
            case R.id.caimageview:

                dialogoDatos= DialogoDatos.newInstance(caTextView,p,"Armadura",900,getActivity());
                dialogoDatos.show(getFragmentManager(),"Armadura");

                break;

            case R.id.iniciativalayout:
                dialogoDatos= DialogoDatos.newInstance(iniciativaTextView,p,"Iniciativa",100,getActivity());
                dialogoDatos.show(getFragmentManager(),"Iniciativa");

                break;

            case R.id.ataquebaselayout:
                dialogoDatos= DialogoDatos.newInstance(ataquebaseTextView,p,"Ataque",100,getActivity());
                dialogoDatos.show(getFragmentManager(),"Ataque");
                break;

            case R.id.velocidadlayout:
                dialogoDatos= DialogoDatos.newInstance(velocidadTextView,p,"Velocidad",100,getActivity());
                dialogoDatos.show(getFragmentManager(),"Velocidad");
                break;
            case R.id.fuerzalayout:
                dialogoDatos= DialogoDatos.newInstance(fuerzaTextView,p,"Fuerza",900,getActivity());
                dialogoDatos.show(getFragmentManager(),"Fuerza");
                break;
            case R.id.destrezalayout:
                dialogoDatos= DialogoDatos.newInstance(destrezaTextView,p,"Destreza",900,getActivity());
                dialogoDatos.show(getFragmentManager(),"Destreza");
                break;

            case R.id.concentracionlayout:
                dialogoDatos= DialogoDatos.newInstance(concentracionTextView,p,"Concentracion",900,getActivity());
                dialogoDatos.show(getFragmentManager(),"Concentracion");
                break;

            case R.id.inteligencialayout:
                dialogoDatos= DialogoDatos.newInstance(inteligenciaTextView,p,"Inteligencia",900,getActivity());
                dialogoDatos.show(getFragmentManager(),"Inteligencia");
                break;

            case R.id.sabidurialayout:
                dialogoDatos= DialogoDatos.newInstance(sabiduriaTextView,p,"Sabiduria",900,getActivity());
                dialogoDatos.show(getFragmentManager(),"Sabiduria");
                break;

            case R.id.carismalayout:
                dialogoDatos= DialogoDatos.newInstance(carismaTextView,p,"Carisma",900,getActivity());
                dialogoDatos.show(getFragmentManager(),"Carisma");
                break;

            case R.id.fortalezalayout:
                dialogoDatos= DialogoDatos.newInstance(fortalezaTextView,p,"Fortaleza",900,getActivity());
                dialogoDatos.show(getFragmentManager(),"Fortaleza");
                break;

            case R.id.reflejoslayout:
                dialogoDatos= DialogoDatos.newInstance(reflejosTextView,p,"Reflejos",900,getActivity());
                dialogoDatos.show(getFragmentManager(),"Reflejos");
                break;

            case  R.id.voluntadlayout:
                dialogoDatos= DialogoDatos.newInstance(voluntadTextView,p,"Voluntad",900,getActivity());
                dialogoDatos.show(getFragmentManager(),"Voluntad");
                break;
        }

    }
}





