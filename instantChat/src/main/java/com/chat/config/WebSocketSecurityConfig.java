package com.chat.config;

import com.chat.security.JWTAuthorizationFilter;
import com.chat.security.error.RestAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
public class WebSocketSecurityConfig  {

    private final AppConfig appConfig;
    private final AuthenticationProvider authenticationProvider;
    private final JWTAuthorizationFilter jwtAuthorizationFilter;

    private final AuthenticationEntryPoint authEntryPoint;

    public WebSocketSecurityConfig(AppConfig appConfig, AuthenticationProvider authenticationProvider, JWTAuthorizationFilter jwtAuthorizationFilter,
                                   @Lazy @Qualifier("delegatedAuthenticationEntryPoint") AuthenticationEntryPoint authEntryPoint) {
        this.appConfig = appConfig;
        this.authenticationProvider = authenticationProvider;
        this.jwtAuthorizationFilter = jwtAuthorizationFilter;
        this.authEntryPoint = authEntryPoint;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .cors()
                .and()
                .authorizeHttpRequests()
                .antMatchers("/users/**", "/ws/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling()
                .authenticationEntryPoint(authEntryPoint)
                .accessDeniedHandler(new RestAccessDeniedHandler());

        return http.build();
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins(appConfig.getFrontEndURL(), appConfig.getBackendURL()).allowCredentials(true);
            }
        };
    }

}
