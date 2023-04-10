package com.choitaek.meethere.meethere.dto.common.response;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorContentDto {

    @Schema(description = "에러 메시지")
    private String message;
}

