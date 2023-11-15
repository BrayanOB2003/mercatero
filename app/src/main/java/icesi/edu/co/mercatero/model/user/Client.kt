package icesi.edu.co.mercatero.model.user

import java.io.Serializable

class Client:Serializable{


        var client_id: String = ""
        var nombre: String = ""
        var apellido: String = ""
        var cc: String = ""
        var direccion: String = ""
        var celular: String = ""
        var email: String = ""


     constructor(
         client_id: String,
         nombre: String,
         apellido: String,
         cc: String,
         direccion: String,
         celular: String,
         email: String
     ) {
         this.client_id = client_id
         this.nombre = nombre
         this.apellido = apellido
         this.cc = cc
         this.direccion = direccion
         this.celular = celular
         this.email = email
     }

     constructor()


 }