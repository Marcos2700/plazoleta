package com.pragma.powerup.infrastructure.security;

import com.pragma.powerup.infrastructure.out.feign.UserFeignClient;
import com.pragma.powerup.infrastructure.out.feign.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserDto user = userFeignClient.getUserByEmail(email);

        Map<String, String> userData = new HashMap<>();
        userData.put("email", user.getEmail());
        userData.put("password", user.getPassword());
        userData.put("role", user.getRole().getRoleName());
        userData.put("name", user.getName());

        return new UserDetailsImpl(userData);
    }
}
