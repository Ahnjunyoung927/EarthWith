package com.kh.eco.configuration;

import java.util.Arrays;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.kh.eco.configuration.filter.JwtFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfigure {
    
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        return httpSecurity
                .formLogin(AbstractHttpConfigurer::disable) // Form Login 비활성화
                .csrf(AbstractHttpConfigurer::disable)      // CSRF 비활성화
                .cors(Customizer.withDefaults())            // CORS 설정 적용
                .sessionManagement(manager -> 
                    manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 미사용 (JWT)
                
                .authorizeHttpRequests(requests -> {
                    // [1] 누구나 접근 가능한 기능 (가장 먼저 체크)
                    requests.requestMatchers(HttpMethod.POST, "/members", "/auth/login", "/auth/refresh", "/auth/logout").permitAll();
                    requests.requestMatchers(HttpMethod.GET, "/boards/**", "/comments/**", "/uploads/**").permitAll(); 
                    requests.requestMatchers(HttpMethod.GET, "/stats/**", "/api/**").permitAll();

                    // [2] 인증(로그인)이 필요한 기능
                    requests.requestMatchers(HttpMethod.POST, "/boards", "/comments").authenticated();
                    requests.requestMatchers(HttpMethod.PUT, "/members", "/boards/**").authenticated();
                    requests.requestMatchers(HttpMethod.DELETE, "/members", "/boards/**").authenticated();
                    
                    // [3] 관리자 전용
                    requests.requestMatchers("/admin/**").hasRole("ADMIN");

                    // [4] 그 외 모든 요청은 인증 필요 (안전장치)
                    requests.anyRequest().authenticated();
                })
                
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }
    
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:5173"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        configuration.setAllowCredentials(true);
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }
}