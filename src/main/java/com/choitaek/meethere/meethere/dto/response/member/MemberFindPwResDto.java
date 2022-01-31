package com.choitaek.meethere.meethere.dto.response.member;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MemberFindPwResDto {

    @Schema(description = "응답 메시지")
    private String message;

    @Schema(description = "탐색 정보")
    private String info;
}
