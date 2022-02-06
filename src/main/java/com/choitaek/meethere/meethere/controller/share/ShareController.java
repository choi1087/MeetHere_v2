package com.choitaek.meethere.meethere.controller.share;

import com.choitaek.meethere.meethere.dto.common.response.ResponseSuccessDto;
import com.choitaek.meethere.meethere.dto.request.share.ShareSaveReqDto;
import com.choitaek.meethere.meethere.dto.response.share.ShareAddressSearchResDto;
import com.choitaek.meethere.meethere.dto.response.share.ShareSaveResDto;
import com.choitaek.meethere.meethere.dto.response.share.ShareSearchDestinationResDto;
import com.choitaek.meethere.meethere.dto.response.share.ShareSearchStartResDto;
import com.choitaek.meethere.meethere.service.share.ShareService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Api("공유")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ShareController {

    private final ShareService shareService;

    // 공유코드 저장
    @Operation(summary = "공유코드 저장", description = "공유코드를 저장한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @PostMapping("/share/save")
    public ResponseEntity<ResponseSuccessDto<ShareSaveResDto>> saveShare(
            @RequestBody ShareSaveReqDto shareSaveReqDto
    ) {
        return ResponseEntity.ok(shareService.saveShare(shareSaveReqDto));
    }

    // 공유코드 - 도착지점 불러오기
    @Operation(summary = "공유코드의 도착지점 불러오기", description = "공유코드의 도착지점을 불러온다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @GetMapping("/share/destination/{code}")
    public ResponseEntity<ResponseSuccessDto<ShareSearchDestinationResDto>> searchDestination(
            @PathVariable String code
    ) {
        return ResponseEntity.ok(shareService.searchShareDestination(code));
    }

    // 공유코드 - 시작지점 리스트 불러오기
    @Operation(summary = "공유코드의 출발지점 리스트 불러오기", description = "공유코드의 출발지점 리스트를 불러온다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @GetMapping("/share/start/{uuid}")
    public ResponseEntity<ResponseSuccessDto<List<ShareSearchStartResDto>>> searchStartList(
            @PathVariable UUID uuid
    ) {
        return ResponseEntity.ok(shareService.searchShareStartList(uuid));
    }
}
