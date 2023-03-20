package com.github.maleksandrowicz93.cqrsdemo.security

import com.github.maleksandrowicz93.cqrsdemo.TestUtils
import spock.lang.Specification

class SecurityStubFactory extends Specification {

    static final INSTANCE = new SecurityStubFactory()

    EncodeService encodeService() {
        Stub(EncodeService) {
            encode(_ as String) >> { String password -> TestUtils.encodePassword(password) }
        }
    }
}
