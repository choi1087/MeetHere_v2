package com.choitaek.meethere.meethere.dto.common.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Schema(description = "API 응답")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResponseCommonDto {

    @Schema(description = "응답 시각, UTC 0")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
    private LocalDateTime timeStamp;
    @Schema(description = "응답 코드")
    private int code;
    @Schema(description = "응답 상태")
    private String status;
}
