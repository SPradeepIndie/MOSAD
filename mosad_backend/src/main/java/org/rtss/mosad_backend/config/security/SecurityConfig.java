package org.rtss.mosad_backend.config.security;

import org.rtss.mosad_backend.filter_pacakge.JwtSecurityFilter;
import org.rtss.mosad_backend.service.login_user.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtSecurityFilter jwtSecurityFilter;
    private final LogoutHandler logoutHandler;

    public SecurityConfig(PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService, JwtSecurityFilter jwtSecurityFilter, LogoutHandler logoutHandler) {
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtSecurityFilter = jwtSecurityFilter;
        this.logoutHandler = logoutHandler;
    }

    @Bean
   protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    //Configure Cross Origin configuration
                    .cors((cors)->cors.configurationSource(corsConfigurationSource()))
                    .authorizeHttpRequests((request) -> request
                            .requestMatchers("/api/v1/login").permitAll()
                            .anyRequest().authenticated()
                    )
                    //disabled the csrf token
                    .csrf(AbstractHttpConfigurer::disable)
                    .httpBasic(Customizer.withDefaults())
                    //enable custom jwtFilter before the UPA Filter
                    .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class)
                    .logout(logout -> logout
                            .logoutUrl("/api/v1/logout")
                            .addLogoutHandler(logoutHandler)
                            .logoutSuccessHandler((request, response, authentication) ->
                                    SecurityContextHolder.clearContext()
                            ));
       return http.build();
   }

   @Bean
    public AuthenticationProvider authenticationProvider() {
       DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
       authenticationProvider.setPasswordEncoder(passwordEncoder.bCryptPasswordEncoder());
       authenticationProvider.setUserDetailsService(customUserDetailsService);
       return authenticationProvider;
   }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    //Create custom CORS (Cross Origin Resource Sharing) configuration for localhost:3000 React app
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE", "OPTIONS", "HEAD"));
        configuration.setAllowedHeaders(List.of("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

}
