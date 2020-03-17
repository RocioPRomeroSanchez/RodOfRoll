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

public class DialogoCambiarDatos extends DialogFragment {

    TextView vista;
    Personaje p;
    String atributo;
    Activity activity;
    int limite;

    public static DialogoCambiarDatos newInstance(TextView view, Personaje p, String atributo, int limite, Activity activity) {
        DialogoCambiarDatos dialogo = new DialogoCambiarDatos( view,  p,atributo,limite, activity);
        return dialogo;
    }

    public DialogoCambiarDatos(TextView view, Personaje p, String atributo, int limite, Activity activity){
        vista=view;
        this.p=p;
        this.atributo=atributo;
        this.activity=activity;
        this.limite= limite;
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

        textView.setText(atributo);
        editText.setText(vista.getText());


        cancelarButon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        aceptarButton.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                String cadena = editText.getText().toString();
                if(cadena.isEmpty()){
                    editText.setError("No puede ser nulo");
                }
               else if(TextUtils.isDigitsOnly(cadena)) {
                   int numero = Integer.valueOf(cadena);

                    if (numero < limite) {
                        vista.setText(String.valueOf(numero));
                        try {
                            p.ModificarAtributosPersonaje(atributo.toLowerCase(),numero,p);



                        } catch (NoSuchFieldException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        }

                        // FireBaseUtils.getRef().child("personajes")
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
