package com.choitaek.meethere.meethere.entity.friend;

import com.choitaek.meethere.meethere.entity.member.MemberEntity;
import com.choitaek.meethere.meethere.entity.TimeEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Table(name = "friend")
@NoArgsConstructor
@AllArgsConstructor
public class FriendEntity extends TimeEntity {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false, columnDefinition = "uuid", name = "uuid")
    private UUID uuid;

    @Column(name = "name", columnDefinition = "varchar(10)")
    private String name;

    @Column(name = "email", columnDefinition = "varchar(50)")
    private String email;

    @Column(name = "phone", columnDefinition = "varchar(20)")
    private String phone;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_uuid", nullable = false)
    private MemberEntity memberEntity;

    public void createFriend(MemberEntity friend, MemberEntity member) {
        this.name = friend.getName();
        this.email = friend.getEmail();
        this.phone = friend.getPhone();
        this.memberEntity = member;
    }
}
