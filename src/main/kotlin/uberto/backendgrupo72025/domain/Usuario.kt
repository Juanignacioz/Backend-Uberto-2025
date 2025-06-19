package uberto.backendgrupo72025.domain

import jakarta.persistence.*
import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import uberto.backendgrupo72025.dto.UsuarioLoginDTO

@MappedSuperclass
abstract class Usuario(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: String? = null,
    @Column(length = 25)
    var nombre: String = "",
    @Column(length = 25)
    var apellido: String = "",
    @Column(length = 2)
    var edad: Int = 0,
    @Column(unique = true)
    val username: String = "",
    @Column(length = 20)
    val contrasenia: String = "",
    @Column(length = 10)
    var telefono: Int = 0,
    @Column(nullable = false)
    val esChofer: Boolean = false,
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val rol: ROLES = ROLES.VIAJERO,
    @Column
    var foto: String = ""
) {

    fun nombreYApellido() = "$nombre $apellido"


    fun accesoUsuario(user: UsuarioLoginDTO): Boolean {
        return user.usuario == username && user.contrasenia == contrasenia
    }

    // Validaciones
    fun esValidoNombre() = nombre.isNotEmpty()
    fun validarNombre() {
        if (!esValidoNombre()) throw RuntimeException("El nombre esta vacio")
    }

    fun esValidoApellido() = apellido.isNotEmpty()
    fun validarApellido() {
        if (!esValidoApellido()) throw RuntimeException("El apellido esta vacio")
    }

    fun esValidoUsername() = username.isNotEmpty()
    fun validarUsername() {
        if (!esValidoUsername()) throw RuntimeException("El username esta vacio")
    }

    fun esValidoContrasenia() = contrasenia.isNotEmpty()
    fun validarContrasenia() {
        if (!esValidoContrasenia()) throw RuntimeException("La contraseña esta vacio")
    }

    fun esValidaLaEdad() = edad > 0
    fun validarEdad() {
        if (!esValidaLaEdad()) throw RuntimeException("La edad ingresada no puede ser menor o igual a 0")
    }

    fun esValidoElTelefono() = telefono > 0
    fun validarTelefono() {
        if (!esValidoElTelefono()) throw RuntimeException("El telefono ingresado no puede ser menor o igual a 0")
    }

    abstract fun validacionesPorUsuario()

    fun validar() {
        validarNombre()
        validarApellido()
        validarContrasenia()
        validarUsername()
        validarEdad()
        validarTelefono()
        validacionesPorUsuario()
    }
}
