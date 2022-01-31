package com.choitaek.meethere.meethere.dto.request.share;

import com.choitaek.meethere.meethere.dto.AddressObjectDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ShareSaveReqDto {

    @Schema(description = "도착지점 주소 정보")
    private AddressObjectDto destinationAddress;

    @Schema(description = "출발지점 주소 정보 리스트")
    private List<AddressObjectDto> startAddressList;
}
