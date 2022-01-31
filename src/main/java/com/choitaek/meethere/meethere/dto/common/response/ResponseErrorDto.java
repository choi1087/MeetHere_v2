package com.choitaek.meethere.meethere.dto.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "API 에러 응답")
@Getter
@Setter
public class ResponseErrorDto<T> extends ResponseCommonDto {

    @Schema(description = "API 경로")
    private String path;

    @Schema(description = "에러")
    private T error;

    @Builder
    public ResponseErrorDto(LocalDateTime timeStamp, int code, String status, String path, T error) {
        super(timeStamp, code, status);
        this.path = path;
        this.error = error;
    }
}
