package com.github.maleksandrowicz93.cqrsdemo.security

import com.github.maleksandrowicz93.cqrsdemo.TestUtils
import spock.lang.Specification

class SecurityFacadeSpec extends Specification {

    EncodeService encodeService = SecurityStubFactory.INSTANCE.encodeService()
    SecurityFacade securityFacade = new SecurityFacade(encodeService)

    def "encode password"() {
        given: "password to be encoded"
        def password = "password"

        expect: "this password is successful encoded"
        securityFacade.encodePassword(password) == TestUtils.encodePassword(password)
    }
}
