package com.example.rodofroll.Vistas.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.arch.core.util.Function;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.example.rodofroll.Actividad;
import com.example.rodofroll.Firebase.FirebaseUtilsV1;
import com.example.rodofroll.Objetos.ComunicateToTabsListener;
import com.example.rodofroll.Objetos.ConversorImagenes;
import com.example.rodofroll.Objetos.InicializarVistas;
import com.example.rodofroll.Objetos.OnTaskCompleted;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.EventObject;
import java.util.Objects;

public class BiografiaFragment extends Fragment implements View.OnClickListener ,EstructuraFragment,ComunicateToTabsListener, OnTaskCompleted {

    View v;
    Personaje p;
    ImageView imagen;
    Spinner spinner;
    EditText editText;
    ScrollView scrollView;
    ProgressBar progressBar;
    Actividad actividad;
    TextView expTextView;
    TextView exptopeTextView;



    public BiografiaFragment(Personaje p, Actividad activity){
        this.p=p;
        this.actividad=activity;
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.biografia_layout, container, false);
        InicializarComponentes(v);


        return v;
    }


    @Override
    public void onClickParentTab() {
        Toast.makeText(getContext(),"Hola2",Toast.LENGTH_LONG).show();

    }

    public void Scroll() {
        View view = Objects.requireNonNull(getActivity()).getCurrentFocus();

        if(view!=null){
            if (view.getId()==R.id.DescripcionEditText) {
                InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                scrollView.scrollTo(0,scrollView.getBottom());
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

            }
        }

    }


    @Override
    public void onTaskCompleted() {
        FirebaseUtilsV1.GET_RefPersonaje(FirebaseUtilsV1.getKey(),p.getKey()).child("biografia").child("imagen").setValue(ConversorImagenes.convertirImagenString(((BitmapDrawable)imagen.getDrawable()).getBitmap()));
    }

    @Override
    public void onTaskFailure() {

    }

    @Override
    public void InicializarComponentes(View view) {
        imagen= view.findViewById(R.id.ImagenImageView);
        imagen.setImageBitmap(ConversorImagenes.convertirStringBitmap(p.getImagen()));
        editText=view.findViewById(R.id.DescripcionEditText);
        scrollView=view.findViewById(R.id.scrollview);
        progressBar=view.findViewById(R.id.progressbarexp);
        expTextView = view.findViewById(R.id.ExpTextView);
        exptopeTextView = view.findViewById(R.id.ExpTopeTextView);
        expTextView.setText(String.valueOf(p.getExp()));
        exptopeTextView.setText(String.valueOf(p.getExptope()));
        progressBar.setMax(p.getExptope());
        progressBar.setProgress(p.getExp());

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actividad.MenuEmergenteImagen(imagen,BiografiaFragment.this);

            }
        });


        spinner = (Spinner) view.findViewById(R.id.AlineamientoSpinner);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Personaje.getAlineamientos());

        Scroll();
        spinner.setAdapter(arrayAdapter);


    }

    @Override
    public void ComportamientoRecycler() {

    }

    @Override
    public void onClick(View v) {

    }
}
