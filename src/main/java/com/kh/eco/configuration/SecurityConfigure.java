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

		return httpSecurity.formLogin(AbstractHttpConfigurer::disable)
						   .csrf(AbstractHttpConfigurer::disable)
						   .cors(Customizer.withDefaults())
						   .authorizeHttpRequests(requests -> {
							   requests.requestMatchers(HttpMethod.POST, "/auth/login", "/members", "/auth/refresh", "/auth/logout").permitAll(); // 누구나 허용할 기능
							   requests.requestMatchers(HttpMethod.PUT, "/members", "/members/password", "/members/email" , "/boards/**").authenticated(); // 수정, 인증 필요한 기능
							   requests.requestMatchers(HttpMethod.DELETE, "/members", "/boards/**").authenticated(); // 삭제, 인증 필요한 기능
							   requests.requestMatchers(HttpMethod.POST, "/boards", "/comments").authenticated(); // 게시글 작성 시 로그인 필요
							   requests.requestMatchers(HttpMethod.GET, "/boards/**", "/comments/**", "/uploads/**").permitAll(); // 게시글 전체조회 및 상세조회는 아무나
							   requests.requestMatchers("/admin/**").hasRole("ADMIN");
							   // 관리자 권한이 필요한 요청으로 연결 시 사용, DB상에 권한 컬럼에 ROLE_ADMIN이 있다면 패스, 아니면 아웃
						   })

						   .sessionManagement(manager -> 
								   				manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
						   .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // 관례적으로 jwt를 앞에다가
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
