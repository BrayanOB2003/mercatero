package icesi.edu.co.mercatero.model.shop

data class Product (
        var shop_id: String?,
        var name: String?,
        var description: String?,
        var price: Double?,
        var photo_id: String? = null
) {
        constructor() : this(null, null, null, null)
}