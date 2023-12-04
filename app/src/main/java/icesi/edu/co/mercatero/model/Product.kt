package icesi.edu.co.mercatero.model

data class Product(
    val product_id: String,
    var name: String,
    var description: String,
    var price: String,
    var imageURL: String,
    var shopName: String
)
