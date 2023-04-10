package com.choitaek.meethere.meethere.repository.jpa.member;

import com.choitaek.meethere.meethere.entity.member.MemberAddressEntity;
import com.choitaek.meethere.meethere.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MemberAddressRepo extends JpaRepository<MemberAddressEntity, UUID> {
    MemberAddressEntity findOneByMemberEntity(MemberEntity memberEntity);
}
