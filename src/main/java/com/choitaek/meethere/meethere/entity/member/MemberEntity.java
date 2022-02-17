package com.choitaek.meethere.meethere.entity.member;

import com.choitaek.meethere.meethere.dto.request.member.MemberSaveReqDto;
import com.choitaek.meethere.meethere.dto.request.member.MemberUpdateReqDto;
import com.choitaek.meethere.meethere.entity.TimeEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Random;
import java.util.UUID;

@Entity
@Getter
@Table(name = "member")
@AllArgsConstructor
@NoArgsConstructor
public class MemberEntity extends TimeEntity {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false, columnDefinition = "uuid", name = "uuid")
    private UUID uuid;

    @Column(name = "email", columnDefinition = "varchar(50)")
    private String email;

    @Column(name = "pw", columnDefinition = "varchar(100)")
    private String pw;

    @Column(name = "name", columnDefinition = "varchar(10)")
    private String name;

    @Column(name = "phone", columnDefinition = "varchar(20)")
    private String phone;

    @Column(name = "is_active", columnDefinition = "boolean")
    private Boolean isActive;

    @Column(name = "verification", columnDefinition = "integer")
    private int authNum;

    public void createMember(MemberSaveReqDto memberSaveReqDto, String pw) {
        Random random = new Random(System.currentTimeMillis());
        this.email = memberSaveReqDto.getEmail();
        this.pw = pw;
        this.name = memberSaveReqDto.getName();
        this.phone = memberSaveReqDto.getPhone();
        this.authNum = 10000 + random.nextInt(900000);
        this.isActive = false;
    }

    public void updateMember(MemberUpdateReqDto memberUpdateReqDto, String newPw) {
        this.pw = newPw;
        this.name = memberUpdateReqDto.getName();
        this.phone = memberUpdateReqDto.getPhone();
    }

    public void updatePw(String newPw) {
        this.pw = newPw;
    }

    public void updateIsActive(boolean isActive){
        this.isActive = isActive;
    }
}
