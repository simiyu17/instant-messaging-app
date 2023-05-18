package com.chat.security;

import com.sun.istack.NotNull;
import io.jsonwebtoken.JwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JWTAuthorizationFilter extends OncePerRequestFilter {

    private final CurrentUserDetails currentUserDetails;


    @Override
    protected void doFilterInternal(@NotNull HttpServletRequest request,
                                    @NotNull HttpServletResponse response,
                                    @NotNull FilterChain chain) throws IOException, ServletException {
        final var header = request.getHeader(SecurityConstants.HEADER_AUTHORIZATION);
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        final var token = header.replaceFirst(SecurityConstants.TOKEN_PREFIX, "").trim();
        UserDetails userDetails = null;
        try {
            userDetails = currentUserDetails
                    .loadUserByUsername(JwtTokenUtil.extractUsername(token));
        }catch (JwtException ignored){

        }
        if (Objects.isNull(userDetails) || !JwtTokenUtil.isTokenValid(token, userDetails)) {
            chain.doFilter(request, response);
            return;
        }

        final var authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());

            authentication.setDetails(
                    new WebAuthenticationDetailsSource().buildDetails(request)
            );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }
}