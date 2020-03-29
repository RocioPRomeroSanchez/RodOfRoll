package com.example.rodofroll.Objetos;

import android.text.InputType;
import android.util.Patterns;
import android.widget.EditText;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class Validacion {
     public static boolean ValidarEdit(EditText editText, TextInputLayout tl) {

         String s = editText.getText().toString();
         if(s.isEmpty()){

            tl.setError("Campo Requerido");


             return false;
         }
         else{
             boolean b=true;

             switch (editText.getInputType()){

                 case InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS+1:
                     if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                         tl.setError("No coincide con un email");
                         b=false;
                     }


                     break;
                 case  InputType.TYPE_TEXT_VARIATION_PASSWORD+1:
                     if(!s.matches("^(?=\\w*\\d)\\S{8,16}$"))
                     {
                         tl.setError("Al menos un número,desde 8 a 16 carácteres sin espacios en blanco");


                         b=false;
                     }


                     break;
             }

             return b;

         }


    }

    public static boolean ValidarEdit(EditText editText){
        String s = editText.getText().toString();
        if(s.isEmpty()){

            editText.setError("Campo Requerido");


            return false;
        }
        else{
            return true;
        }
    }

    public static  boolean ValidarDosEdit(TextInputEditText editText, TextInputLayout tl1, TextInputEditText editText2, TextInputLayout tl2){

         boolean bol = editText.getText().toString().equals(editText2.getText().toString());
         if(!bol){
             tl1.setError("Las contraseñas no coinciden");
         }
         return bol ;
    }
}
