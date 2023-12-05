package icesi.edu.co.mercatero.model

data class Order(
    var order_id: String?,
    var client_id: String?,
    var shop_id: String?,
    var address: String?,
    var price: String?,
    var products: List<OrderProduct>?,
    var status: String?
)

data class OrderProduct(
    val product_id: String,
    val quantity: Int
)