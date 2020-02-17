package com.example.rodofroll.Vistas;

import android.content.Context;
import android.graphics.drawable.Drawable;
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
import androidx.fragment.app.Fragment;

import com.example.rodofroll.Objetos.Dialogos;
import com.example.rodofroll.R;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DadosFragment extends Fragment implements View.OnClickListener {

    TextView DadosValor;
    TextView ModifValor;
    TextView Sumatorio;
    Button DadosRestaButton;
    Button DadosSumaButton;
    Button ModifSumaButton;
    Button ModifRestaButton;
    Spinner spinner;
    Context context;
    ImageView DadoImageView;
    ListView lista;
    ImageView BasuraImageView;
    Button Aceptar;
    int tipodado;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.tirardados_layout, container, false);
        context =view.getContext();
        spinner = view.findViewById(R.id.CarasSpinner);
        DadoImageView = view.findViewById(R.id.DadoimageView);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(inflater.getContext(),
                R.array.dados_array, android.R.layout.simple_spinner_item);
        final ArrayAdapter<Integer> adaptadorlista = new ArrayAdapter<Integer>(context,android.R.layout.simple_expandable_list_item_1);


        Sumatorio=view.findViewById(R.id.SumatorioTextView);
        DadosValor =view.findViewById(R.id.DadoValorTextView);
        ModifValor=view.findViewById(R.id.ModifValortextView);
        ModifSumaButton=view.findViewById(R.id.ModifSumaButton);
        ModifRestaButton=view.findViewById(R.id.ModifRestaButton);
        DadosSumaButton=view.findViewById(R.id.DadoSumaButton);
        DadosRestaButton=view.findViewById(R.id.DadoRestaButton);
        lista =view.findViewById(R.id.listatiradas);
        lista.setAdapter( adaptadorlista);
        BasuraImageView=view.findViewById(R.id.BorrarTiradasImageView);
        Aceptar = view.findViewById(R.id.TiradaButton);


        DadosSumaButton.setOnClickListener(this);
        DadosRestaButton.setOnClickListener(this);
        ModifRestaButton.setOnClickListener(this);
        ModifSumaButton.setOnClickListener(this);
        BasuraImageView.setOnClickListener(this);


        Aceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int res = Dialogos.showDialogoDado(tipodado,view,Integer.parseInt(DadosValor.getText().toString()),Integer.parseInt(ModifValor.getText().toString()),DadosFragment.this);


                adaptadorlista.add(res);
                int suma=0;

                for(int i=0;i<adaptadorlista.getCount();i++){
                    suma+=adaptadorlista.getItem(i);
                }

                Sumatorio.setText( String.valueOf(suma));

            }
        });

        BasuraImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adaptadorlista.clear();
                Sumatorio.setText("0");

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
                        DadoImageView.setImageDrawable(drawable);
                        tipodado=4;
                        break;
                    case 1:
                        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.dado6, null);
                        DadoImageView.setImageDrawable(drawable);
                        tipodado=6;
                        break;
                    case 2:
                        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.dado8, null);
                        DadoImageView.setImageDrawable(drawable);
                        tipodado=8;
                        break;
                    case 3:
                        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.dado10, null);
                        DadoImageView.setImageDrawable(drawable);
                        tipodado=10;
                        break;
                    case 4:
                        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.dado12, null);
                        DadoImageView.setImageDrawable(drawable);
                        tipodado=12;
                        break;
                    case 5:
                        drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.dado20, null);
                        DadoImageView.setImageDrawable(drawable);
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

    @Override
    public void onClick(View view) {
        int dado = Integer.valueOf(DadosValor.getText().toString());
        int mod = Integer.valueOf(ModifValor.getText().toString());
        switch (view.getId()) {

            case R.id.DadoRestaButton:

                if (dado > 1) {
                    dado--;
                    DadosValor.setText(String.valueOf(dado));
                }

                break;
            case R.id.ModifRestaButton:
                mod--;
                if (mod >= 0) {
                    ModifValor.setText("+" + String.valueOf(mod));
                }
                 else {
                    ModifValor.setText("-" + String.valueOf(Math.abs(mod)));

                }
                break;
            case R.id.DadoSumaButton:
                dado++;
                DadosValor.setText(String.valueOf(dado));
                break;
            case R.id.ModifSumaButton:
                mod++;
                if (mod >= 0) {
                    ModifValor.setText("+" + String.valueOf(mod));
                }
                else {
                    ModifValor.setText("-" + String.valueOf(Math.abs(mod)));

                }
                break;


        }
    }




}