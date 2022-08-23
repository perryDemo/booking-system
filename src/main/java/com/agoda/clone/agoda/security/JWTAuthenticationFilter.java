package com.agoda.clone.agoda.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.agoda.clone.agoda.service.UserDetailServiceImpl;

@Component
public class JWTAuthenticationFilter extends OncePerRequestFilter{
    
    @Autowired
    private JWTProvider jwtProvider;
    @Autowired
    private UserDetailServiceImpl userDetailsService;
    @Override

    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String jwt = getJwtFromCookie(request);

        if(StringUtils.hasText(jwt) && jwtProvider.validateToken(jwt)){
            String username = jwtProvider.getSubjectFromJWT(jwt);

            UserDetails userDetails = userDetailsService.loadUserById(Integer.parseInt(username));
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                        (userDetails, null, userDetails.getAuthorities());
            authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromCookie(HttpServletRequest request) {
        if(request.getCookies()!=null){
            Cookie[] cookies = request.getCookies();
            for (Cookie cookie : cookies) {
                if ("agoda".equals(cookie.getName())) {
                    String accessToken = cookie.getValue();
                    if (accessToken == null) return null;
    
                    return accessToken;
                }
            }
        }
        return null;
    }
}
