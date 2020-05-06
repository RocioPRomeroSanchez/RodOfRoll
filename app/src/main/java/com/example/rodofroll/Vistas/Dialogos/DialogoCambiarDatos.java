package com.example.rodofroll.Vistas.Dialogos;

import android.annotation.SuppressLint;
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

    TextView vista;
    Function function;
    Activity activity;
    double limite;
    boolean isnumber;

    public static DialogoCambiarDatos newInstance(TextView view,double limite,Function function, Activity activity,boolean isnumber){
        DialogoCambiarDatos dialogo = new DialogoCambiarDatos(view,limite,function,activity,isnumber);
        if(function==null){
          return null;
        }
        return dialogo;
    }

    public DialogoCambiarDatos(TextView view,double limite, Function function,Activity activity,boolean isnumber){
        this.vista=view;
        this.limite=limite;
        this.activity=activity;
        this.function=function;
        this.isnumber=isnumber;
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
            @SuppressLint("DefaultLocale")
            public void onClick(View v) {
                String cadena = editText.getText().toString();

                if(!cadena.isEmpty()){
                    if(EsUnNuemroReal(cadena)&&isnumber){
                        Double numero = Double.valueOf(cadena);

                        if (Math.abs(numero) < limite) {
                            if(vista!=null) {

                                vista.setText(String.format("%.0f", numero));
                            }
                                function.apply(numero);

                                dismiss();

                        }
                        else{
                            editText.setError("Tiene que ser menor de "+ limite);
                        }
                    }
                    else if(!isnumber){
                        if(vista!=null){
                            vista.setText(cadena);
                            function.apply(cadena);

                            dismiss();
                        }

                    }
                    else {
                        editText.setError("No se interpretar como un numero ");
                    }

                }
                else{
                    editText.setError("No puede ser nulo");
                }



            }
        });
        builder.setView(myView);

        return builder.create();
    }

    public boolean EsUnNuemroReal(String cadena){
       return cadena.matches("^(-)?\\d*");

    }


}
