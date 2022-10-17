package com.example.coursesite_final.member.exception;

public class ResetPasswordException extends RuntimeException{
    public ResetPasswordException(String error) {
        super(error);
    }
}
