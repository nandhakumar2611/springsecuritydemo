package com.example.springsecuritynew.security.jwt;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.springsecuritynew.security.service.UserDetailsServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthenticationTokenFilter extends OncePerRequestFilter{
	
	private static final Logger LOGGER = LoggerFactory.getLogger(JwtAuthenticationTokenFilter.class);
	
	@Autowired
	private JwtUtils jwtUtils;

	@Autowired
	private UserDetailsServiceImpl userDetailsServiceImpl;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		try {
			String jwtToken = parseJwt(request);
			if (jwtToken != null && jwtUtils.validateJwtToken(jwtToken)) {
				String username = jwtUtils.getUserNameFromJwtToken(jwtToken);

				UserDetails userDetails = userDetailsServiceImpl.loadUserByUsername(username);
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
						userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

				SecurityContextHolder.getContext().setAuthentication(authenticationToken);
			}
		} catch (Exception ex) {
			LOGGER.error("CANNOT SET USER AUTHENTICATION :{} ", ex);
		}

		filterChain.doFilter(request, response);	
		
	}
	
	private String parseJwt(HttpServletRequest request) {

		String headerAuthentication = request.getHeader("Authorization");

		if (StringUtils.hasText(headerAuthentication) && headerAuthentication.startsWith("Bearer ")) {
			return headerAuthentication.substring(7);
		}
		return null;
	}


}
