package org.example.ecomarcehandicraftbackend.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

public class SystemConfig {
    @Bean
    public SecurityFilterChain filterRequest(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests(autorize->autorize.requestMatchers("/api/**").authenticated()
                        .anyRequest().permitAll())
                .addFilterBefore(new JwtValidator(), BasicAuthenticationFilter.class).csrf().disable()
                .cors().configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration cc = new CorsConfiguration();
                        cc.setAllowedOrigins(Arrays.asList(
                                "http://localhost:4200/",

                                "http://localhost:3200/"
                        ));
                        cc.setAllowedMethods(Collections.singletonList("*"));
                        cc.setAllowCredentials(true);
                        cc.setAllowedHeaders(Collections.singletonList("*"));
                        cc.setExposedHeaders(Arrays.asList(JwtConstant.JWT_HEADER));
                        cc.setMaxAge(3600L);
                        return cc;
                    }
                }).and().httpBasic().and().formLogin();
        return httpSecurity.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
