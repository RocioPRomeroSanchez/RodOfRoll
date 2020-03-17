package com.example.rodofroll.Vistas.Dialogos;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;

import com.example.rodofroll.Firebase.FireBaseUtils;
import com.example.rodofroll.Objetos.ConversorImagenes;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.Objetos.Usuario;
import com.example.rodofroll.Objetos.Validacion;
import com.example.rodofroll.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DialogoBuscarCombate extends DialogFragment {

    Activity activity;
    Usuario master;
    public static DialogoBuscarCombate newInstance( Activity activity,Usuario usuario) {
        DialogoBuscarCombate dialogo = new DialogoBuscarCombate(activity,usuario);
        return dialogo;
    }

    public DialogoBuscarCombate(Activity activity, Usuario usuario){
        this.activity=activity;
        this.master=usuario;

    }

    public AlertDialog DialogNoTieneCombates(){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        dialogBuilder.setMessage("No tiene combates creados");
        return dialogBuilder.create();
    }

    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View myView = inflater.inflate(R.layout.busqcombat_layout, null);

        ImageView Masterimagen = myView.findViewById(R.id.MasterimageView);
        ImageView dadoimagen = myView.findViewById(R.id.dadoimageView);
        final ImageView Personajeimagen = myView.findViewById(R.id.PersonajeimageView);
        TextView MasterText = myView.findViewById(R.id.MastertextView);
        final TextView InitText = myView.findViewById(R.id.IniciativatextView);
        final TextView ResultadoText = myView.findViewById(R.id.ResultadotextView);
        final EditText TiradEditText = myView.findViewById(R.id.Tiradaeditext);

        final Spinner combatespinner = myView.findViewById(R.id.CombateSpinner);
        final Spinner personajespinner = myView.findViewById(R.id.PersonajeSpinner);


        final DatabaseReference personajesdb = FireBaseUtils.GetPersonajesRef();
        final DatabaseReference combatesdb = FireBaseUtils.GetCombates(master.getKey());
        final List<String> nombres= new ArrayList<>();
        final List<Personaje> personajes= new ArrayList<>();
        final List<String> combates = new ArrayList<>();
        final List<String> keycombates=new ArrayList<>();

        personajesdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                personajes.removeAll(personajes);
                nombres.removeAll(nombres);
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    HashMap<String,Object> principal= (HashMap<String, Object>) snapshot.getValue();

                    Personaje p = new Personaje(principal.get("atributos"),principal.get("biografia"),principal.get("inventario"), snapshot.getKey());

                    personajes.add(p);
                    nombres.add(p.getNombre());


                }

                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, nombres);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                personajespinner.setAdapter(arrayAdapter);



            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        combatesdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                combates.removeAll(combates);
                keycombates.removeAll(keycombates);
                for(DataSnapshot snapshot:dataSnapshot.getChildren()){
                    HashMap<String,String> combate=(HashMap<String,String>)snapshot.getValue();
                    combates.add(combate.get("nombre"));
                    keycombates.add(snapshot.getKey());
                }
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(activity,android.R.layout.simple_spinner_item, combates);
                arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                combatespinner.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        TiradEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                int num;
                if(TiradEditText.getText().toString().isEmpty()){
                    num= Integer.parseInt(0+ InitText.getText().toString());
                }
                else {
                    num = Integer.parseInt(TiradEditText.getText().toString())+ Integer.parseInt(InitText.getText().toString()) ;
                }
                ResultadoText.setText(String.valueOf(num));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        personajespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Personajeimagen.setImageBitmap(ConversorImagenes.convertirStringBitmap(personajes.get(position).getImagen()));
                InitText.setText(String.valueOf(personajes.get(position).getModiniciativa()));
                int num =0;
                if(TiradEditText.getText().toString().isEmpty()){
                    num= Integer.parseInt(0+ InitText.getText().toString());

                }
                else {
                    num = Integer.parseInt(TiradEditText.getText().toString())+ Integer.parseInt(InitText.getText().toString()) ;
                }
                ResultadoText.setText(String.valueOf(num));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        Button enviar = myView.findViewById(R.id.Enviarbutton);
        final EditText dadoeditext = myView.findViewById(R.id.Tiradaeditext);

        dialogBuilder.setView(myView);
        final AlertDialog alertDialog=  dialogBuilder.create();


        Masterimagen.setImageBitmap(ConversorImagenes.convertirStringBitmap(master.getFoto()));
        MasterText.setText(master.getNombre());
        InitText.setText("0");
        ResultadoText.setText("0");

        dadoimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Dialogos.showDialogoDado(20,v,1,0, activity);
                dadoeditext.setText(String.valueOf(num));
                dadoeditext.setError(null);
            }
        });




        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //  FireBaseUtils.getRef().child("datos").child(master.key).push().setValue(1);

                if(combates.size()==0){
                    Toast.makeText(getContext(),"Este usuario no tiene combates asociados",Toast.LENGTH_LONG).show();
                }
                else{
                    String keycombat = (String) keycombates.get(combatespinner.getSelectedItemPosition());

                    if(personajes.size()==0){
                        Toast.makeText(getContext(),"No tienes personajes para enviar",Toast.LENGTH_LONG).show();
                    }
                    else{


                        if(Validacion.ValidarEdit(TiradEditText)){
                            FireBaseUtils.AnyadirRefPersonaje(master.getKey(),keycombat,FireBaseUtils.getKey(),personajes.get(personajespinner.getSelectedItemPosition()));
                            Toast.makeText(getContext(),"Exito",Toast.LENGTH_LONG).show();

                        }

                    }

                }





                /*p.iniciativa=6;*/

                // FireBaseUtils.getRef().child("combates").child(master.key).push().child("OrdenTurno").child(p.key).setValue(p.Map());
            }
        });



            return alertDialog;


    }
}
