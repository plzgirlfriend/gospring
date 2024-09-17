package main.gospring.Jwt;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;

    // Bearer token
    private String parserBearerToken(HttpServletRequest request) {

        // Http reqeust header에서 get BearerToken
        String bearerToken = request.getHeader("Authorization");

        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {

            return bearerToken.substring(7);
        } else{
            return null;
        }
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        try {

            String token = parserBearerToken(request);

            log.info("Filter running..");

            // token 존재
            if (token != null && !token.equalsIgnoreCase("null")) {

                String username = tokenProvider.validateTokenByUsername(token);
                log.info("Authentication username: {}", username);

                // UsernamePasswordAuthenticationToken 생성 (username 사용자 인증 정보 저장)
                AbstractAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        username,
                        null,
                        AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER")
                );

                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                // SecurityContext에 인증된 사용자 정보를 등록(createContext -> setAuthentication)
                SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
                securityContext.setAuthentication(authenticationToken);

                SecurityContextHolder.setContext(securityContext);

            }
        } catch (Exception e) {

            logger.error(e.getMessage());
        }

        // dofilter를 반드시 실행해줘야함
        filterChain.doFilter(request, response);
    }
}
