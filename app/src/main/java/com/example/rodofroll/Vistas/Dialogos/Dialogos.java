package com.example.rodofroll.Vistas.Dialogos;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.rodofroll.Firebase.FirebaseUtilsV1;
import com.example.rodofroll.MainActivity;
import com.example.rodofroll.Objetos.Combatiente;
import com.example.rodofroll.Objetos.Monstruo;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.Objetos.Validacion;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Fragments.FichaPersonajeFragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.rodofroll.Objetos.ConversorImagenes.convertirImagenString;

public class Dialogos {



    public static void showDialogoNuevoCombatiente(final MainActivity activity, final Context context,final Combatiente combatiente){


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
               activity.MenuEmergenteImagen(imageView,null);
           }
       });

        aceptarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if((editText.getText().toString()).length() > 13){
                    editText.setError("Tiene demasiados caracteres máximo 13");

                }
                else if(Validacion.ValidarEdit(editText)) {
                        String imagen = convertirImagenString(((BitmapDrawable) imageView.getDrawable()).getBitmap());

                        if (combatiente instanceof Personaje) {
                            Combatiente p = new Personaje(editText.getText().toString(), imagen);
                            try {
                                FirebaseUtilsV1.AnyadirCombatiente(p);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Combatiente m = new Monstruo(editText.getText().toString(), imagen);
                            try {
                                FirebaseUtilsV1.AnyadirCombatiente(m);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        }
                        alertDialog.dismiss();

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

    public static int showDialogoDado(int tipodado, View view, int numero, int modificador,String NombreTirada, final Activity activity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = activity.getLayoutInflater();

        View v = inflater.inflate(R.layout.resultado, null);
        builder.setView(v);
        final TextView resultado = v.findViewById(R.id.textViewResultado);
        final TextView info = v.findViewById(R.id.textViewNumDados);
        final TextView tiradastextview = v.findViewById(R.id.textViewTiradas);
        final TextView nombretiradatextview = v.findViewById(R.id.NombreTiradatextView);
        ImageView whatsappimage = v.findViewById(R.id.idwhatsapp);

        if(NombreTirada.isEmpty()){
            nombretiradatextview.setVisibility(View.GONE);
        }
        nombretiradatextview.setText(NombreTirada);


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

        whatsappimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Creamos un Intent con la accion de enviar
                Intent whatsappIntent = new Intent(Intent.ACTION_SEND);
                //Ponemos el tipo de dato que vamos a enviar texto plano
                whatsappIntent.setType("text/plain");
                //Seteamos hacia que aplicacion tiene que ir
                whatsappIntent.setPackage("com.whatsapp");
                //Enviamos los datos
                whatsappIntent.putExtra(Intent.EXTRA_TEXT, "*"+nombretiradatextview.getText().toString()+"*"+
                        "\n"+"Informacion:"+info.getText().toString()+"\n"+
                        "Resultado:"+resultado.getText().toString()+"\nDados:"+tiradastextview.getText().toString());
                try {
                    //Iniciamos
                    activity.startActivity(whatsappIntent);
                } catch (android.content.ActivityNotFoundException ex) {
                    //Si hay un error mostramos un Toast
                    Toast.makeText(activity,  "Whatsapp no instalado", Toast.LENGTH_SHORT).show();

                }

            }
        });

        return res;

    }

    public static AlertDialog.Builder showEliminar(String nombre, Activity activity, final Object cadena, final Function function){
        // Use the Builder class for convenient dialog construction
        final AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        String mensaje;
        if(nombre==null||nombre.isEmpty()){
            mensaje="¿Estas seguro?";
        }
        else{
            mensaje="De verdad deseas eliminar a "+nombre;
        }

        builder.setMessage(mensaje)
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



    public static void showDialogNuevoCombate(final MainActivity activity) throws Exception {


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
                        FirebaseUtilsV1.AddCombate(editText.getText().toString());

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
