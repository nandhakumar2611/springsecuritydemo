package com.example.springsecuritynew.security.jwt;

import java.security.Key;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtils {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtils.class);

	private int jwtExpirationMs = 1200000;
	private String jwtSecret = "DucNZ}Rs-R@ENkpKRH,gjr_+YlSexMvUFmlimE!0fbZcqF;ga#";
	
	public String generateJwtToken(Authentication authentication) {
		UserDetails userDetails = (UserDetails) authentication.getPrincipal();
		return Jwts.builder()
				.setSubject(userDetails.getUsername())
				.setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(new Date().getTime() + jwtExpirationMs))
				.signWith(key(),SignatureAlgorithm.HS256)
				.compact();
	}
	
	private Key key() {
		return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
	}
	
	public boolean validateJwtToken(String authToken) {
		
		try {
			Jwts.parserBuilder().setSigningKey(key()).build().parse(authToken);
			return true;
		}
		catch (MalformedJwtException ex) {
			LOGGER.error("INVALID JWT TOKEN: ", ex.getMessage());
		}
		catch (ExpiredJwtException ex) {
			LOGGER.error("JWT TOKEN IS EXPIRED : ", ex.getMessage());
		}
		catch (UnsupportedJwtException ex) {
			LOGGER.error("JWT TOKEN IS UNSUPPORTED : ", ex.getMessage());
		}
		catch (IllegalArgumentException ex) {
			LOGGER.error("JWT CLAIMS STRING IS EMPTY : ", ex.getMessage());
		}
		return false;
	}
	
	public String getUserNameFromJwtToken(String token) {
		return Jwts.parserBuilder().setSigningKey(key()).build()
				.parseClaimsJws(token).getBody().getSubject();
	}

}
