package com.kh.eco.configuration;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
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
	
	@Value("${app.server.url}")
	private String serverUrl;

	@Bean
	public WebSecurityCustomizer webSecurityCustomizer() {
		return (web) -> web.ignoring()
				.requestMatchers("eco/upload/**"); // 정적 리소스는 Security 필터 적용 안 함
	}
	
	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
		
		return httpSecurity
				.formLogin(AbstractHttpConfigurer::disable) 	// Form Login 비활성화
				.csrf(AbstractHttpConfigurer::disable) 	 	    // CSRF 비활성화
				.cors(Customizer.withDefaults()) 		 	    // CORS 설정 적용
				.sessionManagement(manager -> 
					manager.sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // 세션 미사용 (JWT)
				.authorizeHttpRequests(requests -> {
					// [1] 누구나 접근 가능한 기능 (PermitAll)
					
					// POST: 로그인, 회원가입, 토큰 갱신, 로그아웃
					requests.requestMatchers(HttpMethod.POST,
							"/eco/members",
							"/eco/auth/login", 
							"/eco/auth/refresh", 
							"/eco/auth/logout", 
							"/eco/members/profile", 
							"/eco/members/**").permitAll();
					
					// GET: (관리자)게시글/댓글 조회, 파일, 통계, 피드 등 비회원 접근 가능
					requests.requestMatchers(HttpMethod.GET, 
							"/eco/boards/**", 
							"/eco/comments/**", 
							"/eco/uploads/**", 
							"/eco/stats/**", 
							"/eco/api/**", 
							"/eco/feeds/**",
							"/eco/stats/today/**", 
							"/eco/api/boards/stats/**",
							"/eco/members/**",
							"/eco/admin/notices/**"
					).permitAll(); 
					
					// [2] 인증(로그인)이 필요한 기능 (Authenticated)
					
					// PUT: 수정 (HEAD, DEVELOP 통합)
					requests.requestMatchers(HttpMethod.PUT, "eco/members", "eco/boards/**", "eco/members/**").authenticated();
					// POST: 게시글 작성, 댓글 작성, 피드 작성
					requests.requestMatchers(HttpMethod.POST, 
							"/eco/boards", 
							"/eco/comments", 
							"/eco/api/boards/**", 
							"/eco/feeds").authenticated();
					
					// PUT: 회원 정보 수정, 게시글 수정
					// (/members/** 와일드카드가 password, email 등을 모두 포함하므로 통합)
					requests.requestMatchers(HttpMethod.PUT, 
							"/eco/members/**", 
							"/eco/boards/**",
							"/eco/feeds/**",
							"/eco/comments/**").authenticated();
					
					// DELETE: 회원 탈퇴, 게시글 삭제
					requests.requestMatchers(HttpMethod.DELETE, 
							"/eco/members/**", 
							"/eco/boards/**",
							"/eco/feeds/**").authenticated();
					
					// [3] 관리자 전용
					requests.requestMatchers("/eco/admin/**").hasRole("ADMIN");
					
					// [4] 그 외 모든 요청은 인증 필요
					requests.anyRequest().authenticated();
				})
				
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class) // JWT 필터 적용
				.build();

	}
	
	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		
		// 리액트 개발 서버 포트 허용 (5173: Vite, 3000: CRA) - HEAD 설정 유지 (더 포괄적)
		configuration.setAllowedOrigins(Arrays.asList(serverUrl , "http://localhost:5173" /* ,"http://" + serverUrl + ":3000"*/));
		configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
		configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "Cache-Control"));
		configuration.setAllowCredentials(true);
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	// PasswordEncoder 빈
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	// AuthenticationManager 빈
	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}
	
}