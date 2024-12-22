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
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailsService customUserDetailsService;
    private final JwtSecurityFilter jwtSecurityFilter;

    public SecurityConfig(PasswordEncoder passwordEncoder, CustomUserDetailsService customUserDetailsService, JwtSecurityFilter jwtSecurityFilter) {
        this.passwordEncoder = passwordEncoder;
        this.customUserDetailsService = customUserDetailsService;
        this.jwtSecurityFilter = jwtSecurityFilter;
    }

    @Bean
   protected SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            http
                    .authorizeHttpRequests((request) -> request
                       .requestMatchers("/api/v1/login","/api/v1/user/register").permitAll()
                       .anyRequest().authenticated()
                    )
                    //disabled the csrf token
                    .csrf(AbstractHttpConfigurer::disable)
                    .httpBasic(Customizer.withDefaults())
                    //enable custom jwtFilter before the UPA Filter
                    .addFilterBefore(jwtSecurityFilter, UsernamePasswordAuthenticationFilter.class);
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

}
