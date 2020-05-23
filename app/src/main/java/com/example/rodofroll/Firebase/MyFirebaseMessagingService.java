package com.example.rodofroll.Firebase;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.rodofroll.R;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Random;

/**
 * Es el servicio que usaremos para recivir los mensajes que nos vengan desde las funciones de firebase,aqui construiremos como tal la notificacion
 */
public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = "FirebaseMessagingServce";
    int cont=0;


    //Es un metodo que se activa cuando llega un mensaje remoto
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        String notificationTitle = null, notificationBody = null;

        //miramos que el mensaje no llegue vacio y creamos dos varibles que corresponderan con el titulo de la notificacion y el cuerpo
        if(remoteMessage.getData().size()>0){

            //Guardamos el titulo
            notificationTitle = remoteMessage.getData().get("title");
            //Guardamos el cuerpo de los datos que nos llegan
            notificationBody =remoteMessage.getData().get("body");
        }

       /*if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
            notificationTitle = remoteMessage.getNotification().getTitle();
            notificationBody = remoteMessage.getNotification().getBody();
        }*/

        sendNotification(notificationTitle, notificationBody);
    }


    private void sendNotification(String notificationTitle, String notificationBody) {

        NotificationManager nm;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //Creamos el nombre del canal
            CharSequence name = "Notificaciones";
            String description = "Nuevos datos ";

            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("RodRoll", name, importance);
            channel.setDescription(description);

            nm= getSystemService(NotificationManager.class);
            nm.createNotificationChannel(channel);

            NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplication(), "RodRoll")
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