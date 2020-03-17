package com.example.rodofroll.Objetos;

import android.text.InputType;
import android.util.Patterns;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;

public class Validacion {
     public static boolean ValidarEdit(EditText editText) {

         String s = editText.getText().toString();
         if(s.isEmpty()){

             editText.setError("Campo Requerido");


             return false;
         }
         else{
             boolean b=true;

             switch (editText.getInputType()){

                 case InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS+1:
                     if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                         editText.setError("No coincide con un email");
                         b=false;
                     }


                     break;
                 case  InputType.TYPE_TEXT_VARIATION_PASSWORD+1:
                     if(!s.matches("^(?=\\w*\\d)(?=\\w*[A-Z])(?=\\w*[a-z])\\S{8,16}$"))
                     {
                         editText.setError("La contraseña debe tener al entre 8 y 16 caracteres, al menos un dígito, al menos una minúscula y al menos una mayúscula");

                         b=false;
                     }


                     break;
             }

             return b;

         }


    }

    public static  boolean ValidarDosEdit(TextInputEditText editText, TextInputEditText editText2){

         boolean bol = editText.getText().toString().equals(editText2.getText().toString());
         if(!bol){
             editText.setError("Las contraseñas no coinciden");
         }
         return bol ;
    }
}
