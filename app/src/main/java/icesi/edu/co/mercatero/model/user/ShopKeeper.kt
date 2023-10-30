package icesi.edu.co.mercatero.model.user

data class ShopKeeper (
        var shopKeeper_id: String,
        var name: String,
        var lastName: String,
        var CC: Long,
        var number_phone: String,
        var email: String
        )