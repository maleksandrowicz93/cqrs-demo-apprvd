package com.github.maleksandrowicz93.cqrsdemo.security;

import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
public class SecurityFacade {

    EncodeService encodeService;

    public String encodePassword(String password) {
        return encodeService.encode(password);
    }
}
