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
import com.example.rodofroll.Objetos.OnTaskCompleted;

//Es un objeto que sirve para heredar de el lo vamos a utilizar por que tanto el la actividad login como en la actividad main se necesita desplegar un menu al seleccionar una imagen para cambiar esta
//Entrando en la galeria o haciendo una foto
public abstract class Actividad  extends AppCompatActivity {
    //Es la imagen que se va a remplazar
    protected ImageView imagenremp;

    //Creamos dos tipos de resultados que serviran para saber si hemos elegido una foto de la galeria o la hemos creado
    private static int PHOTO_RESULT=0,PICK_IMAGE=1 ;
    OnTaskCompleted onTaskCompleted;


//Metodo que sirve para desplegar el menu del cambio de imagen necesitamos una vista de tipo imageview y el objeto OnTaskCompleted
    public void MenuEmergenteImagen(final ImageView view, final OnTaskCompleted onTaskCompleted){
        //Creamos un popMenu y lo infalmos con el menu de imagen
        PopupMenu popupMenu = new PopupMenu(getApplicationContext(),view);
        popupMenu.getMenuInflater().inflate(R.menu.menuimagen,popupMenu.getMenu());

        //Igualamso nuestro listener
        this.onTaskCompleted=onTaskCompleted;
        //nuestra imagen principal coge la referencia de la imageview que le pasamos
        imagenremp =view;

        //escuchamos las opciones del menu
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId())
                {
                    //opcion foto creamos un intent que nos abre la camara
                    case R.id.opfoto:
                        Intent intento = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intento,PHOTO_RESULT);
                        break;

                    //opcion galareia creamos un intent que nos abre la galeria
                    case R.id.opgaleria:
                        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        galleryIntent.setType("image/*");
                        startActivityForResult(galleryIntent.createChooser(galleryIntent, "Seleccione la aplicacion"), PICK_IMAGE);
                        break;
                     //opcion borrar cambiamos la imagen del imageview a una por defecto
                    case R.id.opborrar:
                        imagenremp.setImageResource(R.drawable.mago);
                        imagenremp.setMaxWidth(imagenremp.getWidth());
                        imagenremp.setMaxHeight(imagenremp.getHeight());

                     //despertamos y ejecutamos el metodo de la interfaz pasada
                        if(onTaskCompleted!=null){
                           onTaskCompleted.onTaskCompleted();
                        }

                        break;

                }

                return true;
            }
        });
        //Mostramos el popup
        popupMenu.show();

    }
    //Dependiendo del resultado y del tipo pasado actuaremos
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap resultado;

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PHOTO_RESULT && resultCode == RESULT_OK) {

            if(data.hasExtra("data"))
            {
                resultado = ((Bitmap) data.getParcelableExtra("data"));
                imagenremp.setImageBitmap(resultado);
                if(onTaskCompleted!=null){
                    onTaskCompleted.onTaskCompleted();
                }



            }

        }
        if(requestCode==PICK_IMAGE&&resultCode==RESULT_OK){

            Uri selectedImage = data.getData();
            resultado= ConversorImagenes.getScaledBitmap(selectedImage,this);
            imagenremp.setImageBitmap(resultado);
            if(onTaskCompleted!=null){
                onTaskCompleted.onTaskCompleted();
            }

        }



    }

}
