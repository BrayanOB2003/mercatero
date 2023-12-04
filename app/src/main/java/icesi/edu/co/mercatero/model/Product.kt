package icesi.edu.co.mercatero.model

import java.io.Serializable

class Product:Serializable{
    var product_id: String = ""
    var name: String = ""
    var description: String = ""
    var price: String = ""
    var imageURL: String = ""
    var shop_id: String = ""
    var shopName: String = ""

    constructor()
    constructor(
        product_id: String,
        name: String,
        description: String,
        price: String,
        imageURL: String,
        shop_id: String,
        shopName: String
    ) {
        this.product_id = product_id
        this.name = name
        this.description = description
        this.price = price
        this.imageURL = imageURL
        this.shop_id = shop_id
        this.shopName = shopName
    }


}
