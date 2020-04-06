package com.example.rodofroll.Firebase;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtilsV1 {

    private static FirebaseUser user;
    private static String key=null;
    static DatabaseReference ref;

    private static final FirebaseUtilsV1 ourInstance = new FirebaseUtilsV1();

    public static FirebaseUtilsV1 getInstance() {
        return ourInstance;
    }

    private FirebaseUtilsV1() {
        user = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference();
    }

    
}
