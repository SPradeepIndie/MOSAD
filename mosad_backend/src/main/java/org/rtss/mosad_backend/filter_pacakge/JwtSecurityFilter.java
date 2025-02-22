package org.rtss.mosad_backend.filter_pacakge;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.rtss.mosad_backend.repository.user_management.BlackListTokensRepo;
import org.rtss.mosad_backend.service.login_user.CustomUserDetailsService;
import org.rtss.mosad_backend.service.login_user.JwtService;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
//For every request this filter is called because of the OncePerRequestFIlter class
public class JwtSecurityFilter extends OncePerRequestFilter {

    private final JwtService  jwtService;
    private final ApplicationContext applicationContext;
    private final BlackListTokensRepo blackListTokensRepo;

    public JwtSecurityFilter(JwtService jwtService, ApplicationContext applicationContext, BlackListTokensRepo blackListTokensRepo) {
        this.jwtService = jwtService;
        this.applicationContext = applicationContext;
        this.blackListTokensRepo = blackListTokensRepo;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getServletPath().equals("/api/v1/refresh_token")) {
            filterChain.doFilter(request, response); // Skip the filter for refresh token endpoint
            return;
        }
        // Bearer [token] : This is the request get from the client side
        String authHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String username = null;
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            jwtToken = authHeader.substring(7);
            username = jwtService.extractUsernameFromToken(jwtToken);
        }
        if(username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            //Getting CustomUserDetailsService without raising the circular dependency.
            UserDetails userDetails=applicationContext.getBean(CustomUserDetailsService.class).loadUserByUsername(username);
            boolean isBlackListed=blackListTokensRepo.findByToken(jwtToken).isPresent();
            //validateToken method validate the token with the token and the userDetails
            if(jwtService.validateToken(jwtToken,userDetails) && !isBlackListed){
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request, response);//continue on next filter
    }
}
