package com.choitaek.meethere.meethere.errorhandling.exception.service;

import com.choitaek.meethere.meethere.errorhandling.exception.DefaultException;

public class VerifyErrorException extends DefaultException {
    public VerifyErrorException(String message) {
        super(message);
    }
}
