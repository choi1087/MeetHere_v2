package com.choitaek.meethere.meethere.dto.request.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendCheckReqDto {

    @Schema(description = "친구 이메일")
    @NotBlank(message = "친구 이메일은 필수입니다.")
    private String email;

    @Schema(description = "친구 이름")
    @NotBlank(message = "친구 이름은 필수입니다.")
    private String name;

    @Schema(description = "친구 전화번호")
    @NotBlank(message = "친구 전화번호는 필수입니다.")
    private String phone;
}
