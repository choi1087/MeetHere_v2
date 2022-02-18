package com.choitaek.meethere.meethere.controller.friend;

import com.choitaek.meethere.meethere.dto.common.response.ResponseSuccessDto;
import com.choitaek.meethere.meethere.dto.request.friend.FriendCheckReqDto;
import com.choitaek.meethere.meethere.dto.request.friend.FriendSaveReqDto;
import com.choitaek.meethere.meethere.dto.response.friend.FriendCheckResDto;
import com.choitaek.meethere.meethere.dto.response.friend.FriendDeleteResDto;
import com.choitaek.meethere.meethere.dto.response.friend.FriendSaveResDto;
import com.choitaek.meethere.meethere.dto.response.friend.FriendSearchResDto;
import com.choitaek.meethere.meethere.service.friend.FriendService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Api("친구")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class FriendController {

    private final FriendService friendService;

    // 친구 찾기
    @Operation(summary = "친구 찾기", description = "친구 데이터를 찾는다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @GetMapping("/friend/check")
    public ResponseEntity<ResponseSuccessDto<FriendCheckResDto>> checkFriend(
            @RequestBody @Valid FriendCheckReqDto friendCheckReqDto
    ) {
        return ResponseEntity.ok(friendService.checkFriend(friendCheckReqDto));
    }

    // 친구 추가
    @Operation(summary = "친구 추가", description = "친구 추가를 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @PostMapping("/friend/save")
    public ResponseEntity<ResponseSuccessDto<FriendSaveResDto>> saveFriend(
            @RequestBody @Valid FriendSaveReqDto friendSaveReqDto
    ) {
        return ResponseEntity.ok(friendService.saveFriend(friendSaveReqDto));
    }

    // 회원의 친구 목록 조회
    @Operation(summary = "친구 목록 조회", description = "친구 목록을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @GetMapping("/friend/search/{uuid}")
    public ResponseEntity<ResponseSuccessDto<FriendSearchResDto>> searchFriend(
            @PathVariable UUID uuid
    ) {
        return ResponseEntity.ok(friendService.searchFriend(uuid));
    }

    // 친구 삭제
    @Operation(summary = "친구 삭제", description = "친구 삭제를 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @DeleteMapping("/friend/delete/{uuid}")
    public ResponseEntity<ResponseSuccessDto<FriendDeleteResDto>> deleteFriend(
            @PathVariable UUID uuid
    ) {
        return ResponseEntity.ok(friendService.deleteFriend(uuid));
    }
}