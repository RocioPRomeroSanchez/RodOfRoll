package com.example.rodofroll;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rodofroll.Objetos.Validacion;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseNetworkException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


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

                closeTecladoMovil();
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

                     CrearsAlertDialog();

               }
               else{
                   Snackbar.make(view,ErroresAuth(task.getException()), Snackbar.LENGTH_LONG)
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

                    CrearsAlertDialog();

                }
                else{

                   Snackbar.make(view,ErroresAuth(task.getException()), Snackbar.LENGTH_LONG)
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
    public void CrearsAlertDialog(){

        final AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(LoginActivity.this);
        LayoutInflater inflater = LoginActivity.this.getLayoutInflater();
        final View myView = inflater.inflate(R.layout.elegirolayout, null);

        final Spinner spin = myView.findViewById(R.id.elecrolnspinner);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this,R.array.roles, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spin.setAdapter(adapter);

        Button button =myView.findViewById(R.id.aceptarrolbutton);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(spin.getSelectedItemPosition()==0){
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("rol",0);
                    startActivity(intent);


                }
                else{
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("rol",1);
                    startActivity(intent);

                }



            }
        });
        dialogBuilder.setView(myView);
        dialogBuilder.show();



    }

    public String ErroresAuth(Exception f){

        if (FirebaseNetworkException.class.equals(f.getClass())) {
            return  "Error de red";

        } else if (FirebaseAuthInvalidCredentialsException.class.equals(f.getClass())) {
            return  "Contraseña incorrecta";

        } else if (FirebaseAuthUserCollisionException.class.equals(f.getClass())) {

            return  "Usuario ya existente";
        }
        else if(FirebaseAuthInvalidUserException.class.equals(f.getClass())){
            return  "Usuario inexistente";
        }

      return "Error";
    }

    public void closeTecladoMovil(){
        View view = this.getCurrentFocus();
        if(view!=null){
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);

        }
    }
}
