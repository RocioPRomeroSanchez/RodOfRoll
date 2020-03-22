package com.example.rodofroll;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.PopupMenu;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rodofroll.Objetos.ConversorImagenes;

public class Actividad  extends AppCompatActivity {
    public ImageView remp;

    private static int PHOTO_RESULT=0,PICK_IMAGE=1 ;


    public void MenuEmergenteImagen(final ImageView view){
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
        popupMenu.getMenuInflater().inflate(R.menu.menuimagen,popupMenu.getMenu());


        remp=view;

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    case R.id.opfoto:
                        Intent intento = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intento,PHOTO_RESULT);
                        break;
                    case R.id.opgaleria:
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent.createChooser(galleryIntent, "Seleccione la aplicacion"), PICK_IMAGE);


                        break;
                    case R.id.opborrar:

                        view.setImageResource(R.drawable.mago);
                        break;

                }

                return true;
            }
        });
        popupMenu.show();

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap resultado;

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_RESULT && resultCode == RESULT_OK) {

            if(data.hasExtra("data"))
            {
                resultado = ((Bitmap) data.getParcelableExtra("data"));
                remp.setImageBitmap(resultado);

            }

        }
        if(requestCode==PICK_IMAGE&&resultCode==RESULT_OK){

            Uri selectedImage = data.getData();
            resultado= ConversorImagenes.getScaledBitmap(selectedImage,this);
            remp.setImageBitmap(resultado);
        }


    }

}