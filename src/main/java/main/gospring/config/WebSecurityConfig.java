package main.gospring.config;

import lombok.RequiredArgsConstructor;
import main.gospring.Jwt.JwtAuthenticationFilter;
import main.gospring.services.UserServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class WebSecurityConfig {

    private final UserServiceImpl userServiceImpl;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    // HTTP request에 대한 web security configuration
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {

        // csrf, httpBasic 비활성화
        // formlogin, logout 활성화
        // /login, /signup, /user 외에 모든 HTTP request 인증 후 사용
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/signup", "/api/login", "api/logout").permitAll()
                        .anyRequest().authenticated())
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .csrf(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .addFilterBefore(corsFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(jwtAuthenticationFilter, CorsFilter.class)
                .build();
    }


    // AuthenticationManager 설정
    @Bean
    public AuthenticationManager authenticationManager()
            throws Exception {

        // DaoAuthenticationProvider로 AuthenticationManager 설정
        // UserDetailsService -> 구현 해놓은 UserServiceImpl
        // PasswordEncoder -> 암호화한 password인 BCryptPasswordencoder
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userServiceImpl);
        daoAuthenticationProvider.setPasswordEncoder(bCryptPasswordEncoder());

        return new ProviderManager(daoAuthenticationProvider);
    }

    // 비밀번호 암호화
    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // React, Spring 충돌 방지를 위한 CorsFilter 구현
    @Bean
    public CorsFilter corsFilter() {
        CorsConfiguration config = new CorsConfiguration();
        // session 통과
        config.setAllowCredentials(true);
        // react server
        config.addAllowedOriginPattern("http://localhost:3000");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
                config.addExposedHeader("Authorization");
    //        config.addExposedHeader("Set-Cookie");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return new CorsFilter(source);
    }

}
