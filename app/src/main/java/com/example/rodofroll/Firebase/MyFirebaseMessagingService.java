package com.example.rodofroll.Firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.example.rodofroll.R;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

/**
 * Es el servicio que usaremos para recivir los mensajes que nos vengan desde las funciones de firebase,aqui construiremos como tal la notificacion
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessagingServce";
    int cont=0;


    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String notificationTitle = null, notificationBody = null;

        if(remoteMessage.getData().size()>0){

            notificationTitle = remoteMessage.getData().get("title");
            notificationBody =remoteMessage.getData().get("body");
        }

       if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            notificationTitle = remoteMessage.getNotification().getTitle();
            notificationBody = remoteMessage.getNotification().getBody();
        }

        sendNotification(notificationTitle, notificationBody);
    }


    private void sendNotification(String notificationTitle, String notificationBody) {

        NotificationManager nm;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Notificacion de master";
            String description = "Nuevos datos en tus combates creados";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("Master", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            nm= getSystemService(NotificationManager.class);
            nm.createNotificationChannel(channel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplication(), "Master")
                    .setSmallIcon(R.drawable.carta)
                    .setContentTitle(notificationTitle)
                    .setContentText(notificationBody)
                    .setVibrate(new long[]{500, 1000})

                    .setPriority(NotificationCompat.PRIORITY_HIGH);


            nm.notify(cont, builder.build());
            if(cont<20){
                cont++;
            }
            else{
                cont=0;
            }


        }
    }
}