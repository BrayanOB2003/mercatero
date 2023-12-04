package icesi.edu.co.mercatero.model

import java.io.Serializable

class Shop:Serializable{

        var shop_id: String? = ""
        var name: String = ""
        var address: String = ""
        var email: String = ""
        var phone: String = ""
        var imageURL: String? = ""


        constructor(
                shop_id: String?,
                name: String,
                address: String,
                email: String,
                phone: String,
                imageUrl: String?
        ) {
                this.shop_id = shop_id
                this.name = name
                this.address = address
                this.email = email
                this.phone = phone
                this.imageURL = imageUrl
        }

        constructor()

}