package com.choitaek.meethere.meethere.dto.request.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendSaveReqDto {

    @Schema(description = "회원 uuid")
    @NotNull(message = "회원 uuid는 필수입니다.")
    private UUID memberUuid;

    @Schema(description = "친구 uuid")
    private UUID friendUuid;
}
