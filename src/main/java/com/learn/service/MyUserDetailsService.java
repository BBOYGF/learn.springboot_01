package com.learn.service;

import com.learn.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("userDetailsService")
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserMapper userMapper;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        System.out.println("输入的是："+s);
        com.learn.pojo.User user = userMapper.getUserByName(s);
        System.out.println(user.toString());

        List<GrantedAuthority> auths= AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_guofan");
        return new User(user.getName(),new BCryptPasswordEncoder().encode(user.getPassword()),auths);
    }
}
