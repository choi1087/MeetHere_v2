package com.choitaek.meethere.meethere.controller.schedule;

import com.choitaek.meethere.meethere.dto.common.response.ResponseSuccessDto;
import com.choitaek.meethere.meethere.dto.request.schedule.ScheduleSaveReqDto;
import com.choitaek.meethere.meethere.dto.request.schedule.ScheduleUpdateReqDto;
import com.choitaek.meethere.meethere.dto.response.schedule.*;
import com.choitaek.meethere.meethere.service.friend.FriendService;
import com.choitaek.meethere.meethere.service.member.MemberService;
import com.choitaek.meethere.meethere.service.schedule.ScheduleService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api("스케쥴")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ScheduleController {

    private final ScheduleService scheduleService;

    // 스케쥴 저장
    @Operation(summary = "스케쥴 저장", description = "스케쥴 저장을 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @PostMapping("/schedule/save")
    public ResponseEntity<ResponseSuccessDto<ScheduleSaveResDto>> saveSchedule(
            @RequestBody ScheduleSaveReqDto scheduleSaveReqDto
    ) {
        return ResponseEntity.ok(scheduleService.saveSchedule(scheduleSaveReqDto));
    }

    // 회원의 스케쥴 목록 조회
    @Operation(summary = "스케쥴 목록 불러오기", description = "스케쥴 목록을 불러온다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @GetMapping("/schedule/list/{memberUuid}")
    public ResponseEntity<ResponseSuccessDto<ScheduleSearchResDto>> searchSchedule(
            @PathVariable UUID memberUuid
    ) {
        return ResponseEntity.ok(scheduleService.searchScheduleList(memberUuid));
    }

    // 스케쥴 상세 조회
    @Operation(summary = "스케쥴 상세 조회", description = "스케쥴 상세 조회를 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @GetMapping("/schedule/list/start/{scheduleUuid}")
    public ResponseEntity<ResponseSuccessDto<ScheduleAddressSearchResDto>> searchStartAddress(
            @PathVariable UUID scheduleUuid
    ) {
        return ResponseEntity.ok(scheduleService.searchStartAddressList(scheduleUuid));
    }

    // 스케쥴 수정
    @Operation(summary = "스케쥴 수정", description = "스케쥴을 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @PutMapping("/schedule/update")
    public ResponseEntity<ResponseSuccessDto<ScheduleUpdateResDto>> updateSchedule(
            @RequestBody ScheduleUpdateReqDto scheduleUpdateReqDto
    ) {
        return ResponseEntity.ok(scheduleService.updateSchedule(scheduleUpdateReqDto));
    }

    // 스케쥴 삭제
    @Operation(summary = "스케쥴 삭제", description = "스케쥴을 삭제를 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @DeleteMapping("/schedule/delete/{scheduleUuid}")
    public ResponseEntity<ResponseSuccessDto<ScheduleDeleteResDto>> deleteSchedule(
            @PathVariable UUID scheduleUuid
    ) {
        return ResponseEntity.ok(scheduleService.deleteSchedule(scheduleUuid));
    }
}
