

//Importamos los modulos firebase functions
const functions = require('firebase-functions');
//Importamos el modulo de administrador
const admin = require('firebase-admin');


admin.initializeApp(functions.config().firebase);

//Es una funcion que crea el formato que tedra la notificacion que enviaremos
function crearNotificacion(titulo,mensaje,valor,id){
  var payload = {
    data:{
      title:titulo,
      body: mensaje+" "+valor
    }

  };
//Escribimos en la base de datos la notificacion para que quede registrada para el usuario
  admin.database().ref("notificaciones/"+id).push().set(titulo+" "+mensaje+" "+valor);
  return payload;
}
//Devolvemos el token del idpublico que nos viene por argumento
async function buscarTokenDispositivo(idpublico){

  var db = admin.database();
  //buscamos el id igual al argumento
  var ref = db.ref("usuarios").orderByChild("id").equalTo(idpublico);
//obtenemos el valor del usuario
 var snapdonde = await ref.once("value");
 //obtenemos la id primera que corresponde al uid
  var iddonde = Object.keys(snapdonde.val())[0];
//una vez obtenido el uid volvemos a preguntar y obtenemos el token
  ref = db.ref('usuarios/'+iddonde);
  var token = (await ref.once("value")).val().token;
  return token;


}
//Es un metodo que se ejecuta cuando se elimina un personaje por cualquier motivo eliminacion de usuario, eliminacion de personaje como tal o se eliminan datos del personaje en referencia al combate
async function EliminarDesdePersonaje(masterid,combateid,personajeid){

  var db = admin.database();
  //se obtiene la id correspondiente a ese personaje en el combate
  var ref = db.ref("combates/"+masterid+"/"+combateid+"/ordenturno").orderByChild("personajekey").equalTo(personajeid);
//se obtiene la key principal
  var valor = await ref.once("value");
  var personajekey=  Object.keys(valor.val())[0];

//se pone a nulo para borrarlo
  admin.database().ref("combates/"+masterid+"/"+combateid+"/ordenturno/"+personajekey).set(null);
  return null;
}
//Es un metodo que se ejecuta cuando se borra un combate
async function EliminarDesdeCombate(personajekey,usuariokey,combateid){

  var db = admin.database();
  //se obtiene la id del combate del personaje pasado por argumento
  var ref = db.ref("publico/"+usuariokey+"/personajes/"+personajekey+"/combates").orderByChild("combateid").equalTo(combateid);

  var valor = await ref.once("value");
  //se obtiene la key principal
  var combatekey=  Object.keys(valor.val())[0];


//se pone a nulo
  admin.database().ref("publico/"+usuariokey+"/personajes/"+personajekey+"/combates/"+combatekey).set(null);
  return null;
}

//Esta funcion se genera cuando se borra un combate desde el personaje
exports.DeleteCombateDesdePersonaje = functions.database.ref('/publico/{id}/personajes/{idpersonaje}/combates/{idcombate}').onDelete((snapshot,context)=>
{
  var idmaster = snapshot.val().masterid;
  var idcombate = snapshot.val().combateid;
  var personajeid = context.params.idpersonaje;

  EliminarDesdePersonaje(idmaster, idcombate,personajeid);


});
//Esta funcion se genera cuando se borra un personaje desde el combate
exports.DeletePersonajeCombate = functions.database.ref('/combates/{id}/{idcombate}/ordenturno/{idpersonaje}').onDelete((snapshot,context)=>
{
    if(snapshot.val().ismonster === false){
        EliminarDesdeCombate(snapshot.val().personajekey,snapshot.val().usuariokey,context.params.idcombate);

    }



});
//Esta funcion se genera cuando se borra el combate
exports.DeleteCombate = functions.database.ref('/combates/{id}/{idcombate}')
  .onDelete((snapshot,context)=>{
    var  combatientes= snapshot.val().ordenturno;


//Se recorre en un bucle todos los personajes enlazados a ese combate
    Object.values(combatientes).forEach(function(childSnapshot){
      var personajekey = childSnapshot.personajekey;
      var usuariokey = childSnapshot.usuariokey;



      EliminarDesdeCombate(personajekey,usuariokey,context.params.idcombate);



    });

    return null;


  });

  //Esta funcion se genera cuando se borra un personaje
exports.DeletePersonaje = functions.database.ref('/publico/{id}/personajes/{idpersonaje}')
  .onDelete((snapshot,context)=>{
    var combates = snapshot.val().combates;

    console.log(Object.values(combates));
    //Se recorre en un bucle todos los combates enlazados a ese personaje
    Object.values(combates).forEach(function(childSnapshot) {

      var combateid = childSnapshot.combateid;
      var masterid = childSnapshot.masterid;

      EliminarDesdePersonaje(masterid,combateid,context.params.idpersonaje);

    });



    return null;
  });

//Se genera cuando se elimina a un usuario
  exports.DeleteUsuarios = functions.database.ref('/usuarios/{id}/').onDelete(async(snapshot,context)=>{

      var db = admin.database();

      var id = snapshot.val().id;
      console.log(id);

//borramos todo lo asociado a el
        admin.database().ref("/combates/"+id).set(null);
        admin.database().ref("/notificaciones/"+id).set(null);
        admin.database().ref("/publico/"+id).set(null);


  });

//Se genera cuando creamos un nuevo combate
  exports.NuevoCombate = functions.database.ref('/combates/{publicoID}/{idcombate}/ordenturno/')
  .onCreate((snapshot,context)=>{
    //Creamos el hijo ronda
    admin.database().ref("/combates/"+context.params.publicoID+"/"+context.params.idcombate+"/ronda").set(0);
  });
//Se genera cuando creamos un nuevo combatiente
exports.NuevoCombatiente = functions.database.ref('/combates/{publicoID}/{idcombate}/ordenturno/{id}')
  .onCreate( async(snapshot,context)=>{
//Esto sirve para informar al propietario del combate que ha recibido un nuevo personaje
try{

  var db = admin.database();
  var ref = db.ref('usuarios/'+context.auth.uid);
  var id;

var snapdesenc = await ref.once("value");

id = snapdesenc.val().id;

ref = db.ref('publico/'+id);
snapdesenc = await ref.once("value");
var token = await buscarTokenDispositivo(context.params.publicoID);

console.log(token);


var titulo = "Tienes un nuevo dato de "+ snapdesenc.val().nombre;

ref = db.ref("publico/"+ snapshot.val().usuariokey+"/personajes/"+snapshot.val().personajekey)
var valorpersonaje = (await ref.once("value")).val();

//Aqui creamos la notificacion
var values = crearNotificacion(titulo,"te ha pasado este personaje",valorpersonaje.biografia.nombre,context.params.publicoID);


//Enviamos la notificacion al usuario del combate
return admin.messaging().sendToDevice(token,values);

}
catch(error){
  console.log(error);
}

return null;

});
//Esta funcion se ejecuta cuando el hijo avisar de un combatiente ha cambiado
exports.AvisarPersonaje = functions.database.ref('combates/{publicoId}/{combateid}/ordenturno/{idpersonaje}/avisar')
.onWrite(async(snapshot,context)=>{
//Se comprueba que el cambio no se deba a una eliminacion de datos
if(!snapshot.after.exists()){
  return null;
}
//Se comprueba que el nuevo valor sea true si es asi se creara una notificacion al propietario del personaje
  if(snapshot.after.val()===true){

  var retorno = null;
      //Sacamos del personaje su publicoID y le notificamos

      var db = admin.database();
      var ref = db.ref("combates/"+context.params.publicoId+"/"+context.params.combateid+"/ordenturno/"+context.params.idpersonaje);
      var personajevalor = (await ref.once("value")).val();

      var refcombatee = db.ref("combates/"+context.params.publicoId+"/"+context.params.combateid+"/nombre");
      var combate = (await refcombatee.once("value")).val();
      console.log(combate);
      var idpublico = personajevalor.usuariokey;

//obtenemos el token del jugador al que se tiene que avisar
      var token = await buscarTokenDispositivo(idpublico);

//se vuelve a poner a false el valor avisar
        admin.database().ref("combates/"+context.params.publicoId+"/"+context.params.combateid+"/ordenturno/"+context.params.idpersonaje+"/avisar").set(false);
            //Ponemos a false el valor
//se crea la notificacion y se guarda esta en notificaciones
     var payload =crearNotificacion("Es tu turno","en el combate",combate,idpublico);



//solo se envia notificacion a los usuarios que esten activos es decir que su token de dispositivo no sea nulo
      if(token!==null){
        retorno = admin.messaging().sendToDevice(token,payload);

      }
      return retorno;
}



  });
