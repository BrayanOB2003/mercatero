package icesi.edu.co.mercatero.model

import java.io.Serializable

class Product:Serializable{
    var product_id: String = ""
    var nombre: String = ""
    var descripcion: String = ""
    var price: Double = 0.0
    var imageURL: String = ""
    var shopName: String = ""

    constructor()
    constructor(
        product_id: String,
        name: String,
        description: String,
        price: Double,
        imageURL: String,
        shopName: String
    ) {
        this.product_id = product_id
        this.nombre = name
        this.descripcion = description
        this.price = price
        this.imageURL = imageURL
        this.shopName = shopName
    }


}