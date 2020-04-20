package com.example.rodofroll.Vistas.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.arch.core.util.Function;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.rodofroll.Firebase.FirebaseUtilsV1;
import com.example.rodofroll.Objetos.Combate;
import com.example.rodofroll.Objetos.ConversorImagenes;
import com.example.rodofroll.Objetos.ElementoAdapterClick;
import com.example.rodofroll.Objetos.InicializarVistas;
import com.example.rodofroll.Objetos.Personaje;
import com.example.rodofroll.R;
import com.example.rodofroll.Vistas.Adapters.TurnoAdapter;
import com.example.rodofroll.Vistas.Dialogos.DialogoCambiarDatos;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


//Es una clase Fragment que esta vinculada al funcionamiento del layout correspondeinte a los turnos de un combate
public class TurnoFragment extends Fragment implements View.OnClickListener , InicializarVistas {

    Combate combate;
    ImageButton turnobutton;
    List<Combate.PersonEnCombate> personajeEnCombateoList=new ArrayList<Combate.PersonEnCombate>();
    TextView ronda;
    RecyclerView recyclerView;
    DatabaseReference refronda;
    ImageView personajeImageView;
    TextView nombreTextView;
    TextView vidaTextView;
    TextView caTextView;
    TextView iniciativaTextView;
    TextView ataqueBaseTextView;
    TextView velociadTextView;
    Query ref;
    ValueEventListener listenerrecycler;




    //Constructor del Fragment requiere de un objeto Combate
    public TurnoFragment(Combate combate){
        this.combate=combate;

    }
    @Nullable
    @Override
    //Método que ocurre al crear la vista
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.combatturnos, container, false);

        //Llamamos al metodo que inicializa los elementos de la vista
        InicializarComponentes(view);

         //Creamos un escuchador que escucha una vez y obtiente el numero de rondas que tiene ese combate
        refronda = FirebaseUtilsV1.GET_RefRonda(combate);
        refronda.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //al cambiar cambiaremos el valor del textView de la ronda
                combate.setRonda((int)(long)dataSnapshot.getValue());
                ronda.setText(String.valueOf(combate.getRonda()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //Llamamos al metodo que define el comportamiento de nuestro recycler
        ComportamientoRecycler();

        return view;



    }
    //Metodo heredado de la interfaz InicializarVistas
    @Override
    public void InicializarComponentes(View view) {
        recyclerView = view.findViewById(R.id.recyclerView);
        ronda  = view.findViewById(R.id.rondatextView);

        personajeImageView = view.findViewById(R.id.PersonajeimageView);
        nombreTextView = view.findViewById(R.id.NombreText);
        vidaTextView = view.findViewById(R.id.VidaTextView);
        caTextView = view.findViewById(R.id.CATextView);
        iniciativaTextView = view.findViewById(R.id.IniciativaTextView);
        ataqueBaseTextView = view.findViewById(R.id.AtaqueBaseTextView);
        velociadTextView = view.findViewById(R.id.VelocidadTextView);

        ronda.setOnClickListener(this);
        turnobutton=view.findViewById(R.id.turnobutton);
        turnobutton.setOnClickListener(this);

        personajeEnCombateoList= new ArrayList<>();

    }
    //Metodo que define el comportamiento de nuestro recycler
    public void ComportamientoRecycler(){

        //Hacemos una consulta a firebase preguntando por los combatientes que hay en el combate pasado por argumento ordenados por su iniciativa de forma ascendente,
        //(Firebase no da opcion ha hacerlo de forma descendete)
         ref = FirebaseUtilsV1.OrdenarCombatePorIniciativa(combate);
        //Inicializacion de la lista de personajes en el combate
        personajeEnCombateoList=new ArrayList<>();
        //Inicializacion del adapeter
        final TurnoAdapter adapter = new TurnoAdapter(getContext(),personajeEnCombateoList,combate);
       listenerrecycler= ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Al efectuar un cambio en la referencia vaciamos la lista de personajes en el combate
                personajeEnCombateoList.removeAll(personajeEnCombateoList);

                //Recogemos todos los datos y lo anyadimos a la lista
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    HashMap<String, Object> valor = (HashMap<String, Object>) snapshot.getValue();
                    Combate.PersonEnCombate persona = new Combate.PersonEnCombate(snapshot.getKey(),(String) valor.get("personajekey"),(String) valor.get("usuariokey"),  (Long) valor.get("iniciativa"), (Boolean) valor.get("turno"),(Boolean) valor.get("avisar"));
                    personajeEnCombateoList.add(persona);

                }
                //Notifamos al adaptador de los cambios
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

        //Evento que ocurre al clickear en la imagen del cardview con forma de campana crea un toast para informar al usuario que se avisara
        //al jugador del personaje que es su turno por medio del metodo avisar
        adapter.setClickBtnImagen(new ElementoAdapterClick() {
            @Override
            public void onElementoClick(Combate.PersonEnCombate personEnCombate) {
                Toast.makeText(getContext(),"Avisando..."+personEnCombate.getIniciativa(), Toast.LENGTH_SHORT).show();
                personEnCombate.Avisar(combate);
            }
        });
        //Evento que ocurre al clickear el elemento correspondiente a la iniciativa de personaje del cardview , se creara un dialogo al que se le pasara
        //un objeto Function que realizara la accion de cambiar la iniciativa del personaje, esto provocara que el recyclerview de este fragment al estar
        //vinculado con el escuchador que recibe cambios al cambiar algo de los personajes en combate (usuariokey,personajekey,iniciativa)
        //en este caso la iniciativa provocara que el recycler reordene los personajes
        adapter.setClicBtnIniciativa(new ElementoAdapterClick() {

            @Override
            public void onElementoClick(Combate.PersonEnCombate personEnCombate) {
                Toast.makeText(getContext(),"Elemnto", Toast.LENGTH_SHORT).show();
                Function f = FuncionIniciativa(personEnCombate.getKeyprincipal());

                DialogoCambiarDatos.newInstance(null,100,f,getActivity(),true).show(getFragmentManager(),"Iniciativa");

            }
        });
        //Evento que ocurre al clickear el cardview de manera corta de los personajes,
        //Provocara un cambio en la parte de arriba de nuestro fragment donde se muestran las carecteristicas principales del personaje seleccionado
        adapter.setOnClickCortoListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Se recoge la posicion del elemento seleccionado
                int posicion=recyclerView.getChildAdapterPosition(v);
                //Al estar reordenado por nuestro codigo tenemos que realizar la operacion de restar la posicion al numero de personajes en la lista para obtener el personaje
                //verdaderamente seleccionado
                Combate.PersonEnCombate p = personajeEnCombateoList.get(personajeEnCombateoList.size()-1-posicion);
                //Metodo que coloca los datos del personaje en la parte informativa del fragmet
                SeleccionPersonaje(p);


            }
        });

        //Se definen los aspectos de recycler vinculando el adapter a el
        recyclerView.setAdapter(adapter);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false));
    }

    //Metodo que ocurre al pulsar el boton de pasar turno
    public void PasarTurno(List<Combate.PersonEnCombate> personajeEnCombateoList,Combate combate){

            boolean todosfalso= true;
            Combate.PersonEnCombate pos=null;
            int posi=0;

            //Se busca saber si alguno de los personajes en el combate tiene el campo turno a verdadero
            //Si es asi se guarda ese personaje y su posicion
            for(int i =0;i<personajeEnCombateoList.size();i++){
                if(personajeEnCombateoList.get(i).getTurno()){
                    todosfalso=false;
                    pos=personajeEnCombateoList.get(i);
                    posi = i;
                }
            }
            //Si ninguno tiene el campo a true es decir si todos son falsos se asigna el turno al combatiente con la mayor inciativa el cual
            //en nuestra lista seria el último (recordemos que la lista esta ordenada ascendetemente de mes a mas)
            //También seteamos el valor de la ronda del combate y la inicializamos a 1
            if(todosfalso){
                personajeEnCombateoList.get(personajeEnCombateoList.size()-1).setTurno(true, combate);

                FirebaseUtilsV1.SET_Ronda(1,combate);


            }
            //Si alguno es verdadero como lo que queremos es pasar el turno lo inicializamos al false
            else{

                personajeEnCombateoList.get(posi).setTurno(false, combate);
                //Como los turnos funcionan como un circulo al pasar el turno desde el ultimo se lo tendriamos que pasar al primero
                //Pero en este caso el primero de la lista es el último asi que lo tenemos que hacer al revés
                //La cuestion es que al siguiente le ponemos el valor de turno a true
                if(posi==0){
                    personajeEnCombateoList.get(personajeEnCombateoList.size()-1).setTurno(true, combate);
                    //Aque estamos recuperando el valor de la ronda y al pasar del ultimo al primero tenemos que aumentarlo en uno
                    int rondaint = combate.getRonda();
                    rondaint++;

                    combate.setRonda(rondaint);
                    ronda.setText(String.valueOf(combate.getRonda()));

                }
                else{

                    personajeEnCombateoList.get(posi-1 ).setTurno(true, combate);
                }



            }

    }

    //Gestiona los Eventos OnClick del fragment
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.turnobutton:
                if(personajeEnCombateoList.size()!=0){
                    PasarTurno(personajeEnCombateoList,combate);
                }

                break;

            case R.id.rondatextView:
                DialogoCambiarDatos.newInstance((TextView) v,100,  FuncionRonda(),getActivity(),true).show(getFragmentManager(),"Ronda");
                break;
        }
    }

    //Eliminar Escuchadores
    @Override
    public void onDestroy() {
        super.onDestroy();

        ref.removeEventListener(listenerrecycler);

    }




    //Recoloca la informacion del personaje seleccionado en la parte informativa de nuestro layout

    public void SeleccionPersonaje(Combate.PersonEnCombate personEnCombate){
        //Pregunta a la base de datos por el personaje seleccionado y saca sus datos

        FirebaseUtilsV1.GET_RefPersonaje(personEnCombate.getUsuariokey(),personEnCombate.getPersonajekey()).addListenerForSingleValueEvent(new ValueEventListener() {
            @SuppressLint("DefaultLocale")
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                HashMap principal = (HashMap) dataSnapshot.getValue();
                Personaje p = new Personaje(principal.get("atributos"),principal.get("biografia"),principal.get("inventario"), dataSnapshot.getKey());
                nombreTextView.setText(p.getNombre());
                personajeImageView.setImageBitmap(ConversorImagenes.convertirStringBitmap(p.getImagen()));
                vidaTextView.setText(String.format("%.0f",p.getVida()));
                caTextView.setText(String.format("%.0f",p.getArmadura()));
                iniciativaTextView.setText(String.format("%.0f",p.getIniciativa()));
                ataqueBaseTextView.setText(String.format("%.0f",p.getAtaque()));
                velociadTextView.setText(String.format("%.0f",p.getVelocidad()));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    //Cambia el valor de la iniciativa
    public Function FuncionIniciativa(final String key){
        Function f = new Function() {
            @Override
            public Object apply(Object input) {

                FirebaseUtilsV1.GET_RefCombate(combate.getKey());
               // FireBaseUtils.getRef().child("combates").child(FireBaseUtils.getKey()).child(combate.getKey()).child("ordenturno").child(key).child("iniciativa").setValue(input);
                return null;
            }
        };
        return f;
    }
    //Cambia el valor de la ronda
    public Function FuncionRonda(){

        Function f = new Function() {
            @Override
            public Object apply(Object input) {
                FirebaseUtilsV1.SET_Ronda(input,combate);

                return null;
            }
        };
        return f;

    }
}
