package uberto.backendgrupo72025.security

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.csrf.CookieCsrfTokenRepository
import org.springframework.security.web.csrf.CsrfToken
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler
import org.springframework.security.web.csrf.CsrfTokenRequestHandler
import org.springframework.security.web.csrf.XorCsrfTokenRequestAttributeHandler
import org.springframework.util.StringUtils
import org.springframework.web.servlet.config.annotation.CorsRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer
import uberto.backendgrupo72025.domain.ROLES
import java.util.function.Supplier

@Configuration
@EnableWebSecurity
class UbertoSecurityConfig {

    @Autowired
    lateinit var jwtAuthorizationFilter: JWTAuthorizationFilter

    private val logger: Logger = LoggerFactory.getLogger(UbertoSecurityConfig::class.java)

    @Bean
    fun securityFilterChain(httpSecurity: HttpSecurity): SecurityFilterChain {
        return httpSecurity
            .cors(Customizer.withDefaults())
            .csrf { csrf -> csrf.disable()
//                csrf.ignoringRequestMatchers("/login")
//                csrf.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse())
//                csrf.csrfTokenRequestHandler(SpaCsrfTokenRequestHandler())
            }
            .authorizeHttpRequests { requests ->
                requests
                    // Endpoints públicos
                    .requestMatchers("/usuarioLogin").permitAll()
                    .requestMatchers("/home").hasAnyAuthority(ROLES.VIAJERO.toString(), ROLES.CONDUCTOR.toString())
                    .requestMatchers("/error").permitAll()
                    .requestMatchers(HttpMethod.OPTIONS).permitAll()

                    // Endpoints de administración
//                    .requestMatchers(HttpMethod.POST, "/heladerias").hasAuthority(ROLES.ADMIN.name)
//                    .requestMatchers(HttpMethod.POST, "/duenios").hasAuthority(ROLES.ADMIN.name)
//                    .requestMatchers(HttpMethod.PUT, "/heladerias/**").hasAuthority(ROLES.ADMIN.name)

                    // Todos los demás endpoints requieren autenticación
                    .anyRequest().authenticated()
            }
            .sessionManagement { session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .exceptionHandling { exceptions ->
                exceptions
                    .accessDeniedHandler { request, response, accessDeniedException ->
                        logger.error("Acceso denegado para ${request.requestURI}: ${accessDeniedException.message}")
                        response.sendError(HttpServletResponse.SC_FORBIDDEN, "Acceso denegado: ${accessDeniedException.message}")
                    }
                    .authenticationEntryPoint { request, response, authException ->
                        logger.error("Autenticación fallida para ${request.requestURI}: ${authException.message}")
                        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "No autorizado: ${authException.message}")
                    }
            }
            .httpBasic(Customizer.withDefaults())
            .build()
    }

    @Bean
    @Throws(Exception::class)
    fun authenticationManager(configuration: AuthenticationConfiguration): AuthenticationManager {
        return configuration.authenticationManager
    }

    @Bean
    fun corsConfigurer(): WebMvcConfigurer {
        return object : WebMvcConfigurer {
            override fun addCorsMappings(registry: CorsRegistry) {
                registry.addMapping("/**")
                    .allowedOrigins("http://localhost:5173")
                    .allowedHeaders("*")
                    .allowedMethods("POST", "GET", "PUT", "DELETE", "OPTIONS")
                    .allowCredentials(true)
                    .maxAge(3600)
            }
        }
    }
}

class SpaCsrfTokenRequestHandler : CsrfTokenRequestHandler {
    private val plain: CsrfTokenRequestHandler = CsrfTokenRequestAttributeHandler()
    private val xor: CsrfTokenRequestHandler = XorCsrfTokenRequestAttributeHandler()

    private val logger: Logger = LoggerFactory.getLogger(SpaCsrfTokenRequestHandler::class.java)

    override fun handle(request: HttpServletRequest, response: HttpServletResponse, csrfToken: Supplier<CsrfToken>) {
        logger.debug("Manejando token CSRF para la solicitud")
        xor.handle(request, response, csrfToken)
        csrfToken.get()
    }

    override fun resolveCsrfTokenValue(request: HttpServletRequest, csrfToken: CsrfToken): String? {
        logger.debug("Resolviendo valor del token CSRF")
        val headerValue = request.getHeader(csrfToken.headerName)

        return if (StringUtils.hasText(headerValue)) {
            logger.debug("Token CSRF encontrado en el encabezado")
            plain.resolveCsrfTokenValue(request, csrfToken)
        } else {
            logger.debug("Token CSRF no encontrado en el encabezado, buscando en parámetros")
            xor.resolveCsrfTokenValue(request, csrfToken)
        }
    }
}