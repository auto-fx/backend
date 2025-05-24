package com.autofx.autofxbackend.iam.infrastructure.authorization.sfs.configuration;


import com.autofx.autofxbackend.iam.infrastructure.authorization.sfs.pipeline.BearerAuthorizationRequestFilter;
import com.autofx.autofxbackend.iam.infrastructure.authorization.sfs.service.UserDetailsServiceImpl;
import com.autofx.autofxbackend.iam.infrastructure.hashing.bcrypt.BCryptHashingService;
import com.autofx.autofxbackend.iam.infrastructure.token.jwts.BearerTokenService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableMethodSecurity
public class WebSecurityConfiguration {

    private final BCryptHashingService bCryptHashingService;
    private final UserDetailsServiceImpl userDetailsService;
    private final BearerTokenService bearerTokenService;


    public WebSecurityConfiguration(BCryptHashingService bCryptHashingService
    , UserDetailsServiceImpl userDetailsService, BearerTokenService bearerTokenService) {
        this.userDetailsService = userDetailsService;
        this.bCryptHashingService = bCryptHashingService;
        this.bearerTokenService = bearerTokenService;
    }

    @Bean
    public BearerAuthorizationRequestFilter authorizationRequestFilter() {
        return new BearerAuthorizationRequestFilter(bearerTokenService, userDetailsService);
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        var authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService);
        authenticationProvider.setPasswordEncoder(bCryptHashingService);
        return authenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return bCryptHashingService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(    "/api/authentication-autofx/v1/users/sign-in",
                            "/api/authentication-autofx/v1/users/sign-up",
                            "/api/authentication-autofx/swagger-ui/**",
                            "/api/authentication-autofx/v3/api-docs",
                            "/api/authentication-autofx/v3/api-docs/**",
                            "/api/authentication-autofx/swagger-ui.html",
                            "/api/authentication-autofx/swagger-resources/**",
                            "/api/authentication-autofx/webjars/**").permitAll();
                    auth.requestMatchers("/api/authentication-autofx/v1/users/update-password",
                            "/api/authentication-autofx/v1/users/update-user-data", "/api/authentication-autofx/v1/roles").hasRole("USER");
                    auth.anyRequest().authenticated();
                })
                .sessionManagement(customizer -> {
                    customizer.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                })
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authorizationRequestFilter(), UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("http://localhost:4040", "https://api-gateway-server-production.up.railway.app")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*")
                        .allowCredentials(true);
            }
        };
    }

}