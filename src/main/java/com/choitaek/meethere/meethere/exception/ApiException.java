package com.choitaek.meethere.meethere.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ApiException {
    private final int statusCode;
    private final String message;
}
