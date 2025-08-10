package co.simplon.soninkrala.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@ConditionalOnProperty(value = "co.simplon.soninkral.cors.enabled", havingValue = "true",
	matchIfMissing = true)
class CorsCustomConfig implements WebMvcConfigurer {

    @Value("${co.simplon.soninkrala.cors}")
    private String origins;

            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedMethods("GET","POST").allowedOrigins(origins);
            };
}
