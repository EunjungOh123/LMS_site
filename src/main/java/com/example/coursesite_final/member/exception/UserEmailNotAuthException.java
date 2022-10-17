package com.example.coursesite_final.member.exception;

public class UserEmailNotAuthException extends RuntimeException {
    public UserEmailNotAuthException(String error) {
        super(error);
    }
}
