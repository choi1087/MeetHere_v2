package com.choitaek.meethere.meethere.repository.jpa.schedule;

import com.choitaek.meethere.meethere.entity.schedule.ScheduleAddressEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleAddressRepo extends JpaRepository<ScheduleAddressEntity, UUID> {
    Page<ScheduleAddressEntity> findByScheduleUuid(UUID uuid);
}
