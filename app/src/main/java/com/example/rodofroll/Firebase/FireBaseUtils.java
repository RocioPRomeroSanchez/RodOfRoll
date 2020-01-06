package com.example.rodofroll.Firebase;

import com.example.rodofroll.Objetos.Combate;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public  class FireBaseUtils {

   static DatabaseReference ref;
   private static FireBaseUtils soyunico;
   private static FirebaseUser user;

    public static DatabaseReference getRef() {
        return ref;
    }

    public static void setRef(DatabaseReference ref) {
        FireBaseUtils.ref = ref;
    }

    public static FirebaseUser getUser() {
        return user;
    }


    private FireBaseUtils() {

        if(soyunico==null){
            user = FirebaseAuth.getInstance().getCurrentUser();


        }


    }

    static public void CrearRef()  {

        if(soyunico==null){
          soyunico= new FireBaseUtils();
        }

    }

    static  public void anyadircombate() throws Exception {

        if(soyunico!=null) {
            ref = FirebaseDatabase.getInstance().getReference();


            Combate combate = new Combate("Hola",true);
            ref.child(user.getUid()).child("combates").push().setValue(combate);

        }
        else{
            throw new Exception("Excemfasdf");
        }




    }
}
