package com.example.rodofroll.Vistas.Dialogos;

import android.app.Activity;
import android.app.Dialog;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;
import androidx.arch.core.util.Function;

public class DialogoCambiarDatos extends DialogFragment {

    /*TextView vista;
    String atributo;
    Activity activity;
    Function function;
    Object object;
    int limite;*/

    TextView vista;
    Object object;
    Function function;
    Activity activity;
    int limite;

    public static DialogoCambiarDatos newInstance(TextView view,int limite,Object object,Function function, Activity activity) {
        DialogoCambiarDatos dialogo = new DialogoCambiarDatos(view,limite,object,function,activity);
        return dialogo;
    }

    public DialogoCambiarDatos(TextView view,int limite,Object object, Function function,Activity activity){
        this.vista=view;
        this.limite=limite;
        this.activity=activity;
        this.object= object;
        this.function=function;
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View myView = inflater.inflate(R.layout.datosdialog_layout, null);
        final EditText editText = myView.findViewById(R.id.EditText);
        TextView textView = myView.findViewById(R.id.VistaTextView);
        Button cancelarButon = myView.findViewById(R.id.CancelarButon);
        Button aceptarButton = myView.findViewById(R.id.AceptarButton);

        if(vista!=null)
        editText.setText(vista.getText());


        cancelarButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        aceptarButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                String cadena = editText.getText().toString();
                if(cadena.isEmpty()){
                    editText.setError("No puede ser nulo");
                }
               else if(TextUtils.isDigitsOnly(cadena)) {
                   int numero = Integer.valueOf(cadena);

                    if (numero < limite) {
                        if(vista!=null){
                            vista.setText(String.valueOf(numero));
                        }

                            function.apply(numero);

                        dismiss();
                    }
                    else{
                        editText.setError("Tiene que ser menor de "+ limite);
                    }
                }

            }
        });
        builder.setView(myView);

        return builder.create();
    }
}
