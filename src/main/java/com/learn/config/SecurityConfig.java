package com.learn.config;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

//@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    //授权
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //首页所有人都能访问功能只能有权限的人访问
        http.authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/adduser","/index","/main").hasAnyRole("vip1")
                .antMatchers("/getAllUser","/","/main").hasAnyRole("guofan");
        System.out.println("授权了！");
        http.formLogin().loginPage("/toLogin").loginProcessingUrl("/login").usernameParameter("username").passwordParameter("password");
        http.rememberMe();

    }
    //认证
    //密码编码
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
       auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
               .withUser("guofan").password(new BCryptPasswordEncoder().encode("456")).roles("guofan")
        .and().withUser("15").password(new BCryptPasswordEncoder().encode("")).roles("vip1");
        UserDetailsService defaultUserDetailsService = auth.getDefaultUserDetailsService();

    }
}
