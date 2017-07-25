package com.phonebook.configuration;

import com.phonebook.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserService userService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/*", "/css/*", "/js/*", "/api/user/**").permitAll()
                .anyRequest().authenticated()
//                .antMatchers("/").hasRole("CLIENT")
            .and()
                .formLogin()
                    .loginPage("/login")
                    .usernameParameter("login")
                    .successForwardUrl("/login/success")
                    .failureForwardUrl("/login/failed")
            .and()
                .logout()
                    .logoutSuccessUrl("/login/logout")
                    .logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll();

    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService);
    }
}
