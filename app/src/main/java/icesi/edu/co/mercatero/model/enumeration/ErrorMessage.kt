package icesi.edu.co.mercatero.model.enumeration

enum class ErrorMessage(val message: String) {
    WRONG_FORMAT_PASSWORD("El correo está mal formado."),
    DUPLICATED_EMAIL("El correo está asociado a otra cuenta."),
    WEAK_PASSWORD("La clave es muy debil."),
    WRONG_AUTHENTICATION("Error de autenticacion.")
}