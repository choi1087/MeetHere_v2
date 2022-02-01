package com.choitaek.meethere.meethere.service.member;

import com.choitaek.meethere.meethere.dto.AddressObjectDto;
import com.choitaek.meethere.meethere.dto.common.response.ResponseSuccessDto;
import com.choitaek.meethere.meethere.dto.request.member.MemberSaveReqDto;
import com.choitaek.meethere.meethere.dto.request.member.MemberUpdateReqDto;
import com.choitaek.meethere.meethere.dto.response.member.MemberDeleteResDto;
import com.choitaek.meethere.meethere.dto.response.member.MemberSaveResDto;
import com.choitaek.meethere.meethere.dto.response.member.MemberSearchResDto;
import com.choitaek.meethere.meethere.dto.response.member.MemberUpdateResDto;
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
public class MemberService implements UserDetailsService {

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
            log.debug("회원 가입 실패 (중복회원)");
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
        memberAddressEntity.createMemberAddress(memberSaveReqDto.getAddressObjectDto());

        memberAddressRepo.save(memberAddressEntity);
        memberRepo.save(memberEntity);

        MemberSaveResDto memberSaveResDto = new MemberSaveResDto("인증번호 발송");
        ResponseSuccessDto<MemberSaveResDto> res = responseUtil.successResponse(memberSaveResDto);
        return res;
    }

    // 회원 활성화 (인증 성공시)
    public Boolean activateMember(String email, int authNum) {
        MemberEntity member = memberRepo.findOneByEmail(email);
        if (member.getAuthNum() == authNum) {
            member.setIsActive(true);
            return true;
        }
        return false;
    }

    // 로그인
    public ResponseSuccessDto<String> login(String email, String pw) {
        MemberEntity member = memberRepo.findOneByEmail(email);
        if (member == null) {
            try {
                throw new Exception("해당 회원이 존재하지 않습니다");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!passwordEncoder.matches(pw, member.getPw())) {
            try {
                throw new Exception("비밀번호가 일치하지 않습니다");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        ResponseSuccessDto<String> res = responseUtil.successResponse("로그인 성공");
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
    
    // 회원 조회 (이메일, 이름, 휴대전화)
    public ResponseSuccessDto<MemberSearchResDto> findMemberByNameAndPhone(String email, String name, String phone) {
        MemberEntity member = memberRepo.findOneByEmail(email);
        if (member == null) {
            try {
                throw new Exception("해당 회원이 존재하지 않습니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!member.getName().equals(name)) {
            try {
                throw new Exception("이름이 일치하지 않습니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (!member.getPhone().equals(phone)) {
            try {
                throw new Exception("휴대전화가 일치하지 않습니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        MemberSearchResDto findMember = getMemberSearchResDto(member);
        ResponseSuccessDto<MemberSearchResDto> res = responseUtil.successResponse(findMember);
        return res;
    }
    
    // 회원 정보 수정
    public ResponseSuccessDto<MemberUpdateResDto> updateMember(MemberUpdateReqDto memberUpdateReqDto) {
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
        MemberAddressEntity memberAddress = memberAddressRepo.findOneByMemberUuid(member.getUuid());
        memberAddressRepo.delete(memberAddress);
        memberRepo.delete(member);

        MemberDeleteResDto memberDeleteResDto = new MemberDeleteResDto("삭제 완료");
        ResponseSuccessDto<MemberDeleteResDto> res = responseUtil.successResponse(memberDeleteResDto);
        return res;
    }
    
    // 중복 회원 확인
    private boolean validateDuplicateMember(MemberSaveReqDto memberSaveReqDto) {
        // 중복 이메일 확인
        MemberEntity member = memberRepo.findOneByEmail(memberSaveReqDto.getEmail());
        if (member != null) {

            // 인증 절차가 안된 이메일이라면, 이전 데이터 삭제
            if (!member.getIsActive()) {
                log.debug("미인증 메일 데이터 삭제");
                memberRepo.delete(member);
            } else {
                log.debug("이미 존재하는 회원입니다.");
                return false;
            }
        }

        // 중복 이름, 번호
        Page<MemberEntity> findMember = memberRepo.findByName(memberSaveReqDto.getName());
        for (MemberEntity memberEntity : findMember) {
            if (memberEntity.getPhone().equals(memberSaveReqDto.getPhone())) {
                log.debug("이미 존재하는 회원입니다");
                return false;
            }
        }
        return true;
    }

    // MemberEntity -> MemberSearchResDto
    private MemberSearchResDto getMemberSearchResDto(MemberEntity member) {
        MemberSearchResDto memberSearchResDto = new MemberSearchResDto(
                member.getUuid(), member.getEmail(), member.getPw(), member.getName(), member.getPhone(),
                changeMemberEntityToDto(memberAddressRepo.findOneByMemberUuid(member.getUuid()))
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

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        MemberEntity member = memberRepo.findOneByEmail(email);
        if (member == null) {
            try {
                throw new Exception("해당 회원이 존재하지 않습니다.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
        grantedAuthorities.add(new SimpleGrantedAuthority("TRUE"));

        return new User(member.getEmail(), member.getPw(), grantedAuthorities);
    }
}