package com.example.rodofroll.Objetos;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;

import com.airbnb.lottie.LottieAnimationView;
import com.example.rodofroll.MainActivity;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.DadosFragment;
import com.example.rodofroll.Vistas.FichaPersonajeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.example.rodofroll.Objetos.MisMetodos.convertirImagenString;

public class Dialogos {


    public static void showDialogoRol(final Activity activity, Context context){
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View myView = inflater.inflate(R.layout.elegirolayout, null);

        final Spinner spin = myView.findViewById(R.id.elecrolnspinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(context,R.array.roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spin.setAdapter(adapter);

        Button button =myView.findViewById(R.id.aceptarrolbutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(spin.getSelectedItemPosition()==0){
                    activity.getIntent().putExtra("rol",0);
                    activity.recreate();

                }
                else{
                    activity.getIntent().putExtra("rol",1);
                    activity.recreate();

                }
            }
        });
        dialogBuilder.setView(myView);
        dialogBuilder.show();
    }

    public static AlertDialog.Builder showDialogoNuevoUsuario(final MainActivity activity, final Context context){


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
                        activity.AnyadirCombatiente(p);

                    fichaPersonajeFragment= new FichaPersonajeFragment(p);
                    activity.RemplazarFragment(fichaPersonajeFragment,true);


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

        return dialogBuilder;

    }

    public static int showDialogoDado(int numero, View view, int dado, int modificador, Fragment fragment) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = fragment.getLayoutInflater();

        View v = inflater.inflate(R.layout.resultado, null);
        builder.setView(v);
        TextView resultado = v.findViewById(R.id.textViewResultado);
        TextView info = v.findViewById(R.id.textViewNumDados);
        TextView tiradastextview = v.findViewById(R.id.textViewTiradas);


        Random r = new Random();
        List<Integer> tiradas = new ArrayList<Integer>();
        String cadena="";

        int suma = 0;


        for (int i = 0; i < dado; i++) {
            int n = r.nextInt(numero) + 1;
            cadena+=n;
            suma += n;
            if(i!=dado-1){
                cadena+=";";
            }

        }
        int res = suma+modificador;
        resultado.setText(String.valueOf(res) );
        if(modificador>=0) {
            info.setText(dado + "d" +numero+"+"+ + modificador);
        }

        else{
            info.setText(dado + "d" +numero+ "-"+modificador);
        }
        tiradastextview.setText(cadena);

        builder.show();

        return res;

    }

    public static boolean showEliminar(String nombre, Activity activity, final String cadena, final Function function){
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
        // Create the AlertDialog object and return it
        builder.create().show();

        return false;

    }


}
