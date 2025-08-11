package co.simplon.soninkrala.config;

import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CorsConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtValidators;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

@Configuration
public class SecurityConfig {

    @Value("${co.simplon.soninkrala.cost}")
    private Integer cost; //nombre d'itération

    @Value("${co.simplon.soninkrala.secret_jwt}")
    private String secret;

    @Value("${co.simplon.soninkrala.expiration_jwt}")
    private long expiration;

    @Value("${co.simplon.soninkrala.issuer}")
    private String issuer;

	@Value("${co.simplon.soninkrala.cors.enabled}")
    private boolean corsEnabled;

    @Bean
    JwtProvider jwtProvider() {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        return new JwtProvider(algorithm,expiration,issuer);
    }

    @Bean
    PasswordEncoder encoder(){
        return new BCryptPasswordEncoder(cost);
    }

    @Bean //objet reconnu et géré par spring
    JwtDecoder jwtDecoder() { // Tell Spring how to verify JWT signature
        SecretKey secretKey = new SecretKeySpec(secret.getBytes(), "HmacSHA256");
        NimbusJwtDecoder decoder = NimbusJwtDecoder.withSecretKey(secretKey)
                .macAlgorithm(MacAlgorithm.HS256)
                .build();
        OAuth2TokenValidator<Jwt> auth = JwtValidators.createDefaultWithIssuer(issuer);
        decoder.setJwtValidator(auth);
        return decoder;
    }

    @Bean // injection de dépendance
        //Change default behaviour
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf((csrf) -> csrf.disable())  // Désactive la protection CSRF (pas nécessaire avec JWT)
                .cors(corsCustomizer())
                .authorizeHttpRequests((authorize) -> authorize
                        .requestMatchers(HttpMethod.POST,"/api/v1/accounts","/api/v1/accounts/log-in").anonymous()
                        .requestMatchers(HttpMethod.GET,"/soninkrala/api/v1/quiz", "/soninkrala/api/v1/quiz/{id}/answers","/soninkrala/api/v1/quiz/{id}/questions","/soninkrala/api/v1/alphabet").hasRole("MEMBER")
                        .requestMatchers(HttpMethod.POST,"/soninkrala/api/v1/quiz","/soninkrala/api/v1/quiz/{id}/correct-answer","/soninkrala/api/v1/pronunciations").hasRole("MEMBER")
                        .requestMatchers(HttpMethod.GET, "/soninkrala/api/v1/accounts/verify").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer((srv) -> srv.jwt(Customizer.withDefaults())  // Configurer le JwtDecoder pour valider les JWT
                );

        return http.build();
        // Default Spring behaviour: PoLP (no authorization at all)
        // Relies on JWT
        // authorize some requests or not
        // ???
    }

    private Customizer<CorsConfigurer<HttpSecurity>> corsCustomizer() {
	return corsEnabled ? Customizer.withDefaults()
		: cors -> cors.disable();
    }
}
