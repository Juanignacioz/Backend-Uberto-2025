package uberto.backendgrupo72025.security


import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import uberto.backendgrupo72025.domain.TokenExpiradoException
import uberto.backendgrupo72025.service.UsuarioService


@Component
class JWTAuthorizationFilter : OncePerRequestFilter() {

    @Autowired
    lateinit var tokenUtils: TokenUtils

    @Autowired
    lateinit var usuarioService: UsuarioService

    public override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        try {
            val bearerToken = request.getHeader("Authorization")
            if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
                val usernamePAT = tokenUtils.getAuthentication(bearerToken)
                usuarioService.validarUsuario(usernamePAT.name)
                SecurityContextHolder.getContext().authentication = usernamePAT
                logger.info("username PAT: $usernamePAT")
            }

            filterChain.doFilter(request, response)
        } catch (e: TokenExpiradoException) {
            // Captura la excepción de token expirado y devuelve el status code adecuado (401-Unauthorized)
            logger.warn(e.message)
            response.sendError(HttpStatus.UNAUTHORIZED.value(), e.message)
        }
    }
}