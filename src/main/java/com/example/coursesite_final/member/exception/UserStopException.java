package com.example.coursesite_final.member.exception;

public class UserStopException extends RuntimeException{
    public UserStopException(String error) {
        super(error);
    }
}
