package icesi.edu.co.mercatero.model.user

data class Client (
        var client_id: String?,
        var name: String?,
        var lastName: String?,
        var CC: Long?,
        var address: String?,
        var number_phone: String?,
        var email: String?,
        var imageURL: String?
        )