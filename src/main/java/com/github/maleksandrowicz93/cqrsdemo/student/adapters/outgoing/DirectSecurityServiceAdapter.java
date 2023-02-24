package com.github.maleksandrowicz93.cqrsdemo.student.adapters.outgoing;

import com.github.maleksandrowicz93.cqrsdemo.security.SecurityFacade;
import com.github.maleksandrowicz93.cqrsdemo.student.port.outgoing.SecurityService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Component;

@Component
@FieldDefaults(makeFinal = true)
@RequiredArgsConstructor
class DirectSecurityServiceAdapter implements SecurityService {

    SecurityFacade securityFacade;

    @Override
    public String encodePassword(String password) {
        return securityFacade.encodePassword(password);
    }
}
