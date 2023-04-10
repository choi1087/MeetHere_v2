package com.choitaek.meethere.meethere.errorhandling.exception.service;

import com.choitaek.meethere.meethere.errorhandling.exception.DefaultException;

public class DuplicateErrorException extends DefaultException {
    public DuplicateErrorException(String message) {
        super(message);
    }
}
