package icesi.edu.co.mercatero.model.shop

data class Order(
    val id_Client: String,
    val id_Shop: String,
    val address: String,
    val products: Map<String, Int>
) {
    fun getIdProducts(): Set<String> {
        return products.keys
    }

    fun getAmountProduct(id: String) : Int? {
        return products[id]
    }
}
