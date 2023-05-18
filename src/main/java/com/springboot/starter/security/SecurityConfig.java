package com.springboot.starter.security;

import com.springboot.starter.constants.SecurityConstant;
import com.springboot.starter.exceptions.AuthenticationExceptionHandler;
import com.springboot.starter.exceptions.CustomAuthenticationSuccessHandler;
import java.util.Collections;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private AuthenticationExceptionHandler authenticationExceptionHandler;

    @Autowired
    private CustomAuthenticationSuccessHandler authenticationSuccessHandler;

    @Bean
    @Order(1)
    public SecurityFilterChain jwtFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .cors()
                .disable()
                .securityMatcher("/api/**")
                .authorizeHttpRequests()
                .requestMatchers(SecurityConstant.JWT_DISABLE_ANTMATCHERS)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(authenticationExceptionHandler)
                .and()
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .requiresChannel()
                .requestMatchers(r -> r.getHeader("X-Forwarded-Proto") != null)
                .requiresSecure();

        return httpSecurity.build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain sessionFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf()
                .disable()
                .cors()
                .disable()
                .securityMatcher("/**")
                .authorizeHttpRequests()
                .requestMatchers(SecurityConstant.FORM_DISABLE_ANTMATCHERS)
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=true")
                .successHandler(authenticationSuccessHandler)
                .and()
                .rememberMe()
                .tokenValiditySeconds((int) SecurityConstant.SESSION_TOKEN_EXPIRATION_TIME)
                .key(SecurityConstant.SECRET_KEY)
                .rememberMeParameter("remember-me")
                .and()
                .logout()
                .logoutUrl("/logout")
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
                .logoutSuccessUrl("/login")
                .clearAuthentication(true)
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID", "remember-me");

        return httpSecurity.build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();

        List<String> all = List.of("*");
        List<String> methods = List.of("GET", "POST", "PUT", "DELETE", "PATCH");
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(methods);
        configuration.setAllowCredentials(false);
        configuration.setAllowedHeaders(all);

        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
