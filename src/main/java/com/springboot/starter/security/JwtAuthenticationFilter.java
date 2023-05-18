package com.springboot.starter.security;

import static com.springboot.starter.SpringBootStarterApplication.LOGGER;

import com.springboot.starter.constants.SecurityConstant;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

@Configuration
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwt = getJwtFromRequest(request);
            if (StringUtils.hasText(jwt) && jwtTokenProvider.validateToken(jwt)) {
                String username = jwtTokenProvider.getUsernameFromJwt(jwt);
                UserDetails userDetails = customUserDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        } catch (ExpiredJwtException ex) {
            LOGGER.debug("Expired JWT token.");
            String isRefreshToken = request.getHeader("isRefreshToken");
            String requestUrl = request.getRequestURI();

            if (isRefreshToken != null && isRefreshToken.equals("true") && requestUrl.contains("refreshtoken")) {
                allowForRefreshToken(ex, request);
            } else {
                request.setAttribute("exception", ex);
            }
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }

        filterChain.doFilter(request, response);
    }

    public String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(SecurityConstant.HEADER_STRING);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(SecurityConstant.TOKEN_PREFIX)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(null, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        request.setAttribute("claims", ex.getClaims());
    }
}
