package com.dima.meterscollector.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
        http.authorizeHttpRequests()
                .requestMatchers("/api/meter_config/**").authenticated()
                .requestMatchers("/**").permitAll()
//                .anyRequest().permitAll()
                .and()
                .csrf().disable()
//                .formLogin()
//                .and()
//                .logout().logoutUrl("/logout").logoutSuccessUrl("/")
//                .deleteCookies("JSESSIONID")
//                .invalidateHttpSession(true)
//                .and()
                .httpBasic();

        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(){
        UserDetails user = User
                .withUsername("admin")
                .password(passwordEncoder().encode("090980"))
                .roles("ADMIN")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
