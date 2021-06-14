package com.orangetalents.biometria.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeRequests(authorizeRequests ->
                        authorizeRequests
                                .antMatchers(HttpMethod.GET, "/api/cartoes/**").hasAuthority("SCOPE_cartoes:read")
                                .antMatchers(HttpMethod.POST, "/api/cartoes/**").hasAuthority("SCOPE_cartoes:write")
                                .antMatchers(HttpMethod.POST, "/api/biometria/**").hasAuthority("SCOPE_biometria:write")
                                .anyRequest().authenticated()
                )
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
