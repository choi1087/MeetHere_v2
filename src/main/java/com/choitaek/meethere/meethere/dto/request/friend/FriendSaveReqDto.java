package com.choitaek.meethere.meethere.dto.request.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FriendSaveReqDto {

    @Schema(description = "회원 uuid")
    private UUID memberUuid;

    @Schema(description = "친구 uuid")
    private UUID friendUuid;
}
