package com.example.rodofroll.Vistas.Dialogos;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.BitmapDrawable;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.arch.core.util.Function;

import com.example.rodofroll.Firebase.FireBaseUtils;
import com.example.rodofroll.MainActivity;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.Objetos.Validacion;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Fragments.FichaPersonajeFragment;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.rodofroll.Objetos.ConversorImagenes.convertirImagenString;

public class Dialogos {



    public static void showDialogoNuevoCombatiente(final MainActivity activity, final Context context){


        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View myView = inflater.inflate(R.layout.nuevo_combatiente, null);
        final TextInputEditText editText = myView.findViewById(R.id.nombrenuevoeditext);
        final ImageView imageView = myView.findViewById(R.id.imageView);
        Button aceptarbutton = myView.findViewById(R.id.aceptarbutton);
        Button cancelarbutton = myView.findViewById(R.id.cancelbutton);

        dialogBuilder.setView(myView);
       final AlertDialog alertDialog=  dialogBuilder.show();

       imageView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               activity.MenuEmergenteImagen(imageView);
           }
       });

        aceptarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validacion.ValidarEdit(editText)){
                    FichaPersonajeFragment fichaPersonajeFragment= new FichaPersonajeFragment();
                    String imagen =convertirImagenString(((BitmapDrawable)imageView.getDrawable()).getBitmap());

                    Personaje p = new Personaje(editText.getText().toString(),imagen);
                    try {
                        FireBaseUtils.AnyadirCombatiente(p);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                  /*  fichaPersonajeFragment= new FichaPersonajeFragment(p);
                    activity.RemplazarFragment(fichaPersonajeFragment,true);*/


                    alertDialog.dismiss();


                }
                else{
                    Toast.makeText(context, "Tiene que haber una imagen",Toast.LENGTH_LONG).show();
                }


            }
        });
        cancelarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    public static int showDialogoDado(int tipodado, View view, int numero, int modificador, Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = activity.getLayoutInflater();

        View v = inflater.inflate(R.layout.resultado, null);
        builder.setView(v);
        TextView resultado = v.findViewById(R.id.textViewResultado);
        TextView info = v.findViewById(R.id.textViewNumDados);
        TextView tiradastextview = v.findViewById(R.id.textViewTiradas);


        Random r = new Random();
        List<Integer> tiradas = new ArrayList<Integer>();
        String cadena="";

        int suma = 0;


        for (int i = 0; i < numero; i++) {
            int n = r.nextInt(tipodado) + 1;
            cadena+=n;
            suma += n;
            if(i!=numero-1){
                cadena+=";";
            }

        }
        int res = suma+modificador;
        resultado.setText(String.valueOf(res) );
        if(modificador>=0) {
            info.setText(numero + "d" +tipodado+"+"+ + modificador);
        }

        else{
            info.setText(numero + "d" +tipodado+ "-"+modificador);
        }
        tiradastextview.setText(cadena);

        builder.show();

        return res;

    }

    public static AlertDialog.Builder showEliminar(String nombre, Activity activity, final String cadena, final Function function){
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);

        builder.setMessage("De verdad deseas eliminar a "+ Html.fromHtml("<b>"+nombre+"</b>"))
                .setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        function.apply(cadena);

                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();

                    }
                });



        return builder;

    }


    public static void showDialogNuevoCombate(final MainActivity activity, final Context context) throws Exception {


        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();

        final View myView = inflater.inflate(R.layout.anyadircombatedialog, null);
        final TextInputEditText editText = myView.findViewById(R.id.nombrecombatedit);
        Button aceptarbutton =myView.findViewById(R.id.aceptarbutton);
        Button cancelarbutton = myView.findViewById(R.id.cancelbutton);
        dialogBuilder.setView(myView);


        final AlertDialog alertDialog=  dialogBuilder.show();


        aceptarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if(Validacion.ValidarEdit(editText)){
                        FireBaseUtils.AnyadirCombate(editText.getText().toString());
                         alertDialog.dismiss();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        cancelarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });



    }
}
