package com.choitaek.meethere.meethere.service.member;

import com.choitaek.meethere.meethere.dto.AddressObjectDto;
import com.choitaek.meethere.meethere.dto.common.response.ResponseSuccessDto;
import com.choitaek.meethere.meethere.dto.request.member.MemberLoginReqDto;
import com.choitaek.meethere.meethere.dto.request.member.MemberSaveReqDto;
import com.choitaek.meethere.meethere.dto.request.member.MemberUpdateReqDto;
import com.choitaek.meethere.meethere.dto.request.member.MemberVerifyReqDto;
import com.choitaek.meethere.meethere.dto.response.member.*;
import com.choitaek.meethere.meethere.entity.member.MemberAddressEntity;
import com.choitaek.meethere.meethere.entity.member.MemberEntity;
import com.choitaek.meethere.meethere.repository.jpa.member.MemberAddressRepo;
import com.choitaek.meethere.meethere.repository.jpa.member.MemberRepo;
import com.choitaek.meethere.meethere.util.ResponseUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MemberService {

    private final ResponseUtil responseUtil;
    private final PasswordEncoder passwordEncoder;

    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "YOUR_EMAIL_ADDRESS";

    private final MemberRepo memberRepo;
    private final MemberAddressRepo memberAddressRepo;

    // 회원가입
    public ResponseSuccessDto<MemberSaveResDto> save(MemberSaveReqDto memberSaveReqDto) {

        // 중복 회원 검증
        if (!validateDuplicateMember(memberSaveReqDto)) {
            System.out.println("회원 가입 실패 (중복회원)");
            return responseUtil.successResponse(new MemberSaveResDto("회원가입 실패"));
        }

        // 회원 정보
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.createMember(memberSaveReqDto);
        Random random = new Random(System.currentTimeMillis());
        memberEntity.setAuthNum(100000 + random.nextInt(900000));
        memberEntity.setIsActive(false);

        // 회원 주소 정보
        MemberAddressEntity memberAddressEntity = new MemberAddressEntity();
        memberAddressEntity.createMemberAddress(memberSaveReqDto.getAddressObjectDto(), memberEntity);

        memberRepo.save(memberEntity);
        memberAddressRepo.save(memberAddressEntity);

        MemberSaveResDto memberSaveResDto = new MemberSaveResDto("인증번호 발송");
        ResponseSuccessDto<MemberSaveResDto> res = responseUtil.successResponse(memberSaveResDto);
        return res;
    }

    // 회원 활성화 (인증 성공시)
    public ResponseSuccessDto<MemberVerifyResDto> activateMember(MemberVerifyReqDto memberVerifyReqDto) {
        ResponseSuccessDto<MemberVerifyResDto> res;
        Optional<MemberEntity> byEmail = memberRepo.findByEmail(memberVerifyReqDto.getEmail());
        MemberEntity member = byEmail.get();

        if (byEmail.isEmpty()) {
            return responseUtil.successResponse(new MemberVerifyResDto("해당 회원이 존재하지 않습니다."));
        }

        if (member.getAuthNum() == memberVerifyReqDto.getAuthNum()) {
            member.setIsActive(true);
            res = responseUtil.successResponse(new MemberVerifyResDto("인증 성공"));
            return res;
        }
        res = responseUtil.successResponse(new MemberVerifyResDto("인증 실패"));
        return res;
    }

    // 로그인
    public ResponseSuccessDto<MemberLoginResDto> login(MemberLoginReqDto memberLoginReqDto) {
        MemberEntity member = memberRepo.findOneByEmail(memberLoginReqDto.getEmail());
        if (member == null) {
            try {
                throw new Exception("해당 회원이 존재하지 않습니다");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        System.out.println("입력 비밀번호 = " + memberLoginReqDto.getPw());
        System.out.println("멤버 비밀번호 = " + member.getPw());
        /*if (!passwordEncoder.matches(memberLoginReqDto.getPw(), member.getPw())) {

        }*/
        if (!memberLoginReqDto.getPw().equals(member.getPw())) {
            return responseUtil.successResponse(new MemberLoginResDto("로그인 실패"));
        }

        MemberLoginResDto memberLoginResDto = new MemberLoginResDto("로그인 성공");
        ResponseSuccessDto<MemberLoginResDto> res = responseUtil.successResponse(memberLoginResDto);
        return res;
    }

    // 전체 회원 조회
    @Transactional(readOnly = true)
    public ResponseSuccessDto<Page<MemberSearchResDto>> findMembers() {
        Page<MemberEntity> page = memberRepo.findAll(PageRequest.of(0, 20));
        Page<MemberSearchResDto> toMap = page.map(this::getMemberSearchResDto);
        ResponseSuccessDto<Page<MemberSearchResDto>> res = responseUtil.successResponse(toMap);
        return res;
    }

    // 아이디로 회원 조회
    @Transactional(readOnly = true)
    public ResponseSuccessDto<MemberSearchResDto> findOne(UUID uuid) {
        MemberEntity member = memberRepo.findOneByUuid(uuid);
        MemberSearchResDto findMember = getMemberSearchResDto(member);
        ResponseSuccessDto<MemberSearchResDto> res = responseUtil.successResponse(findMember);
        return res;
    }

    // 이메일로 회원 조회
    @Transactional(readOnly = true)
    public ResponseSuccessDto<MemberSearchResDto> findByEmail(String email) {
        MemberEntity member = memberRepo.findOneByEmail(email);
        MemberSearchResDto findMember = getMemberSearchResDto(member);
        ResponseSuccessDto<MemberSearchResDto> res = responseUtil.successResponse(findMember);
        return res;
    }
    
    // 회원 이메일 찾기 (이름, 휴대전화)
    @Transactional(readOnly = true)
    public ResponseSuccessDto<MemberFindEmailResDto> findMemberEmail(String name, String phone) {
        MemberEntity member = memberRepo.findOneByNameAndPhone(name, phone);
        if (member == null) {
            try {
                throw new Exception("해당 회원이 존재하지 않습니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        MemberFindEmailResDto memberFindEmailResDto = new MemberFindEmailResDto("회원 조회 성공", member.getEmail());
        ResponseSuccessDto<MemberFindEmailResDto> res = responseUtil.successResponse(memberFindEmailResDto);
        return res;
    }

    // 회원 비밀번호 찾기 (이메일, 이름, 휴대전화)
    @Transactional(readOnly = true)
    public ResponseSuccessDto<MemberFindPwResDto> findMemberPw(String email, String name, String phone) {
        MemberEntity findMember = memberRepo.findOneByEmailAndNameAndPhone(email, name, phone);
        if (findMember == null) {
            try {
                throw new Exception("해당 회원이 존재하지 않습니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        MemberFindPwResDto memberFindPwResDto = new MemberFindPwResDto(
                "회원 조회 성공", "비밀번호가 임시 비밀번호로 변경되었습니다. 임시 비밀번호는 해당 계정의 이메일로 발송하였습니다."
        );
        ResponseSuccessDto<MemberFindPwResDto> res = responseUtil.successResponse(memberFindPwResDto);
        return res;
    }

    // 회원 정보 수정
    public ResponseSuccessDto<MemberUpdateResDto> updateMember(MemberUpdateReqDto memberUpdateReqDto) {
        System.out.println("1차비번 = " + memberUpdateReqDto.getPw());
        System.out.println("2차비번 = " + memberUpdateReqDto.getCheckedPw());
        if (!memberUpdateReqDto.getPw().equals(memberUpdateReqDto.getCheckedPw())) {
            try {
                throw new Exception("비밀번호가 일치하지 않습니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        MemberEntity member = memberRepo.findOneByUuid(memberUpdateReqDto.getUuid());
        member.updateMember(memberUpdateReqDto);

        MemberUpdateResDto memberUpdateResDto = new MemberUpdateResDto("수정 완료");
        ResponseSuccessDto<MemberUpdateResDto> res = responseUtil.successResponse(memberUpdateResDto);
        return res;
    }
    
    // 회원 탈퇴
    public ResponseSuccessDto<MemberDeleteResDto> deleteMember(UUID uuid) {
        MemberEntity member = memberRepo.findOneByUuid(uuid);
        MemberAddressEntity memberAddress = memberAddressRepo.findOneByMemberEntity(member);
        memberAddressRepo.delete(memberAddress);
        memberRepo.delete(member);

        MemberDeleteResDto memberDeleteResDto = new MemberDeleteResDto("삭제 완료");
        ResponseSuccessDto<MemberDeleteResDto> res = responseUtil.successResponse(memberDeleteResDto);
        return res;
    }
    
    // 중복 회원 확인
    private Boolean validateDuplicateMember(MemberSaveReqDto memberSaveReqDto) {
        // 중복 이메일 확인
        Optional<MemberEntity> byEmail = memberRepo.findByEmail(memberSaveReqDto.getEmail());
        if (byEmail.isEmpty()) {
            System.out.println("회원 가능 이메일");
            return true;
        }

        MemberEntity member = byEmail.get();

        // 인증 절차가 안된 이메일이라면, 이전 데이터 삭제
        if (!member.getIsActive()) {
            System.out.println("미인증 메일 데이터 삭제");
            memberRepo.delete(member);
        } else {
            System.out.println("이미 존재하는 회원입니다.");
            return false;
        }

        // 중복 이름, 번호
        Page<MemberEntity> findMember = memberRepo.findByName(memberSaveReqDto.getName(), PageRequest.of(0, 20));
        for (MemberEntity memberEntity : findMember) {
            if (memberEntity.getPhone().equals(memberSaveReqDto.getPhone())) {
                System.out.println("이미 존재하는 회원입니다");
                return false;
            }
        }
        return true;
    }

    // MemberEntity -> MemberSearchResDto
    private MemberSearchResDto getMemberSearchResDto(MemberEntity member) {
        MemberSearchResDto memberSearchResDto = new MemberSearchResDto(
                member.getUuid(), member.getEmail(), member.getPw(), member.getName(), member.getPhone(),
                changeMemberEntityToDto(memberAddressRepo.findOneByMemberEntity(member))
        );
        return memberSearchResDto;
    }
    
    // MemberAddressEntity -> AddressObjectDto
    private AddressObjectDto changeMemberEntityToDto(MemberAddressEntity memberAddressEntity) {
        AddressObjectDto addressObjectDto = new AddressObjectDto(
                memberAddressEntity.getAddressName(),
                memberAddressEntity.getPlaceName(),
                memberAddressEntity.getRoadName(),
                memberAddressEntity.getLat(),
                memberAddressEntity.getLat()
        );
        return addressObjectDto;
    }

//    @Override
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        MemberEntity member = memberRepo.findOneByEmail(email);
//        if (member == null) {
//            try {
//                throw new Exception("해당 회원이 존재하지 않습니다.");
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//
//        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        grantedAuthorities.add(new SimpleGrantedAuthority("TRUE"));
//
//        return new User(member.getEmail(), member.getPw(), grantedAuthorities);
//    }
}