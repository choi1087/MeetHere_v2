package com.choitaek.meethere.meethere.dto.response.share;

import com.choitaek.meethere.meethere.dto.share.ShareAddressObjectDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShareAddressSearchResDto {

    @Schema(description = "응답 메시지")
    private String message;

    @Schema(description = "공유 출발지점 리스트")
    private List<ShareAddressObjectDto> startAddressList;
}
