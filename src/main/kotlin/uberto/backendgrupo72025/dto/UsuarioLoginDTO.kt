package uberto.backendgrupo72025.dto


import uberto.backendgrupo72025.domain.Usuario

data class UsuarioLoginDTO(
    val usuario : String,
    val contrasenia : String,
)

data class LoginDTO(
    val id : String?,
    val esChofer: Boolean
)

fun Usuario.toLoginDTO() = LoginDTO(
    id =  id,
    esChofer = esChofer
)


