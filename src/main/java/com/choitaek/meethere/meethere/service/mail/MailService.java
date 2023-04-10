package com.choitaek.meethere.meethere.service.mail;

import com.choitaek.meethere.meethere.dto.MailDto;
import com.choitaek.meethere.meethere.entity.member.MemberEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private static final String FROM_ADDRESS = "YOUR_EMAIL_ADDRESS";

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
        String title = name + "님의 회원가입 인증번호 입니다.";
        String message = "안녕하세요. 회원가입 인증번호는 [" + code + "] 입니다.";
        return new MailDto(email, title, message);
    }

    // 비밀번호 변경 메일
    public MailDto createMailAndChangePassword(MemberEntity memberEntity) {
        String tempPw = "qwe123!@#";
        String title = memberEntity.getName() + "님의 임시 비밀번호 안내 메일 입니다.";
        String message = "안녕하세요. 회원님의 임시 비밀번호는 [" + tempPw + "] 입니다";
        updatePassword(memberEntity, tempPw);
        return new MailDto(memberEntity.getEmail(), title, message);
    }

    // 비밀번호 변경
    public void updatePassword(MemberEntity memberEntity, String pw) {
        String newPw = passwordEncoder.encode(pw);
        memberEntity.updatePw(newPw);
    }
}
