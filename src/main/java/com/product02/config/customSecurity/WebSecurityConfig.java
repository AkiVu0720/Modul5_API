package com.product02.config.customSecurity;

import com.product02.config.customSecurity.CustomAuthenProvider;
import com.product02.utils.jwt.JwtFiler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig  {
    @Autowired
    private JwtFiler jwtFiler;
    @Autowired
    private CustomAuthenProvider customAuthenProvider;

@Bean
    public PasswordEncoder passwordEncoder(){
    return new BCryptPasswordEncoder();
}
@Bean
public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
    return httpSecurity.csrf().disable().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            .and().authorizeHttpRequests()
            .requestMatchers("/api.myservice.com/v1/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .addFilterBefore(jwtFiler, UsernamePasswordAuthenticationFilter.class)
            .build();
}

    /**
     *
     * @param httpSecurity
     * @return
     * @throws Exception
     */
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.getSharedObject(AuthenticationManagerBuilder.class)
                .authenticationProvider(customAuthenProvider)
                .build();
    }

}
