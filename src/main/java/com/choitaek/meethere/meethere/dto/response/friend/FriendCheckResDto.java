package com.choitaek.meethere.meethere.dto.response.friend;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendCheckResDto {

    @Schema(description = "응답 메시지")
    private String message;

    @Schema(description = "친구 uuid")
    private UUID uuid;
}
