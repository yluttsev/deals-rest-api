package ru.luttsev.deals.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.apache.hc.core5.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import ru.luttsev.deals.exception.InvalidJwtTokenException;
import ru.luttsev.deals.exception.JwtTokenExpiredException;
import ru.luttsev.deals.model.payload.security.UserDetailsImpl;
import ru.luttsev.deals.service.impl.JwtService;

import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String jwt = authHeader.substring(7);
            if (!jwtService.isAccessTokenExpired(jwt)) {
                if (jwtService.validateAccessToken(jwt)) {
                    String username = jwtService.getUsernameFromAccessToken(jwt);
                    List<String> roles = jwtService.getUserRolesFromAccessToken(jwt);
                    UserDetailsImpl userDetails = new UserDetailsImpl(
                            username, roles
                    );
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            userDetails, null, userDetails.getAuthorities()
                    );
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new InvalidJwtTokenException();
                }
            } else {
                throw new JwtTokenExpiredException();
            }
        }
        filterChain.doFilter(request, response);
    }

}
