package com.example.rodofroll;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import javax.xml.datatype.Duration;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener  {

    TextInputLayout emailTextLayout;
    TextInputLayout passTextLayout;
    TextInputLayout pass2TextLayout;
    TextInputEditText emailEditText;
    TextInputEditText passEditText;
    TextInputEditText pass2EditText;
    TextView signOption;
    Button b;
    Acceso acceso;
    FirebaseAuth auth=FirebaseAuth.getInstance();
    DatabaseReference databaseReference;

    enum Acceso{
        Registrarse, Login

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailTextLayout =findViewById(R.id.emaillayout);
        passTextLayout = findViewById(R.id.passlayout);
        pass2TextLayout = findViewById(R.id.pass2layout);

        pass2TextLayout.setVisibility(View.GONE);

        emailEditText=findViewById(R.id.emaileditext);
        passEditText=findViewById(R.id.passeditext);
        pass2EditText=findViewById(R.id.pass2editext);

        AnyadirToggle(passEditText,passTextLayout);
        AnyadirToggle(pass2EditText,pass2TextLayout);

        signOption = findViewById(R.id.signoptiontextView);
        signOption.setOnClickListener(this);


        b = findViewById(R.id.button);
        b.setOnClickListener(this);

        acceso = Acceso.Login;

        databaseReference= FirebaseDatabase.getInstance().getReference();

    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            case R.id.signoptiontextView:
                if (signOption.getText().equals(getResources().getString(R.string.Registrarse))) {
                    signOption.setText(R.string.Login);

                    pass2TextLayout.setVisibility(View.VISIBLE);
                    pass2TextLayout.setPasswordVisibilityToggleEnabled(true);
                    b.setText(R.string.Registrarse);
                    acceso= Acceso.Registrarse;


                } else {
                    signOption.setText(R.string.Registrarse);
                    pass2TextLayout.setVisibility(View.GONE);
                    b.setText(R.string.Login);
                    pass2EditText.setError(null);
                    pass2EditText.setText(null);
                    acceso= Acceso.Login;

                }
                break;

            case R.id.button:

                boolean boolpass =true;
               passTextLayout.setPasswordVisibilityToggleEnabled(false);
               pass2TextLayout.setPasswordVisibilityToggleEnabled(false);

                if (acceso==Acceso.Registrarse) {

                   boolpass= Validacion.ValidarEdit(pass2EditText)&&Validacion.ValidarDosEdit(pass2EditText,passEditText);

                }
                if(Validacion.ValidarEdit(emailEditText)&Validacion.ValidarEdit(passEditText)&&boolpass){

                    if(acceso==Acceso.Registrarse){


                        try {
                            RegistrarUser(emailEditText.getText().toString(), passEditText.getText().toString(),v);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }


                    }else{


                        try {
                            LoginUser(emailEditText.getText().toString(),passEditText.getText().toString(),v);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
                //Se envia un intent para acceder a la siguiente actividad
                break;

        }


    }
    public void RegistrarUser(String email, String password,final View view) throws InterruptedException {
       view.setEnabled(false);

        Thread.sleep(2000);
       auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {

               if(task.isSuccessful()){
                   Snackbar.make(view, "Exito", Snackbar.LENGTH_LONG)
                           .show();

               }
               else{
                   Snackbar.make(view, "No se puedo registrar al usuario", Snackbar.LENGTH_LONG)
                           .show();
               }
               view.setEnabled(true);
           }
       });

    }
    public void LoginUser(String email, String password ,final View view) throws InterruptedException {
        view.setEnabled(false);

        Thread.sleep(2000);

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Snackbar.make(view, "Exito al entrar", Snackbar.LENGTH_LONG)
                            .show();
                }
                else{
                    Snackbar.make(view, "Usuario o contraseña incorrectos", Snackbar.LENGTH_LONG)
                            .show();
                }
                view.setEnabled(true);
            }
        });
    }
    public void AnyadirToggle(EditText e ,final TextInputLayout t){
        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                t.setPasswordVisibilityToggleEnabled(true);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
