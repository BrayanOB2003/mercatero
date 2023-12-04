package icesi.edu.co.mercatero.model.shop

data class Order(
    val client_id: String?,
    val shop_id: String?,
    val address: String?,
    val products: List<OrderProduct>?
) {
    constructor() : this("", "", "", emptyList())

    fun getIdProducts(): List<String>? {
        return products?.map { it.product_id }
    }

    fun getAmountProduct(id: String) : Int? {
        return products?.size
    }
}

data class OrderProduct(
    val product_id: String,
    val quantity: Int
)