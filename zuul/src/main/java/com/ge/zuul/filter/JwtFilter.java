package com.ge.zuul.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;

public class JwtFilter extends OncePerRequestFilter {

    private static final String JWT_KEY = "super secret key";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String NAME_CLAIM_TAG = "name";


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {

                String bearerToken = httpServletRequest.getHeader(AUTHORIZATION_HEADER);
        if(bearerToken == null || !bearerToken.contains("Bearer ")){
            System.out.println("No bearer token found, Security Context not modified");
            filterChain.doFilter(httpServletRequest, httpServletResponse);
            return;
        }

        JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(JWT_KEY)).build();

        System.out.println("bearerToken:" + bearerToken);
        System.out.println("bearerToken.substring(7):" + bearerToken.substring(7));
        DecodedJWT decodedJWT;
        try {
            decodedJWT = jwtVerifier.verify(bearerToken.substring(7));
        }catch (TokenExpiredException | InvalidClaimException | SignatureVerificationException ex){
            System.out.println(ex.getMessage());
            httpServletResponse.sendError(HttpStatus.UNAUTHORIZED.value(), ex.getMessage());
            return;
        }


        Claim userNameClaimed = decodedJWT.getClaim(NAME_CLAIM_TAG);
        UsernamePasswordAuthenticationToken roleToken = new UsernamePasswordAuthenticationToken(decodedJWT.getSubject(), null, Collections.emptyList());
//        SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()
        SecurityContextHolder.getContext().setAuthentication(roleToken);
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
