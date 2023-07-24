package com.pragma.powerup.securityTests;

import com.pragma.powerup.infrastructure.security.TokenUtils;
import com.pragma.powerup.infrastructure.security.UserDetailsImpl;
import io.jsonwebtoken.JwtException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.HashMap;
import java.util.Map;

@ExtendWith(SpringExtension.class)
class TokenUtilsTests {

    @InjectMocks
    TokenUtils tokenUtils;

    @Test
    void createToken(){
        Map<String, String> userData = new HashMap<>();
        userData.put("email", "Example.com");
        userData.put("password", "xxxxxxxxxxxxxxx");
        userData.put("name", "Example");
        userData.put("role", "administrator");

        UserDetailsImpl userDetails = new UserDetailsImpl(userData);

        String token = tokenUtils.createToken(userDetails);

        Assertions.assertInstanceOf(String.class, token);
        Assertions.assertNotNull(token);
    }

    @Test
    void authenticateWithWrongToken(){
        String wrongToken = "xxxxxxxxxxxxxxxxxxx";
        try {
            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    tokenUtils.getAuthentication(wrongToken);
        }
        catch (JwtException e){
            Assertions.assertInstanceOf(JwtException.class, e);
        }
    }

}
