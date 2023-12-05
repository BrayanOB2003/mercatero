package icesi.edu.co.mercatero.model

import java.io.Serializable

class Order:Serializable{
    var order_id: String = ""
    var client_id: String = ""
    var shop_id: String = ""
    var address: String = ""
    var price: Double = 0.0
    var products: ArrayList<String> = arrayListOf()
    var status: String = ""


    constructor(
        order_id: String,
        client_id: String,
        shop_id: String,
        address: String,
        price: Double,
        status: String
    ) {
        this.order_id = order_id
        this.client_id = client_id
        this.shop_id = shop_id
        this.address = address
        this.price = price
        this.status = status
    }

    constructor()


}
