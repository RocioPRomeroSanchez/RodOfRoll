package com.example.rodofroll;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DadosFragment extends Fragment implements View.OnClickListener {

    TextView cantidaddados;
    TextView modificador;
    TextView sumatorio;
    Button menosnumero;
    Button masnumero;
    Button menosmodificador;
    Button masmodificador;
    Spinner spinner;
    Context context;
    ImageView imageView;
    ListView lista;
    int tipodado;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.dados_layout, container, false);
        context =view.getContext();
        spinner = view.findViewById(R.id.spinner);
        imageView = view.findViewById(R.id.idDadoImagen);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(inflater.getContext(),
                R.array.dados_array, android.R.layout.simple_spinner_item);
        final ArrayAdapter<Integer> adaptadorlista = new ArrayAdapter<Integer>(context,android.R.layout.simple_expandable_list_item_1);


        sumatorio=view.findViewById(R.id.sumatoriotextview);
        cantidaddados =view.findViewById(R.id.textViewDado);
        modificador=view.findViewById(R.id.textViewMod);
        masmodificador=view.findViewById(R.id.buttonsuma2);
        menosmodificador=view.findViewById(R.id.buttonresta2);
        masnumero=view.findViewById(R.id.buttonsuma1);
        menosnumero=view.findViewById(R.id.buttonresta1);
        lista =view.findViewById(R.id.listatiradas);
        lista.setAdapter( adaptadorlista);

        cantidaddados.setOnClickListener(this);
        masnumero.setOnClickListener(this);
        menosnumero.setOnClickListener(this);
        menosmodificador.setOnClickListener(this);
        masmodificador.setOnClickListener(this);
        imageView.setOnClickListener(this);


        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 int res = Dialogo(tipodado,view);


                adaptadorlista.add(res);
                int suma=0;

                for(int i=0;i<adaptadorlista.getCount();i++){
                    suma+=adaptadorlista.getItem(i);
                }

                sumatorio.setText( String.valueOf(suma));

            }
        });


        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                Drawable drawable;

                switch (position) {
                    case 0:
                        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.dado4, null);
                        imageView.setImageDrawable(drawable);
                        tipodado=4;
                        break;
                    case 1:
                        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.dado6, null);
                        imageView.setImageDrawable(drawable);
                        tipodado=6;
                        break;
                    case 2:
                        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.dado8, null);
                        imageView.setImageDrawable(drawable);
                        tipodado=8;
                        break;
                    case 3:
                        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.dado10, null);
                        imageView.setImageDrawable(drawable);
                        tipodado=10;
                        break;
                    case 4:
                        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.dado12, null);
                        imageView.setImageDrawable(drawable);
                        tipodado=12;
                        break;
                    case 5:
                        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.dado20, null);
                        imageView.setImageDrawable(drawable);
                        tipodado=20;
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        return view;
    }
    public int Dialogo(int numero, View view) {
        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
        LayoutInflater inflater = DadosFragment.this.getLayoutInflater();

        View v = inflater.inflate(R.layout.resultado, null);
        builder.setView(v);
        TextView resultado = v.findViewById(R.id.textViewResultado);
        TextView info = v.findViewById(R.id.textViewNumDados);
        TextView tiradastextview = v.findViewById(R.id.textViewTiradas);


        Random r = new Random();
        List<Integer> tiradas = new ArrayList<Integer>();
        String cadena="";

        int suma = 0;
        TextView textdado = view.findViewById(R.id.textViewDado);
        TextView textmod = view.findViewById(R.id.textViewMod);
        int numdado = Integer.parseInt((textdado.getText().toString()));
        int modificador = Integer.parseInt(textmod.getText().toString());
        for (int i = 0; i < numdado; i++) {
            int n = r.nextInt(numero) + 1;
            cadena+=n;
            suma += n;
            if(i!=numdado-1){
                cadena+=";";
            }

        }
        int res = suma+modificador;
        resultado.setText(String.valueOf(res) );
        if(modificador>=0) {
            info.setText(String.valueOf(cantidaddados.getText()) + "d" +numero+"+"+ + modificador);
        }

        else{
            info.setText(String.valueOf(cantidaddados.getText()) + "d" +numero+ "-"+modificador);
        }
        tiradastextview.setText(cadena);

        builder.show();

        return res;

    }



    @Override
    public void onClick(View view) {
        int dado=Integer.valueOf(cantidaddados.getText().toString());
        int mod=Integer.valueOf(modificador.getText().toString());
        switch (view.getId()) {

            case R.id.buttonresta1:

                if(dado>1) {
                    dado--;
                    cantidaddados.setText(String.valueOf(dado));
                }

                break;
            case R.id.buttonresta2:
                mod--;
                if(mod>0) {
                    modificador.setText("+"+String.valueOf(mod));
                }
                else if(mod==0){
                    modificador.setText(String.valueOf(mod));
                }
                else{
                    modificador.setText("-"+String.valueOf(Math.abs(mod)));

                }
                break;
            case R.id.buttonsuma1:
                dado++;
                cantidaddados.setText(String.valueOf(dado));
                break;
            case R.id.buttonsuma2:
                mod++;
                if(mod>=0) {
                    modificador.setText("+"+String.valueOf(mod));
                }
                else if(mod==0){
                    modificador.setText(String.valueOf(mod));
                }
                else{
                    modificador.setText("-"+String.valueOf(Math.abs(mod)));

                }
                break;
        }

    }
}
