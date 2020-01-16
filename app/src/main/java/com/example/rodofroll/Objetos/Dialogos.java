package com.example.rodofroll.Objetos;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.ThemedSpinnerAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.example.rodofroll.MainActivity;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.FichaPersonajeFragment;
import com.google.android.material.textfield.TextInputEditText;

import static com.example.rodofroll.Objetos.MisMetodos.convertirImagenString;
import static com.example.rodofroll.Objetos.MisMetodos.convertirStringBitmap;

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

    public static void showDialogoNuevoUsuario(final Activity activity, Context context){
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
               Toast.makeText(v.getContext(),"Aqui hay que cambiar la imagen",Toast.LENGTH_LONG).show();
           }
       });

        aceptarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validacion.ValidarEdit(editText)){
                    FichaPersonajeFragment fichaPersonajeFragment= new FichaPersonajeFragment();
                    String imagen =convertirImagenString(((BitmapDrawable)imageView.getDrawable()).getBitmap());

                    Personaje p = new Personaje(editText.getText().toString(),imagen);



                    ((MainActivity)activity).RemplazarFragment(fichaPersonajeFragment,true);



                    alertDialog.dismiss();


                };

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
