package com.github.maleksandrowicz93.cqrsdemo.student;

import com.github.maleksandrowicz93.cqrsdemo.security.SecurityFacade;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class InjectableSecurityServiceAdapter implements SecurityService {

    SecurityFacade securityFacade;

    @Override
    public String encodePassword(String password) {
        return securityFacade.encodePassword(password);
    }
}
