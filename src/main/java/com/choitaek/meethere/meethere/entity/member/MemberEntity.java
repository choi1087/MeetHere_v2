package com.choitaek.meethere.meethere.entity.member;

import com.choitaek.meethere.meethere.dto.request.member.MemberSaveReqDto;
import com.choitaek.meethere.meethere.dto.request.member.MemberUpdateReqDto;
import com.choitaek.meethere.meethere.entity.TimeEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Setter
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

    @Column(name = "pw", columnDefinition = "varchar(50)")
    private String pw;

    @Column(name = "name", columnDefinition = "varchar(10)")
    private String name;

    @Column(name = "phone", columnDefinition = "varchar(20)")
    private String phone;

    @Column(name = "is_active", columnDefinition = "boolean")
    private Boolean isActive;

    @Column(name = "verification", columnDefinition = "integer")
    private int authNum;

    public void createMember(MemberSaveReqDto memberSaveReqDto) {
        this.email = memberSaveReqDto.getEmail();
        this.pw = memberSaveReqDto.getPw();
        this.name = memberSaveReqDto.getName();
        this.phone = memberSaveReqDto.getPhone();
    }

    public void updateMember(MemberUpdateReqDto memberUpdateReqDto) {
        this.pw = memberUpdateReqDto.getPw();
        this.name = memberUpdateReqDto.getName();
        this.phone = memberUpdateReqDto.getPhone();
    }
}
