package com.example.rodofroll.Vistas.Fragments;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;

import com.example.rodofroll.Firebase.FirebaseUtilsV1;
import com.example.rodofroll.MainActivity;
import com.example.rodofroll.Objetos.ConversorImagenes;
import com.example.rodofroll.Objetos.Validacion;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Dialogos.Dialogos;
import com.google.android.gms.tasks.Task;

public class MiCuentaFragment extends Fragment implements View.OnClickListener {

    Button eliminarcuentabutton;
    Button modificarbutton;
    ImageView userimageView;
    EditText editText;

    @Nullable
    @Override
    //Sucede cuando creamos la vista
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.micuenta,container,false);


        userimageView = view.findViewById(R.id.UserimageView);
        userimageView.setOnClickListener(this);

        editText = view.findViewById(R.id.apodetext);

        editText.setText(FirebaseUtilsV1.getDatosUser().getNombre());
        userimageView.setImageBitmap(ConversorImagenes.convertirStringBitmap(FirebaseUtilsV1.getDatosUser().getFoto()));


        eliminarcuentabutton = view.findViewById(R.id.BorrarCuentabutton);
        eliminarcuentabutton.setOnClickListener(this);

        modificarbutton = view.findViewById(R.id.ModifButton);
        modificarbutton.setOnClickListener(this);

        return view;
    }

    @Override
    //Gestiona los eventos de los botones
    public void onClick(View v) {

        switch (v.getId()){
            case  R.id.BorrarCuentabutton:
                Function f = new Function() {
                    @Override
                    public Object apply(Object input) {
                        FirebaseUtilsV1.BorrarCuenta(getActivity());
                        return null;
                    }
                } ;

                Dialogos.showEliminar(null,getActivity(),null,f).show();



                break;

            case R.id.ModifButton:

                if(Validacion.ValidarEdit(editText)) {
                    FirebaseUtilsV1.setUserDetalls(editText.getText().toString(), ConversorImagenes.convertirImagenString(((BitmapDrawable) userimageView.getDrawable()).getBitmap()));
                    ((MainActivity) getActivity()).CambiarDatosMenu(editText.getText().toString(), ((BitmapDrawable) userimageView.getDrawable()).getBitmap());
                }

                break;

            case R.id.UserimageView:
                ((MainActivity)getActivity()).MenuEmergenteImagen(userimageView,null);

                break;
        }
    }
}
