package com.choitaek.meethere.meethere.dto.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendObjectDto {

    @Schema(description = "친구 uuid")
    @NotNull(message = "친구 uuid는 필수입니다.")
    private UUID uuid;

    @Schema(description = "이름")
    @NotBlank(message = "이름은 필수입니다.")
    private String name;

    @Schema(description = "이메일")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @Schema(description = "전화번호")
    @NotBlank(message = "전화번호는 필수입니다.")
    private String phone;
}
