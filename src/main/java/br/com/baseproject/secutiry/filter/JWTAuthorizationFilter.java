package br.com.baseproject.secutiry.filter;


import br.com.baseproject.applicationUser.entity.ApplicationUser;
import br.com.baseproject.secutiry.service.CustomuserDetailService;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static br.com.baseproject.secutiry.filter.Constants.*;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    private final CustomuserDetailService customuserDetailService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, CustomuserDetailService customuserDetailService) {
        super(authenticationManager);
        this.customuserDetailService = customuserDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header = request.getHeader(HEADER_STRING);
        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(request);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(HEADER_STRING);
        if (token == null) return null;
        String username = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody().getSubject();
        UserDetails userDetails = customuserDetailService.loadUserByUsername(username);
        ApplicationUser applicationUser = customuserDetailService.loadApplicationByUserName(username);
        return username != null ? new UsernamePasswordAuthenticationToken(applicationUser.getUsername(), null, userDetails.getAuthorities()) : null;
    }
}
