package br.com.baseproject.secutiry.filter;

import br.com.baseproject.applicationUser.entity.ApplicationUser;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.SneakyThrows;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;

import static br.com.baseproject.secutiry.filter.Constants.*;


public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        ApplicationUser user = new ObjectMapper().readValue(request.getInputStream(), ApplicationUser.class);
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword()));

    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        ZonedDateTime expTimeUTC = ZonedDateTime.now(ZoneOffset.UTC).plus(EXPIRATION_TIME, ChronoUnit.MILLIS);
        String token = Jwts.builder().setSubject(((ApplicationUser)
                        authResult.getPrincipal()).getUsername())
                .setExpiration(Date.from(expTimeUTC.toInstant())).signWith(SignatureAlgorithm.HS256, SECRET).compact();

        token = TOKEN_PREFIX + token;
        String tokenJson = "{\"token\":" + addQuotes(token)
                + ", \"exp\" :" + addQuotes(expTimeUTC.toString()) + "}";
        response.getWriter().write(tokenJson);
        response.addHeader("Content-Type", "application/json;charset=UTF-8");
        response.addHeader(HEADER_STRING, token);
    }

    private String addQuotes(String value) {
        return new StringBuilder(300).append("\"").append(value).append("\"").toString();
    }
}
