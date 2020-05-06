package com.example.rodofroll.Vistas.Fragments;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
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
import com.example.rodofroll.Objetos.Combatiente;
import com.example.rodofroll.Objetos.ComunicateToTabsListener;
import com.example.rodofroll.Objetos.ConversorImagenes;
import com.example.rodofroll.Objetos.InicializarVistas;
import com.example.rodofroll.Objetos.Monstruo;
import com.example.rodofroll.Objetos.OnTaskCompleted;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Dialogos.DialogoCambiarDatos;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DatabaseReference;

import java.util.EventObject;
import java.util.Objects;

public class BiografiaFragment extends Fragment implements View.OnClickListener,EstructuraFragment,ComunicateToTabsListener, OnTaskCompleted {

    View v;
    Combatiente p;
    ImageView imagen;
    Spinner spinner;
    EditText editText;
    ScrollView scrollView;
    ProgressBar progressBar;
    Actividad actividad;
    TextView expTextView;
    TextView exptopeTextView;
    TextView levelTextView;
    EditText nombreEditText;
    EditText claseEditText;
    EditText razaEditText;
    EditText descripcionEditText;
    DatabaseReference reference;



    public BiografiaFragment(Combatiente p, Actividad activity){
        this.p= p;
        this.actividad=activity;
        if(p instanceof Monstruo){
            reference =FirebaseUtilsV1.GET_RefMonstruo(FirebaseUtilsV1.getKey(),p.getKey());
        }
        else {
            reference =FirebaseUtilsV1.GET_RefPersonaje(FirebaseUtilsV1.getKey(),p.getKey());
        }
    }

    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.biografia_layout, container, false);
        InicializarComponentes(v);
        AsociarPojo();
        Scroll();




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

           reference.child("biografia").child("imagen").setValue(ConversorImagenes.convertirImagenString(((BitmapDrawable)imagen.getDrawable()).getBitmap()));

    }

    @Override
    public void onTaskFailure() {

    }

    @Override
    public void InicializarComponentes(View view) {

        imagen= view.findViewById(R.id.ImagenImageView);

        editText=view.findViewById(R.id.DescripcionEditText);
        scrollView=view.findViewById(R.id.scrollview);
        progressBar=view.findViewById(R.id.progressbarexp);
        expTextView = view.findViewById(R.id.ExpTextView);
        exptopeTextView = view.findViewById(R.id.ExpTopeTextView);
        nombreEditText = view.findViewById(R.id.NombreEditText);
        razaEditText = view.findViewById(R.id.RazaEditText);
        descripcionEditText = view.findViewById(R.id.DescripcionEditText);
        claseEditText = view.findViewById(R.id.ClaseEditText);

        levelTextView=view.findViewById(R.id.LevelTextView);

        imagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actividad.MenuEmergenteImagen(imagen,BiografiaFragment.this);

            }
        });


        spinner = (Spinner) view.findViewById(R.id.AlineamientoSpinner);


        levelTextView.setOnClickListener(this);
        exptopeTextView.setOnClickListener(this);


        nombreEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
              reference.child("biografia").child("nombre").setValue(nombreEditText.getText().toString());

            }
        });

        razaEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               reference.child("biografia").child("raza").setValue(razaEditText.getText().toString());
            }
        });
        claseEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
               reference.child("biografia").child("clase").setValue(claseEditText.getText().toString());
            }
        });

        descripcionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                reference.child("biografia").child("descripcion").setValue(descripcionEditText.getText().toString());
            }
        });


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                reference.child("biografia").child("alineamiento").setValue(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    public void AsociarPojo(){

        imagen.setImageBitmap(ConversorImagenes.convertirStringBitmap(p.getImagen()));
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_item, Personaje.getAlineamientos());
        spinner.setAdapter(arrayAdapter);
        levelTextView.setText(String.valueOf(p.getLevel()));
        nombreEditText.setText(p.getNombre());
        claseEditText.setText(p.getClase());
        razaEditText.setText(p.getRaza());
        descripcionEditText.setText(p.getDescripcion());
        spinner.setSelection(p.getAlineamiento());

    }

    @Override
    public void ComportamientoRecycler() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.LevelTextView:
                DialogoCambiarDatos.newInstance(levelTextView,100,CrearFuncion(),actividad,true).show(getFragmentManager(),"Level");
                break;

            case R.id.ExpTopeTextView:
                DialogoCambiarDatos.newInstance(exptopeTextView,100000000,CrearFuncionExpTope(),actividad,true).show(getFragmentManager(),"Experiecia");
                break;


        }

    }

    //Creamos una funcion que devuelve el objeto Function

    public Function CrearFuncion(){

        Function function = new Function() {

            @Override
            public Object apply(Object input) {

                reference.child("biografia").child("level").setValue((Math.round((double)input)));

                return null;
            }
        };

        return  function;
    }
    public Function CrearFuncionExpTope(){

        Function function = new Function() {

            @Override
            public Object apply(Object input) {

                reference.child("biografia").child("exptope").setValue((Math.round((double)input)));
                progressBar.setMax((int)(Math.round((double)input)));

                return null;
            }
        };

        return  function;
    }

}
