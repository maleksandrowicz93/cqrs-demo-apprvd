package com.github.maleksandrowicz93.cqrsdemo.security;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class SecurityFacade {

    PasswordEncoder passwordEncoder;

    public String encodePassword(String password) {
        return passwordEncoder.encode(password);
    }
}
