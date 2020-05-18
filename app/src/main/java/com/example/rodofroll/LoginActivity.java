package com.example.rodofroll;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.rodofroll.Firebase.FirebaseUtilsV1;
import com.example.rodofroll.Objetos.ConversorImagenes;
import com.example.rodofroll.Objetos.Usuario;
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
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

//Actividad de Login gestiona el acceso a la aplicacion
public class LoginActivity extends Actividad implements View.OnClickListener {

    TextInputLayout emailTextLayout;
    TextInputLayout apodoTextLayout;
    TextInputLayout passTextLayout;
    TextInputLayout pass2TextLayout;
    TextInputEditText emailEditText;
    TextInputEditText apodoEditText;
    TextInputEditText passEditText;
    TextInputEditText pass2EditText;
    TextView signOption;
    ProgressBar progressBar;
    Button b;
    Acceso acceso;
    FirebaseAuth auth = FirebaseAuth.getInstance();

    ImageView imageView;

    Dialog dialog;


    enum Acceso {
        Registrarse, Login

    }


    //evento que se realiza al crearse la actividad

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //Al inicializar la actividad se habia cargado un tema distinto para mostrar un splash screen , aqui lo ponemos al tema que deberia ser
        setTheme(R.style.AppTheme);


        //Se empezara mostrando la opcion de Login en vez de la de registrarse
        emailTextLayout = findViewById(R.id.emaillayout);
        apodoTextLayout = findViewById(R.id.apodolayout);
        passTextLayout = findViewById(R.id.passlayout);
        pass2TextLayout = findViewById(R.id.pass2layout);
        imageView = findViewById(R.id.UserimageView);
        emailEditText = findViewById(R.id.emaileditext);
        apodoEditText = findViewById(R.id.apodoeditext);
        passEditText = findViewById(R.id.passeditext);
        pass2EditText = findViewById(R.id.pass2editext);


        pass2TextLayout.setVisibility(View.GONE);
        apodoTextLayout.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);

        //Se añade el simbolo del ojo a los siguientes editText
        AnyadirToggle(passEditText, passTextLayout);
        AnyadirToggle(pass2EditText, pass2TextLayout);

        signOption = findViewById(R.id.signoptiontextView);
        signOption.setOnClickListener(this);

        b = findViewById(R.id.button);
        b.setOnClickListener(this);

        acceso = Acceso.Login;
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


        //Al cliclear a la imagen se llamara al metodo MenuEmergente se le pasara el onTaskCompleted a nulo porque el cambio de la imagen en la base de datos se hara desde aqui
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MenuEmergenteImagen(imageView,null);
            }
        });


    }

    @Override
    public void onClick(View v) {


        switch (v.getId()) {

            //Al teclear al texto de Login/Registrar se mostraran o se ocultaran distintos elementos
            case R.id.signoptiontextView:
                if (signOption.getText().equals(getResources().getString(R.string.Registrarse))) {
                    signOption.setText(R.string.Login);

                    pass2TextLayout.setVisibility(View.VISIBLE);
                    apodoTextLayout.setVisibility(View.VISIBLE);
                    imageView.setVisibility(View.VISIBLE);

                    pass2TextLayout.setPasswordVisibilityToggleEnabled(true);
                    b.setText(R.string.Registrarse);
                    acceso = Acceso.Registrarse;


                } else {
                    signOption.setText(R.string.Registrarse);
                    pass2TextLayout.setVisibility(View.GONE);
                    apodoTextLayout.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                    b.setText(R.string.Login);
                    pass2EditText.setError(null);
                    pass2EditText.setText(null);
                    acceso = Acceso.Login;


                }
                break;
             //Al clickear al boton de aceptar se comprobara en que estado estamos si en Registrar o en Login
            case R.id.button:

                //se cerrara el teclado del movil ya que ahora no sera necesario
                closeTecladoMovil();
                //boolpass comprueba que todos los editext que deben verse en las dos opciones esten correctos
                boolean boolpass = true;


                if (acceso == Acceso.Registrarse) {

                    boolpass = Validacion.ValidarDosEdit(pass2EditText, pass2TextLayout, passEditText, passTextLayout) & Validacion.ValidarEdit(apodoEditText, apodoTextLayout);

                }
                if (Validacion.ValidarEdit(emailEditText, emailTextLayout) & Validacion.ValidarEdit(passEditText, passTextLayout) && boolpass) {

                    if (acceso == Acceso.Registrarse) {
                        try {
                            RegistrarUser(emailEditText.getText().toString(), apodoEditText.getText().toString(), passEditText.getText().toString(), ConversorImagenes.convertirImagenString(((BitmapDrawable) imageView.getDrawable()).getBitmap()), v);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            LoginUser(emailEditText.getText().toString(), passEditText.getText().toString(), v);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }


                break;

        }


    }

    //Es un metodo que registra al usuario en la base de datos
    public void RegistrarUser(final String email, final String apodo, final String password, final String imagen, final View view) throws InterruptedException {
        view.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);


        //Se crea un usario con email y contraseña y se se comprueba que se haya actualizado en firebase si ha dado un error se muestra un snackbar mostrando el error
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    final String key = FirebaseUtilsV1.GeneradorKeys();
                    if(FirebaseUtilsV1.getInstance()!=null){
                        FirebaseUtilsV1.RecreateInstance();

                    }
                    //Se creara el perfil publico del usuario y se introducira el token del dispositivo actual
                    FirebaseUtilsV1.RegistroUsuario(key,new Usuario(apodo,imagen,key),LoginActivity.this);

                } else {
                    Snackbar.make(view, ErroresAuth(task.getException()), Snackbar.LENGTH_LONG)
                            .show();


                }
                view.setEnabled(true);
            }
        });


    }

    //Es un metodo que loguea al usuario en la base de datos
    public void LoginUser(String email, final String password, final View view) throws InterruptedException {
        view.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);

        //Se comprueba el usuario si ha dado un error se muestra un snackbar mostrando el error
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    if(FirebaseUtilsV1.getInstance()!=null){
                        FirebaseUtilsV1.RecreateInstance();

                    }
                    //Se introducira el token del dispositivo actual
                    FirebaseUtilsV1.AddDevice(LoginActivity.this);


                } else {

                    Snackbar.make(view, ErroresAuth(task.getException()), Snackbar.LENGTH_LONG)
                            .show();
                    passTextLayout.setPasswordVisibilityToggleEnabled(true);

                }
                view.setEnabled(true);
            }


        });


    }

    //Metodo que controla la visibilidad de los passwords
    public void AnyadirToggle(EditText e, final TextInputLayout t) {
        e.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                t.setError(null);
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



    //Metodo que devuelve el tipo de error
    public String ErroresAuth(Exception f) {

        if (FirebaseNetworkException.class.equals(f.getClass())) {
            return "Error de red";

        } else if (FirebaseAuthInvalidCredentialsException.class.equals(f.getClass())) {
            return "Contraseña incorrecta";

        } else if (FirebaseAuthUserCollisionException.class.equals(f.getClass())) {

            return "Usuario ya existente";
        } else if (FirebaseAuthInvalidUserException.class.equals(f.getClass())) {
            return "Usuario inexistente";
        }

        return "Error";
    }

    //Metodo para el cierre del teclado
    public void closeTecladoMovil() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();


    }
}

