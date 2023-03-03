package com.github.maleksandrowicz93.cqrsdemo

class TestUtils {

    private TestUtils() {}

    static def encodePassword(String password) {
        def bytes = password.bytes
        def encodedBytes = Base64.getEncoder().encode(bytes)
        return new String(encodedBytes)
    }
}
