package icesi.edu.co.mercatero.model.shop

data class Order(
    val client_id: String?,
    val shop_id: String?,
    val address: String?,
    val products: Map<String, Int>?
) {
    constructor() : this("", "", "", null)

    fun getIdProducts(): Set<String>? {
        return products?.keys
    }

    fun getAmountProduct(id: String) : Int? {
        return products?.get(id)
    }
}
