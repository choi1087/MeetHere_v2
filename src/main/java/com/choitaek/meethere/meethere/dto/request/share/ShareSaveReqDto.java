package com.choitaek.meethere.meethere.dto.request.share;

import com.choitaek.meethere.meethere.dto.share.ShareObjectDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShareSaveReqDto {

    @Schema(description = "도착지점 주소 정보")
    private ShareObjectDto destinationAddress;

    @Schema(description = "출발지점 주소 정보 리스트")
    private List<ShareObjectDto> startAddressList;
}
