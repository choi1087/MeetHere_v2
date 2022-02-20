package com.choitaek.meethere.meethere.errorhandling.exception.service;

import com.choitaek.meethere.meethere.errorhandling.exception.DefaultException;

public class LoginErrorException extends DefaultException {
    public LoginErrorException(String message) {
        super(message);
    }
}
