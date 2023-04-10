package com.choitaek.meethere.meethere.repository.jpa.friend;

import com.choitaek.meethere.meethere.entity.friend.FriendEntity;
import com.choitaek.meethere.meethere.entity.member.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface FriendRepo extends JpaRepository<FriendEntity, UUID> {
    List<FriendEntity> findByMemberEntity(MemberEntity memberEntity);
}
