package com.vp.voicepocket.domain.token.config;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter{

    private final JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        JSONObject jsonObject = new JSONObject();
        try{
            String token = jwtProvider.resolveToken(request);
            if(token != null && jwtProvider.checkValidationToken(token)){
                Authentication authentication = jwtProvider.getAuthentication(token);   // 토큰에서 유저 정보를 추출
                SecurityContextHolder.getContext().setAuthentication(authentication);   // 해당 정보를 SecurityContextHolder에 저장
                filterChain.doFilter(request, response);
            }
        } catch (MalformedJwtException e){
            jsonObject.put("code", -1013);
            jsonObject.put("message", e.getMessage());
        } catch ( ExpiredJwtException e){
            jsonObject.put("code", -1014);
            jsonObject.put("message", e.getMessage());
        }catch (UnsupportedJwtException | IllegalArgumentException e){
            jsonObject.put("code", -1015);
            jsonObject.put("message", e.getMessage());
        }finally {
            response.getWriter().write(jsonObject.toString());
        }

    }
}
