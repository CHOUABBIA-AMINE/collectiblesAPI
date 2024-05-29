/**
 *	
 *	@author		: CHOUABBIA Amine
 *
 *	@Name		: JwtAuthFilter
 *	@CreatedOn	: 06-26-2023
 *
 *	@Type		: Class
 *	@Layaer		: Configuration
 *	@Goal		: Security
 *
 **/

package aures.api.configuration.common.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import aures.api.service.common.security.UserDetailService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component

public class JwtAuthFilter extends OncePerRequestFilter {

	@Autowired
    private UserDetailService userDetailsService;
	
	@Autowired
    private JwtUtils jwtUtils;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

    	final String authHeader = request.getHeader("Authorization");
    	final String jwt;
    	final String username;
    	
    	if(authHeader == null || !authHeader.startsWith("Bearer")) {
    		filterChain.doFilter(request, response);
            return;
    	}
    	
    	jwt = authHeader.substring(7);
    	username = jwtUtils.extractUsername(jwt);
    	if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            if (jwtUtils.isTokenValid(jwt, userDetails)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
    	
    	filterChain.doFilter(request, response);
    }
}