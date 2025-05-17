package com.ict.Hackathon.config;

import com.ict.Hackathon.jwt.JwtAuthenticationFilter;
import com.ict.Hackathon.jwt.JwtTokenProvider;
import jakarta.servlet.http.HttpServletResponse;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

	private final JwtTokenProvider jwtTokenProvider;

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		// CSRF 비활성화
		http
			.headers(headers -> headers
				.frameOptions(frameOptions -> frameOptions
						.deny() // 또는 sameOrigin()
					)
				)
			.cors(cors -> cors.configurationSource(corsConfigurationSource()))
			.csrf(csrf -> csrf.disable())  // CSRF 보호 비활성화
			.authorizeHttpRequests(authz -> authz
				// Swagger 경로 허용
				.requestMatchers(
					"/swagger-ui/**",
					"/v3/api-docs/**",
					"/swagger-resources/**",
					"/webjars/**"
				).permitAll()

				// 로그아웃은 인증된 사용자만 가능
				.requestMatchers("/logout").authenticated()

				// 나머지 요청은 모두 허용
				.anyRequest().permitAll()
			)
			.addFilterBefore(new JwtAuthenticationFilter(jwtTokenProvider),
				UsernamePasswordAuthenticationFilter.class) // JWT 인증 필터 추가
			.exceptionHandling(ex -> ex
				.authenticationEntryPoint((request, response, authException) -> {
					response.setContentType("application/json");
					response.setStatus(
						HttpServletResponse.SC_UNAUTHORIZED); // 401 Unauthorized 상태 설정
					response.getWriter().write("{\"error\": \"Unauthorized\"}");
				})
				.accessDeniedHandler((request, response, accessDeniedException) -> {
					response.setContentType("application/json"); // 401 Unauthorized 상태 설정
					response.setStatus(HttpServletResponse.SC_FORBIDDEN);
					response.getWriter().write("{\"error\": \"Access Denied\"}");
				})
			)
			.sessionManagement(session -> session
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // Stateless 기반 세션 관리
			)
			// 로그아웃 경로 설정
			.logout(logout -> logout
				.logoutUrl("/logout") // 로그아웃 URL
				.logoutSuccessHandler(logoutSuccessHandler()) // 커스텀 핸들러 등록
				.permitAll() // 로그아웃 경로는 모두 허용

			);

		return http.build();
	}

	@Bean
	public LogoutSuccessHandler logoutSuccessHandler() {
		return new CustomLogoutSuccessHandler(jwtTokenProvider);  // 커스텀 로그아웃 성공 핸들러 사용
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return PasswordEncoderFactories.createDelegatingPasswordEncoder(); // 패스워드 인코딩
	}

	@Bean
	public AuthenticationManager authenticationManager(
		AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public CorsConfigurationSource corsConfigurationSource() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.setAllowedOriginPatterns(Arrays.asList("http://localhost:3000", "*"));
		configuration.setAllowedMethods(
			Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS")); // 허용 메서드
		configuration.setAllowedHeaders(
			Arrays.asList("Authorization", "verify", "Content-Type", "cookie")); // 허용 헤더
		configuration.setExposedHeaders(
			Arrays.asList("Authorization", "verify")); // 클라이언트가 접근할 수 있는 응답 헤더
		configuration.setAllowCredentials(true); // 자격증명 허용
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}