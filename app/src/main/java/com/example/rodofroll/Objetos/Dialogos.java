package com.example.rodofroll.Objetos;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.arch.core.util.Function;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.airbnb.lottie.LottieAnimationView;
import com.example.rodofroll.Firebase.FireBaseUtils;
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

        builder.create().show();

        return false;

    }

    public static void showDialogoCombate(final Activity activity, Context context , final Usuario master ){


        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View myView = inflater.inflate(R.layout.busqcombat_layout, null);

        ImageView Masterimagen = myView.findViewById(R.id.MasterimageView);
        ImageView dadoimagen = myView.findViewById(R.id.dadoimageView);
        TextView MasterText = myView.findViewById(R.id.MastertextView);
        TextView InitText = myView.findViewById(R.id.IniciativatextView);
        TextView ResultadoText = myView.findViewById(R.id.ResultadotextView);

        Spinner combatespinner = myView.findViewById(R.id.CombateSpinner);
        Spinner personajespinner = myView.findViewById(R.id.PersonajeSpinner);

        Button enviar = myView.findViewById(R.id.Enviarbutton);
        final EditText dadoeditext = myView.findViewById(R.id.Tiradaeditext);

        dialogBuilder.setView(myView);
        final AlertDialog alertDialog=  dialogBuilder.show();


        Masterimagen.setImageBitmap(MisMetodos.convertirStringBitmap(master.foto));
        MasterText.setText(master.getNombre());
        InitText.setText("0");
        ResultadoText.setText("0");

        dadoimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num =Dialogos.showDialogoDado(20,v,1,0, activity);
                dadoeditext.setText(String.valueOf(num));
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FireBaseUtils.getRef().child("datos").child(master.key).push().setValue(1);
            }
        });









    }


}
