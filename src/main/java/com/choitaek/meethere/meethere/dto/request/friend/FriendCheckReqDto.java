package com.choitaek.meethere.meethere.dto.request.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendCheckReqDto {

    @Schema(description = "친구 이메일")
    private String email;

    @Schema(description = "친구 이름")
    private String name;

    @Schema(description = "친구 전화번호")
    private String phone;
}
