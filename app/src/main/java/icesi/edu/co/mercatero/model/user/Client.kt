package icesi.edu.co.mercatero.model.user

import java.io.Serializable

class Client:Serializable{

        var client_id: String? = ""
        var name: String? = ""
        var lastName: String? = ""
        var CC: String? = ""
        var address: String? = ""
        var phone: String? = ""
        var email: String? = ""
        var imageURL: String? = ""


     constructor(
         client_id: String?,
         name: String?,
         lastName: String?,
         CC: String?,
         address: String?,
         phone: String?,
         email: String?,
         imageURL: String?
     ) {
         this.client_id = client_id
         this.name = name
         this.lastName = lastName
         this.CC = CC
         this.address = address
         this.phone = phone
         this.email = email
         this.imageURL = imageURL
     }

     constructor()
 }