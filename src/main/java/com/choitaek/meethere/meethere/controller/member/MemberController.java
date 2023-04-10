package com.choitaek.meethere.meethere.controller.member;

import com.choitaek.meethere.meethere.dto.common.response.ResponseSuccessDto;
import com.choitaek.meethere.meethere.dto.request.member.MemberLoginReqDto;
import com.choitaek.meethere.meethere.dto.request.member.MemberSaveReqDto;
import com.choitaek.meethere.meethere.dto.request.member.MemberUpdateReqDto;
import com.choitaek.meethere.meethere.dto.request.member.MemberVerifyReqDto;
import com.choitaek.meethere.meethere.dto.response.member.*;
import com.choitaek.meethere.meethere.service.friend.FriendService;
import com.choitaek.meethere.meethere.service.mail.MailService;
import com.choitaek.meethere.meethere.service.member.MemberService;
import com.choitaek.meethere.meethere.service.schedule.ScheduleService;
import com.choitaek.meethere.meethere.service.share.ShareService;
import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@Api("회원")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class MemberController {

    private final MemberService memberService;
    private final FriendService friendService;
    private final ScheduleService scheduleService;
    private final ShareService shareService;
    private final MailService mailService;

    // 회원가입
    @Operation(summary = "회원가입", description = "회원가입을 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @PostMapping("/member/save")
    public ResponseEntity<ResponseSuccessDto<MemberSaveResDto>> saveMember(
            @RequestBody @Valid MemberSaveReqDto memberSaveReqDto
    ) {
        return ResponseEntity.ok(memberService.save(memberSaveReqDto));
    }

    // 회원인증
    @Operation(summary = "회원인증", description = "회원인증을 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @PostMapping("/member/verify")
    public ResponseEntity<ResponseSuccessDto<MemberVerifyResDto>> verifyMember(
            @RequestBody @Valid MemberVerifyReqDto memberVerifyReqDto
    ) {
        return ResponseEntity.ok(memberService.activateMember(memberVerifyReqDto));
    }

    // 아이디 찾기(이름, 전화번호)
    @Operation(summary = "아이디 찾기", description = "아이디를 찾는다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @GetMapping("/member/find/email/{name}/{phone}")
    public ResponseEntity<ResponseSuccessDto<MemberFindEmailResDto>> findMemberEmail(
            @PathVariable String name, @PathVariable String phone
    ) {
        return ResponseEntity.ok(memberService.findMemberEmail(name, phone));
    }

    // 비밀번호 찾기(이메일, 이름, 전화번호)
    @Operation(summary = "비밀번호 찾기", description = "비밀번호를 찾는다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @GetMapping("/member/find/password/{email}/{name}/{phone}")
    public ResponseEntity<ResponseSuccessDto<MemberFindPwResDto>> findMemberPw(
            @PathVariable String email, @PathVariable String name, @PathVariable String phone
    ) {
        return ResponseEntity.ok(memberService.findMemberPw(email, name, phone));
    }

    // 전체 회원 리스트
    @Operation(summary = "전체 회원 조회", description = "전체 회원 목록을 조회한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @GetMapping("/member/list")
    public ResponseEntity<ResponseSuccessDto<Page<MemberSearchResDto>>> findMemberList() {
        return ResponseEntity.ok(memberService.findMembers());
    }

    // 로그인
    @Operation(summary = "로그인", description = "로그인을 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @GetMapping("/member/login")
    public ResponseEntity<ResponseSuccessDto<MemberLoginResDto>> login(
            @RequestBody @Valid MemberLoginReqDto memberLoginReqDto
    ) {
        return ResponseEntity.ok(memberService.login(memberLoginReqDto));
    }

    // 회원정보 수정
    @Operation(summary = "회원정보 수정", description = "회원정보를 수정한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @PutMapping("/member/update")
    public ResponseEntity<ResponseSuccessDto<MemberUpdateResDto>> updateMember(
            @RequestBody @Valid MemberUpdateReqDto memberUpdateReqDto
    ) {
        return ResponseEntity.ok(memberService.updateMember(memberUpdateReqDto));
    }

    // 회원 탈퇴
    @Operation(summary = "회원탈퇴", description = "회원탈퇴를 한다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "성공"),
            @ApiResponse(responseCode = "400", description = "실패", content = @Content)})
    @DeleteMapping("/member/delete/{uuid}")
    public ResponseEntity<ResponseSuccessDto<MemberDeleteResDto>> deleteMember(
            @PathVariable UUID uuid
    ) {
        return ResponseEntity.ok(memberService.deleteMember(uuid));
    }
}