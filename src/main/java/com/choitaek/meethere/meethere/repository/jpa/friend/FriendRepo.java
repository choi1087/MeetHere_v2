package com.choitaek.meethere.meethere.repository.jpa.friend;

import com.choitaek.meethere.meethere.entity.friend.FriendEntity;
import com.choitaek.meethere.meethere.entity.member.MemberEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FriendRepo extends JpaRepository<FriendEntity, UUID> {
    Page<FriendEntity> findByMemberEntity(MemberEntity memberEntity, Pageable pageable);
}
