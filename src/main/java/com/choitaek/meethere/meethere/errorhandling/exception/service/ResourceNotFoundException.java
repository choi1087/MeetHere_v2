package com.choitaek.meethere.meethere.errorhandling.exception.service;

import com.choitaek.meethere.meethere.errorhandling.exception.DefaultException;

public class ResourceNotFoundException extends DefaultException {
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
