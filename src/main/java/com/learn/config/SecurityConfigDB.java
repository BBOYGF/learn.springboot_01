package com.learn.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class SecurityConfigDB extends WebSecurityConfigurerAdapter {
    //自动注入userDetailsService
    @Autowired
    private UserDetailsService userDetailsService;
    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //配置读取设置
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/adduser","/index","/main").hasAnyRole("vip1")
                .antMatchers("/getAllUser","/index","/main","/adduser").hasAnyRole("guofan");
        System.out.println("授权了！=======================");
        http.formLogin().loginPage("/toLogin").
                loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .defaultSuccessUrl("/").permitAll()
        ;
        http.rememberMe();

    }
    //认证
    //密码编码
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
        System.out.println("认证了！=====================================");
    }
    @Bean
    PasswordEncoder passwordEncoder()
    {
        return new BCryptPasswordEncoder();
    }
}
