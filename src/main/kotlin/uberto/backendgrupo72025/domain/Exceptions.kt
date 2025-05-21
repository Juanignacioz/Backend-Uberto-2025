package uberto.backendgrupo72025.domain

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus

@ResponseStatus(HttpStatus.BAD_REQUEST)
class BadRequestException(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.NOT_FOUND)
class NotFoundException(message : String) : RuntimeException(message)

@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
class NotAllowedException(message: String) : RuntimeException(message)

@ResponseStatus(HttpStatus.UNAUTHORIZED)
open class UnauthorizedException(message: String) : RuntimeException(message)

class CredencialesInvalidasException(message: String = "Credenciales inválidas. Por favor, intente nuevamente.") : UnauthorizedException(message)

class TokenExpiradoException(message: String = "Sesión expirada.") : UnauthorizedException(message)