package com.choitaek.meethere.meethere.repository.jpa.schedule;

import com.choitaek.meethere.meethere.entity.schedule.ScheduleAddressEntity;
import com.choitaek.meethere.meethere.entity.schedule.ScheduleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ScheduleAddressRepo extends JpaRepository<ScheduleAddressEntity, UUID> {
    Page<ScheduleAddressEntity> findByScheduleEntity(ScheduleEntity schedule, Pageable pageable);
}
