package com.choitaek.meethere.meethere.util;

import com.choitaek.meethere.meethere.dto.common.response.ResponseSuccessDto;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.util.TimeZone;

@Component
public class ResponseUtil<T> {

    public ResponseSuccessDto<T> successResponse(T data) {
        ResponseSuccessDto<T> res = ResponseSuccessDto
                .<T>builder()
                .timeStamp(ZonedDateTime.now(TimeZone.getTimeZone("UTC").toZoneId()))
                .code(HttpStatus.OK.value())
                .status(HttpStatus.OK.name())
                .data(data)
                .build();
        return res;
    }
}
