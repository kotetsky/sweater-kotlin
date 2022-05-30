package com.spikart.sweater.config

import com.spikart.sweater.service.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.crypto.password.NoOpPasswordEncoder


@Configuration
@EnableWebSecurity
class WebSecurityConfig : WebSecurityConfigurerAdapter() {

    @Autowired
    private lateinit var userService: UserService

    @Throws(Exception::class)
    protected override fun configure(http: HttpSecurity) {
        http
            .authorizeRequests()
            .antMatchers("/", "/registration", "/static/**").permitAll()
            .anyRequest().authenticated()
            .and()
            .formLogin().loginPage("/login").permitAll()
            .and()
            .logout().permitAll()
    }

    override fun configure(auth: AuthenticationManagerBuilder?) {
        auth?.also {
            it.userDetailsService(userService)
                .passwordEncoder(NoOpPasswordEncoder.getInstance())
        }


    }
}