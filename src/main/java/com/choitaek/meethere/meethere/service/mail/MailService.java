package com.choitaek.meethere.meethere.service.mail;

import com.choitaek.meethere.meethere.dto.MailDto;
import com.choitaek.meethere.meethere.entity.member.MemberEntity;
import com.choitaek.meethere.meethere.exception.ApiRequestException;
import com.choitaek.meethere.meethere.repository.jpa.member.MemberRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class MailService {

    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "YOUR_EMAIL_ADDRESS";

    private final MemberRepo memberRepo;

    // 메일 전송
    public void mailSend(MailDto mailDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(mailDto.getEmail());
        message.setFrom(MailService.FROM_ADDRESS);
        message.setSubject(mailDto.getTitle());
        message.setText(mailDto.getMessage());
        mailSender.send(message);
    }

    // 인증번호 메일
    public MailDto verification(String email, String name, int code) {
        MailDto mailDto = new MailDto();
        mailDto.setEmail(email);
        mailDto.setTitle(name + "님의 회원가입 인증번호 입니다");
        mailDto.setMessage("안녕하세요. 회원가입 인증 번호는 [" + code + "] 입니다.");
        return mailDto;
    }

    // 비밀번호 변경 메일
    public MailDto createMailAndChangePassword(String email, String name, String tempPw) {
        MailDto mailDto = new MailDto();
        mailDto.setEmail(email);
        mailDto.setTitle(name + "님의 임시 비밀번호 안내 이메일 입니다");
        mailDto.setMessage("안녕하세요. 회원님의 임시 비밀번호는 [" + tempPw + "] 입니다");
        updatePassword(email, tempPw);
        return mailDto;
    }

    // 비밀번호 변경
    public void updatePassword(String email, String pw) {
        MemberEntity memberEntity = memberRepo.findByEmail(email).orElseThrow(
                () -> new ApiRequestException("해당 회원이 존재하지 않습니다.")
        );

        String newPw = passwordEncoder.encode(pw);
        memberEntity.setPw(newPw);
    }
}
