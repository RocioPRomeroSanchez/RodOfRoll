package com.example.rodofroll.Vistas.Dialogos;

import android.annotation.SuppressLint;
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

import com.example.rodofroll.Firebase.FirebaseUtilsV1;
import com.example.rodofroll.Objetos.ConversorImagenes;
import com.example.rodofroll.Objetos.InicializarVistas;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.Objetos.Usuario;
import com.example.rodofroll.Objetos.Validacion;
import com.example.rodofroll.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DialogoBuscarCombate extends DialogFragment implements InicializarVistas {

    Activity activity;
    Usuario master;
    ImageView Masterimagen;
    ImageView dadoimagen;
    ImageView Personajeimagen;
    TextView MasterText;
    TextView InitText;
    TextView ResultadoText;
    EditText TiradEditText;
    Button enviar;
    EditText dadoeditext;

    Spinner combatespinner;
    Spinner personajespinner;

    final List<String> nombres= new ArrayList<>();
    final List<Personaje> personajes= new ArrayList<>();
    final List<String> combates = new ArrayList<>();
    final List<String> keycombates=new ArrayList<>();

    DatabaseReference personajesdb;
    DatabaseReference combatesdb;

    public static DialogoBuscarCombate newInstance( Activity activity,Usuario usuario) {
        DialogoBuscarCombate dialogo = new DialogoBuscarCombate(activity,usuario);
        return dialogo;
    }

    public DialogoBuscarCombate(Activity activity, Usuario usuario){
        this.activity=activity;
        this.master=usuario;

    }
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        final View myView = inflater.inflate(R.layout.busqcombat_layout, null);

        InicializarComponentes(myView);
        personajesdb = FirebaseUtilsV1.GET_RefPersonajes();
        combatesdb = FirebaseUtilsV1.GET_RefCombates(master.getKey());

        PersonajesPropios();
        CombatesUsuario();

        dialogBuilder.setView(myView);
        final AlertDialog alertDialog=  dialogBuilder.create();



        return alertDialog;

    }

    public void PersonajesPropios(){
        personajesdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                personajes.removeAll(personajes);
                nombres.removeAll(nombres);
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){

                    HashMap<String,Object> principal= (HashMap<String, Object>) snapshot.getValue();

                    Personaje p = new Personaje(principal.get("atributos"),principal.get("biografia"),principal.get("inventario"), snapshot.getKey());

                    HashMap<String,Object> combates = (HashMap<String, Object>)principal.get("combates");
                    List<Personaje.CombatesAsociados> combatesAsociados= new ArrayList<>();
                    if(combates!=null){
                        for (Map.Entry<String,Object> s :combates.entrySet()) {
                            String combateid = (String) ((HashMap<String,Object>)s.getValue()).get("combateid");
                            String masterid = (String) ((HashMap<String,Object>)s.getValue()).get("masterid");
                            combatesAsociados.add(new Personaje.CombatesAsociados(masterid,combateid));
                        }
                        p.setCombates(combatesAsociados);
                    }


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

    }

    public void TiradorEditText(){
        TiradEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("DefaultLocale")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                double num;
                if(TiradEditText.getText().toString().isEmpty()){
                    num= Double.parseDouble(InitText.getText().toString());
                }
                else {
                    num = Double.parseDouble(TiradEditText.getText().toString())+ Double.parseDouble(InitText.getText().toString()) ;
                }
                ResultadoText.setText(String.format("%.0f",num));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void CombatesUsuario(){
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

    }
    public void InicializarSpinner(){
        personajespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Personajeimagen.setImageBitmap(ConversorImagenes.convertirStringBitmap(personajes.get(position).getImagen()));
                InitText.setText(String.format("%.0f",personajes.get(position).getModiniciativa()));
                double num =0;
                if(TiradEditText.getText().toString().isEmpty()){
                    num= Integer.parseInt(InitText.getText().toString());

                }
                else {
                    num = Double.parseDouble(TiradEditText.getText().toString())+ Double.parseDouble(InitText.getText().toString()) ;
                }
                ResultadoText.setText(String.format("%.0f",num));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    @Override
    public void InicializarComponentes(View myView) {
        Masterimagen = myView.findViewById(R.id.MasterimageView);
        dadoimagen = myView.findViewById(R.id.dadoimageView);
        Personajeimagen = myView.findViewById(R.id.PersonajeimageView);
        MasterText = myView.findViewById(R.id.MastertextView);
        InitText = myView.findViewById(R.id.IniciativatextView);
        ResultadoText = myView.findViewById(R.id.ResultadotextView);
        TiradEditText = myView.findViewById(R.id.Tiradaeditext);
        combatespinner = myView.findViewById(R.id.CombateSpinner);
        personajespinner = myView.findViewById(R.id.PersonajeSpinner);
        enviar = myView.findViewById(R.id.Enviarbutton);
        dadoeditext = myView.findViewById(R.id.Tiradaeditext);

        Masterimagen.setImageBitmap(ConversorImagenes.convertirStringBitmap(master.getFoto()));
        MasterText.setText(master.getNombre());
        InitText.setText("0");
        ResultadoText.setText("0");

        dadoimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Dialogos.showDialogoDado(20, v, 1, 0, activity);
                dadoeditext.setText(String.valueOf(num));
                dadoeditext.setError(null);
            }
        });

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (combates.size() == 0) {
                    Toast.makeText(getContext(), "Este usuario no tiene combates asociados", Toast.LENGTH_LONG).show();
                } else {
                    String keycombat = (String) keycombates.get(combatespinner.getSelectedItemPosition());

                    if (personajes.size() == 0) {
                        Toast.makeText(getContext(), "No tienes personajes para enviar", Toast.LENGTH_LONG).show();
                    } else {


                        if (Validacion.ValidarEdit(TiradEditText)) {
                            FirebaseUtilsV1.SET_PersonjeINTOCombat(getContext(), master.getKey(), keycombat, Integer.parseInt(ResultadoText.getText().toString()), personajes.get(personajespinner.getSelectedItemPosition()));
                        }

                    }

                }

            }
        });

        TiradorEditText();
        InicializarSpinner();
    }
}
