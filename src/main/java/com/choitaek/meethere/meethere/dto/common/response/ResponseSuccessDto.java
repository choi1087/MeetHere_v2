package com.choitaek.meethere.meethere.dto.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDateTime;


@Schema(description = "API 응답")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseSuccessDto<T> extends ResponseCommonDto {

    @Schema(description = "데이터")
    private T data;

    @Builder
    public ResponseSuccessDto(LocalDateTime timeStamp, int code, String status, T data) {
        super(timeStamp, code, status);
        this.data = data;
    }
}
