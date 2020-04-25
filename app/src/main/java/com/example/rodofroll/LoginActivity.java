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


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setTheme(R.style.AppTheme);


        emailTextLayout = findViewById(R.id.emaillayout);
        apodoTextLayout = findViewById(R.id.apodolayout);
        passTextLayout = findViewById(R.id.passlayout);
        pass2TextLayout = findViewById(R.id.pass2layout);
        imageView = findViewById(R.id.UserimageView);


        pass2TextLayout.setVisibility(View.GONE);
        apodoTextLayout.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);


        emailEditText = findViewById(R.id.emaileditext);
        apodoEditText = findViewById(R.id.apodoeditext);
        passEditText = findViewById(R.id.passeditext);
        pass2EditText = findViewById(R.id.pass2editext);


        AnyadirToggle(passEditText, passTextLayout);
        AnyadirToggle(pass2EditText, pass2TextLayout);

        signOption = findViewById(R.id.signoptiontextView);
        signOption.setOnClickListener(this);


        b = findViewById(R.id.button);
        b.setOnClickListener(this);

        acceso = Acceso.Login;
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);


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

            case R.id.button:

                closeTecladoMovil();
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

    public void RegistrarUser(final String email, final String apodo, final String password, final String imagen, final View view) throws InterruptedException {
        view.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);


        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {

                    // Sign in is successful
                    //firebaseStorage=FirebaseStorage.g


                    // FireBaseUtils.CrearRef();

                    final String key = FirebaseUtilsV1.GeneradorKeys();

                    FirebaseUtilsV1.RegistroUsuario(key,new Usuario(apodo,imagen,key),LoginActivity.this);

                } else {
                    Snackbar.make(view, ErroresAuth(task.getException()), Snackbar.LENGTH_LONG)
                            .show();


                }
                view.setEnabled(true);
            }
        });


    }

    public void LoginUser(String email, final String password, final View view) throws InterruptedException {
        view.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);


        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if (task.isSuccessful()) {


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

