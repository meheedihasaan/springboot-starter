package com.springboot.starter.exceptions;

import com.springboot.starter.entities.Role;
import com.springboot.starter.enums.RoleType;
import com.springboot.starter.security.CustomUserDetails;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;
import java.util.Optional;
import static com.springboot.starter.SpringBootStarterApplication.LOGGER;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    private void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
        if(response.isCommitted()) {
            LOGGER.info("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        System.out.println(response.getHeader("Authorization"));
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    public String determineTargetUrl(Authentication authentication) {
        boolean isGranted = false;

        try {
            CustomUserDetails principal = (CustomUserDetails) authentication.getPrincipal();
            Optional<Role> roleOptional = principal.getRoles().stream().findFirst();

            if(roleOptional.isPresent()) {
                isGranted = roleOptional.get().getRoleType() == RoleType.ADMIN;
            }
        }
        catch (Exception ex) {
            System.out.println(ex.getMessage());
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            for(GrantedAuthority grantedAuthority : authorities) {
                System.out.println(grantedAuthority.getAuthority());

                if(grantedAuthority.getAuthority().startsWith("ROLE")) {
                    isGranted = true;
                    break;
                }
            }
        }

        if(isGranted) {
            return "/dashboard";
        }

        SecurityContextHolder.getContext().setAuthentication(null);
        return "/permission-denied";
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if(session == null) {
            return;
        }

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}
