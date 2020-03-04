package com.example.rodofroll;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.rodofroll.Firebase.FireBaseUtils;
import com.example.rodofroll.Objetos.Dialogos;
import com.example.rodofroll.Objetos.MisMetodos;
import com.example.rodofroll.Objetos.Usuario;
import com.example.rodofroll.Objetos.Validacion;
import com.example.rodofroll.Vistas.Actividad;
import com.firebase.ui.auth.AuthUI;
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
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.storage.FirebaseStorage;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Random;


public class LoginActivity extends Actividad implements View.OnClickListener  {

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
    FirebaseAuth auth=FirebaseAuth.getInstance();

    ImageView imageView;
    LinearLayout marco;
    Dialog dialog;



    enum Acceso{
        Registrarse, Login

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        emailTextLayout = findViewById(R.id.emaillayout);
        apodoTextLayout = findViewById(R.id.apodolayout);
        passTextLayout = findViewById(R.id.passlayout);
        pass2TextLayout = findViewById(R.id.pass2layout);
        imageView = findViewById(R.id.UserimageView);
        marco= findViewById(R.id.marcoimagen);


        pass2TextLayout.setVisibility(View.GONE);
        apodoTextLayout.setVisibility(View.GONE);
        imageView.setVisibility(View.GONE);
        marco.setVisibility(View.GONE);

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
                MenuEmergenteImagen(imageView);
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
                    marco.setVisibility(View.VISIBLE);
                    pass2TextLayout.setPasswordVisibilityToggleEnabled(true);
                    b.setText(R.string.Registrarse);
                    acceso= Acceso.Registrarse;


                } else {
                    signOption.setText(R.string.Registrarse);
                    pass2TextLayout.setVisibility(View.GONE);
                    apodoTextLayout.setVisibility(View.GONE);
                    imageView.setVisibility(View.GONE);
                    b.setText(R.string.Login);
                    pass2EditText.setError(null);
                    pass2EditText.setText(null);
                    acceso= Acceso.Login;
                    marco.setVisibility(View.GONE);

                }
                break;

            case R.id.button:

                closeTecladoMovil();
                boolean boolpass =true;
               passTextLayout.setPasswordVisibilityToggleEnabled(false);
               pass2TextLayout.setPasswordVisibilityToggleEnabled(false);

                if (acceso==Acceso.Registrarse) {

                   boolpass= Validacion.ValidarDosEdit(pass2EditText,passEditText)&Validacion.ValidarEdit(apodoEditText);

                }
                if(Validacion.ValidarEdit(emailEditText)&Validacion.ValidarEdit(passEditText)&&boolpass){

                    if(acceso==Acceso.Registrarse){
                        try {
                            RegistrarUser(emailEditText.getText().toString(), apodoEditText.getText().toString(),passEditText.getText().toString(),MisMetodos.convertirImagenString(((BitmapDrawable)imageView.getDrawable()).getBitmap()),v);
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
    public void RegistrarUser(final String email, final String apodo, final String password, final String imagen, final View view) throws InterruptedException {
       view.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);


       auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
           @Override
           public void onComplete(@NonNull Task<AuthResult> task) {
               progressBar.setVisibility(View.GONE);
               if(task.isSuccessful()){

                   // Sign in is successful
                   //firebaseStorage=FirebaseStorage.g



                   FireBaseUtils.CrearRef();

               final String key = FireBaseUtils.getRef().child("key").push().getKey();


               FireBaseUtils.getRef().child("usuarios").child(FireBaseUtils.getUser().getUid()).child("id").setValue(key , new DatabaseReference.CompletionListener() {
                   @Override
                   public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                       FireBaseUtils.getRef().child("publico").child(key).setValue(new Usuario(apodo,email,imagen), new DatabaseReference.CompletionListener() {
                           @Override
                           public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {

                               FireBaseUtils.getRef().child("usuarios").child(FireBaseUtils.getUser().getUid()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {
                                   @Override
                                   public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                                           AddDevice(view);
                                       Intent intent=new Intent(LoginActivity.this,MainActivity.class);

                                       startActivity(intent);


                                   }

                                   @Override
                                   public void onCancelled(@NonNull DatabaseError databaseError) {

                                   }
                               });
                           }
                       });
                   }
               });

                   /*db.child("id").setValue(FireBaseUtils.getUser().getUid());

                   db.child("datos").setValue(new Usuario(apodo,email,imagen));
*/



                 //  db.setValue(new Usuario(apodo,email,imagen));

               }
               else{
                   Snackbar.make(view,ErroresAuth(task.getException()), Snackbar.LENGTH_LONG)
                           .show();



               }
               view.setEnabled(true);
           }
       });


    }
    public void LoginUser(String email, final String password , final View view) throws InterruptedException {
        view.setEnabled(false);
        progressBar.setVisibility(View.VISIBLE);



       auth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){

                    AddDevice(view);

                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);

                    startActivity(intent);


                }
                else{

                   Snackbar.make(view,ErroresAuth(task.getException()), Snackbar.LENGTH_LONG)
                            .show();
                    emailTextLayout.setPasswordVisibilityToggleEnabled(true);
                    passTextLayout.setPasswordVisibilityToggleEnabled(true);

                }
                view.setEnabled(true);
            }


        });


    }
    public void AnyadirToggle(EditText e ,final TextInputLayout t){
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
                 //   intent.putExtra("rol",0);
                    startActivity(intent);


                }
                else{
                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                  //  intent.putExtra("rol",1);
                    startActivity(intent);

                }




            }
        });
        dialogBuilder.setView(myView);
       dialog= dialogBuilder.show();



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


    @Override
    protected void onDestroy() {
        super.onDestroy();

      /*  if(dialog!=null){
            dialog.dismiss();
            dialog=null;
        }
*/

    }





    public void AddDevice(final View view) {
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {

                            return;

                        }

                        // Get new Instance ID token
                        final String token = task.getResult().getToken();


                        FireBaseUtils.CrearRef();
                        FireBaseUtils.getRef().child("usuarios").child(FireBaseUtils.getUser().getUid()).child("token").addListenerForSingleValueEvent(new ValueEventListener() {

                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                               String token1 = (String) dataSnapshot.getValue();
                                FireBaseUtils.getRef().child("usuarios").child(FireBaseUtils.getUser().getUid()).child("token").setValue(token);
                                       //FireBaseUtils.getRef().child("usuarios").child(FireBaseUtils.getUser().getUid()).child("token").setValue(token);
                               /* if(token1==null|| token.equals(token1)){

                                    FireBaseUtils.getRef().child("usuarios").child(FireBaseUtils.getUser().getUid()).child("token").setValue(token);
                                    CrearsAlertDialog();
                                }
                                else{
                                        Snackbar.make(view,"No ha cerrado la sesion en otro dispositivo", Snackbar.LENGTH_LONG).show();
                                }
*/
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {

                            }


                            });

                    }
                });


    }
}
