package com.choitaek.meethere.meethere.errorhandling.exception.service;

import com.choitaek.meethere.meethere.errorhandling.exception.DefaultException;

public class RegisterErrorException extends DefaultException {
    public RegisterErrorException(String message) {
        super(message);
    }
}
