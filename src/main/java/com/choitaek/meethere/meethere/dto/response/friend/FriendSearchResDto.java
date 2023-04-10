package com.choitaek.meethere.meethere.dto.response.friend;

import com.choitaek.meethere.meethere.dto.friend.FriendObjectDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class FriendSearchResDto {

    @Schema(description = "응답 메시지")
    private String message;

    @Schema(description = "친구 목록")
    private List<FriendObjectDto> friendList;
}
