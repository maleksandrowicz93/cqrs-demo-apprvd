package com.github.maleksandrowicz93.cqrsdemo.student

class SnapshotFactory {

    private SnapshotFactory() {}

    private static final EMAIL = "email"
    private static final PASSWORD = "password"
    private static final UUID ID = UUID.randomUUID()

    static def studentToAdd() {
        StudentSnapshot.builder()
                .email(EMAIL)
                .password(PASSWORD)
                .build()
    }

    static def addedStudent() {
        StudentSnapshot.builder()
                .id(ID)
                .email(EMAIL)
                .password(PASSWORD)
                .build()
    }

    static def newStudentData() {
        StudentSnapshot.builder()
                .id(ID)
                .email("newEmail")
                .password("newPassword")
                .build()
    }

    static def incorrectSnapshots() {
        [
                StudentSnapshot.builder().email(null).password(PASSWORD).build(),
                StudentSnapshot.builder().email(" ").password(PASSWORD).build(),
                StudentSnapshot.builder().email(EMAIL).password(null).build(),
                StudentSnapshot.builder().email(EMAIL).password(" ").build()
        ]
    }
}