package com.example.rodofroll.Vistas.Fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.arch.core.util.Function;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Firebase.FirebaseUtilsV1;
import com.example.rodofroll.MainActivity;
import com.example.rodofroll.Objetos.Combatiente;
import com.example.rodofroll.Objetos.ComunicateToTabsListener;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.Objetos.Validacion;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Dialogos.DialogoCambiarDatos;
import com.example.rodofroll.Vistas.Dialogos.Dialogos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class InventarioFragment extends Fragment implements View.OnClickListener, ComunicateToTabsListener, EstructuraFragment {

    View v;
    RecyclerView recyclerView;
    AdapterInventario adapter;
    Personaje p;
    TextView OroTextView;
    TextView PlataTextView;
    TextView CobreTextView;
    TextView PesoTextView;
    TextView PesoLimitTextView;
    double contadorpeso;
    Activity activity;
    List<Personaje.Cosa> cosaList;


    public InventarioFragment(Combatiente p, Activity activity) {
        this.p=(Personaje) p;
        this.activity=activity;
    }



    @Override
    public View onCreateView(final LayoutInflater inflater,
                             final ViewGroup container, final Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.inventario_layout, container, false);
        contadorpeso=0;
        cosaList=new ArrayList<>();
       InicializarComponentes(v);
        adapter = new AdapterInventario(cosaList);
       ComportamientoRecycler();



        return v;
    }
    @SuppressLint("DefaultLocale")
    @Override
    public void InicializarComponentes(View view) {
        recyclerView= v.findViewById(R.id.recycler);

        OroTextView = v.findViewById(R.id.OroTextView);
        PlataTextView = v.findViewById(R.id.PlataTextView);
        CobreTextView = v.findViewById(R.id.CobreTextView);
        PesoTextView = v.findViewById(R.id.PesoTextView);
        PesoLimitTextView = v.findViewById(R.id.LimitPesoTextView);

        OroTextView.setOnClickListener(this);
        CobreTextView.setOnClickListener(this);
        PlataTextView.setOnClickListener(this);
        PesoLimitTextView.setOnClickListener(this);

        OroTextView.setText(String.format("%.0f",p.getDinero().oro));
        PlataTextView.setText(String.format("%.0f",p.getDinero().plata));
        CobreTextView.setText(String.format("%.0f",p.getDinero().cobre));
        PesoLimitTextView.setText(String.format("%.0f",p.getPesolimit()));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.OroTextView:
                DialogoCambiarDatos.newInstance(OroTextView,99999, CrearFuncionDinero("oro"),activity,true).show(getFragmentManager(),"Oro");
                break;
            case R.id.PlataTextView:
                DialogoCambiarDatos.newInstance(PlataTextView,99999, CrearFuncionDinero("plata"),activity,true).show(getFragmentManager(),"Plata");
                break;
            case R.id.CobreTextView:
                DialogoCambiarDatos.newInstance(CobreTextView,99999, CrearFuncionDinero("cobre"), activity,true).show(getFragmentManager(),"Cobre");
                break;
            case R.id.LimitPesoTextView:
                    DialogoCambiarDatos.newInstance(PesoLimitTextView,1000, CrearFuncionPesoLimite(),activity,true).show(getFragmentManager(),"LimitPeso");
                break;
        }
    }
   public Function CrearFuncionDinero(final String tipodinero){


        Function f= new Function() {
            @Override
            public Object apply(Object input) {
                FirebaseUtilsV1.SET_Dinero(tipodinero,(double) input,p);
                return null;
            }
        };
       return f;
   }

    public Function CrearFuncionPesoLimite(){


        Function f= new Function() {
            @Override
            public Object apply(Object input) {
                double numero = (double)input;
                if(Double.parseDouble(PesoLimitTextView.getText().toString())< contadorpeso){
                    Toast.makeText(getContext(),"Ha intentado poner un peso incoherente con el peso actual",Toast.LENGTH_LONG).show();
                    PesoLimitTextView.setText(String.valueOf(contadorpeso));
                    numero=contadorpeso;
                }


                FirebaseUtilsV1.SET_PesoLimit(numero,p);
                return null;
            }
        };
        return f;
    }

    @Override
    public void onClickParentTab() {
       showDialogoCosas((MainActivity) activity);



    }

    public void showDialogoCosas(final MainActivity mainActivity){
        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(mainActivity);
        LayoutInflater inflater = mainActivity.getLayoutInflater();
        Button aceptarbutton;
        final EditText NombreEditText;
        final EditText PesoEditText;

        final View myView = inflater.inflate(R.layout.cosas_layout, null);
        aceptarbutton=myView.findViewById(R.id.Aceptarbutton);
        NombreEditText = myView.findViewById(R.id.NombreEditText);
        PesoEditText = myView.findViewById(R.id.PesoEditText);

        dialogBuilder.setView(myView);
        final Dialog dialog =dialogBuilder.show();

        aceptarbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if(Validacion.ValidarEdit(NombreEditText)&Validacion.ValidarEdit(PesoEditText)){
                    if(!Validacion.EsUnNuemroReal(PesoEditText.getText().toString())){
                        PesoEditText.setError("No se puede interpretar como un numero");
                    }
                else if(Double.parseDouble(PesoEditText.getText().toString())+contadorpeso>Double.parseDouble(PesoLimitTextView.getText().toString())){
                    PesoEditText.setError("No puedes llevar tanto peso");
                }
                else{
                    String numero = PesoEditText.getText().toString().replace(',','.');
                    Double numerdouble = Double.parseDouble(String.format("%.2f",Double.parseDouble(numero)).replace(',','.'));
                    Personaje.Cosa cosa = new Personaje.Cosa(NombreEditText.getText().toString(),numerdouble);
                    FirebaseUtilsV1.GET_RefCosas(FirebaseUtilsV1.getDatosUser(),p).push().setValue(cosa);
                    dialog.dismiss();

                }

                }




            }
        });


    }

    public void ComportamientoRecycler(){

        cosaList=new ArrayList<>();
        adapter = new AdapterInventario(cosaList);


        final DatabaseReference cosasdb = FirebaseUtilsV1.GET_RefCosas(FirebaseUtilsV1.getDatosUser(),p);


        cosasdb.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cosaList.removeAll(cosaList);
                recyclerView.removeAllViews();
                contadorpeso=0;
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Personaje.Cosa cosa = snapshot.getValue(Personaje.Cosa.class);
                    cosa.setKey(snapshot.getKey());
                    cosaList.add(cosa);
                    contadorpeso+=cosa.getPeso();
                }
                PesoTextView.setText(String.format("%.2f",contadorpeso));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT


        ) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {


                return false;
            }



            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Personaje.Cosa cosa = (Personaje.Cosa) cosaList.get(viewHolder.getAdapterPosition());

                Function<String,Void> function = new Function<String, Void>() {
                    @Override
                    public Void apply(String input) {
                        cosasdb.child(input).removeValue();
                        return null;
                    }
                };

                Dialogos.showEliminar(cosa.getNombre(), getActivity(),cosa.getKey(),function).show();
                adapter.notifyDataSetChanged();

            }
        } ).attachToRecyclerView(recyclerView);


    }



    class HolderInventario extends RecyclerView.ViewHolder implements View.OnClickListener{


        CardView NombreCardView;
        TextView NombreTextView;
        CardView PesoCardView;
        TextView PesoTextView;

        View.OnClickListener listener;

        public HolderInventario(@NonNull View itemView) {
            super(itemView);
            NombreTextView = itemView.findViewById(R.id.NombreTextView);
            PesoTextView = itemView.findViewById(R.id.PesoTextView);
            NombreCardView = itemView.findViewById(R.id.NombreCardView);
            PesoCardView = itemView.findViewById(R.id.PesoCardView);

            NombreCardView.setOnClickListener(this);
            PesoCardView.setOnClickListener(this);

        }
        @SuppressLint("DefaultLocale")
        public void bind(Personaje.Cosa cosa){
            PesoTextView.setText(String.valueOf(cosa.getPeso()));
            NombreTextView.setText(cosa.getNombre());
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.NombreCardView:
                    break;
                case R.id.PesoCardView:

                    break;
            }

        }
    }
    class AdapterInventario extends RecyclerView.Adapter implements View.OnClickListener {

        List<Personaje.Cosa> cosas;
        HolderInventario holderInventario;


        public AdapterInventario(List<Personaje.Cosa> cosas) {
            this.cosas=cosas;
        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.inventario_cardview,parent,false);
            holderInventario = new HolderInventario(v);

            return holderInventario;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
          holderInventario.bind(this.cosas.get(position));
        }

        @Override
        public int getItemCount() {
            return this.cosas.size();
        }

        @Override
        public void onClick(View v) {

        }
    }

}
