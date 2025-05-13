package com.ict.Hackathon.jwt;


import com.ict.Hackathon.config.GlobalExceptionHandler.InvalidTokenException;
import com.ict.Hackathon.dto.TokenResponseDto;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
@Slf4j
@Component
public class JwtTokenProvider {

	private final Key key;

	@Value("${jwt.access-token-expiry}")
	private long accessTokenExpiry;

	@Value("${jwt.refresh-token-expiry}")
	private long refreshTokenExpiry;

	private static final String AUTHORITIES_KEY = "auth";
	private static final String BEARER_PREFIX = "Bearer ";

	public JwtTokenProvider(@Value("${jwt.secret}") String secretKey) {
		byte[] keyBytes = Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public TokenResponseDto generateToken(Authentication authentication) {
		String authorities = authentication.getAuthorities().stream()
			.map(GrantedAuthority::getAuthority)
			.collect(Collectors.joining(","));

		long now = System.currentTimeMillis();
		String subject = authentication.getName();

		String accessToken = createAccessToken(subject, authorities, now);
		String refreshToken = createRefreshToken(now);

		return TokenResponseDto.builder()
			.grantType("Bearer")
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
	}

	private String createAccessToken(String subject, String authorities, long now) {
		Date expiry = new Date(now + accessTokenExpiry);

		return Jwts.builder()
			.subject(subject)
			.claim(AUTHORITIES_KEY, authorities)
			.expiration(expiry)
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	private String createRefreshToken(long now) {
		return Jwts.builder()
			.expiration(new Date(now + refreshTokenExpiry))
			.signWith(key, SignatureAlgorithm.HS256)
			.compact();
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseClaims(accessToken)
			.orElseThrow(() -> new InvalidTokenException());

		String authoritiesClaim = claims.get(AUTHORITIES_KEY, String.class);
		if (authoritiesClaim == null || authoritiesClaim.isEmpty()) {
			authoritiesClaim = "ROLE_USER";
		}

		Collection<? extends GrantedAuthority> authorities =
			Arrays.stream(authoritiesClaim.split(","))
				.map(SimpleGrantedAuthority::new)
				.toList();

		return new UsernamePasswordAuthenticationToken(claims.getSubject(), null, authorities);
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(key).build().parseClaimsJws(token);
			return true;
		} catch (JwtException | IllegalArgumentException e) {
			log.warn("Invalid JWT Token", e);
		}
		return false;
	}

	private Optional<Claims> parseClaims(String token) {
		try {
			return Optional.of(
				Jwts.parser().setSigningKey(key).build().parseClaimsJws(token).getBody()
			);
		} catch (ExpiredJwtException e) {
			return Optional.of(e.getClaims());
		}
	}

	public String resolveToken(HttpServletRequest request) {
		String bearerToken = request.getHeader("Authorization");
		if (bearerToken != null && bearerToken.startsWith(BEARER_PREFIX)) {
			return bearerToken.substring(BEARER_PREFIX.length());
		}
		return null;
	}
}