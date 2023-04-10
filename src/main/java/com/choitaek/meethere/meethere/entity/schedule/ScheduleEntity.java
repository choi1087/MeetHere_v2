package com.choitaek.meethere.meethere.entity.schedule;

import com.choitaek.meethere.meethere.dto.request.schedule.ScheduleSaveReqDto;
import com.choitaek.meethere.meethere.dto.request.schedule.ScheduleUpdateReqDto;
import com.choitaek.meethere.meethere.entity.member.MemberEntity;
import com.choitaek.meethere.meethere.entity.TimeEntity;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Table(name = "schedule")
@NoArgsConstructor
@AllArgsConstructor
public class ScheduleEntity extends TimeEntity {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false, columnDefinition = "uuid", name = "uuid")
    private UUID uuid;

    @Column(name = "name", columnDefinition = "varchar(20)")
    private String name;

    @Column(name = "address_name", columnDefinition = "varchar(50)")
    private String addressName;

    @Column(name = "place_name", columnDefinition = "varchar(50)")
    private String placeName;

    @Column(name = "road_name", columnDefinition = "varchar(50)")
    private String roadName;

    @Column(name = "date", columnDefinition = "varchar(10)")
    private String date;

    @Column(name = "lat", columnDefinition = "double")
    private double lat;

    @Column(name = "lon", columnDefinition = "double")
    private double lon;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_uuid", nullable = false)
    MemberEntity memberEntity;

    public void createSchedule(ScheduleSaveReqDto scheduleSaveReqDto, MemberEntity memberEntity) {
        this.name = scheduleSaveReqDto.getName();
        this.addressName = scheduleSaveReqDto.getDestinationAddress().getAddressName();
        this.placeName = scheduleSaveReqDto.getDestinationAddress().getPlaceName();
        this.roadName = scheduleSaveReqDto.getDestinationAddress().getRoadName();
        this.date = scheduleSaveReqDto.getDate();
        this.lat = scheduleSaveReqDto.getDestinationAddress().getLat();
        this.lon = scheduleSaveReqDto.getDestinationAddress().getLon();
        this.memberEntity = memberEntity;
    }

    public void updateSchedule(ScheduleUpdateReqDto scheduleUpdateReqDto) {
        this.name = scheduleUpdateReqDto.getName();
        this.date = scheduleUpdateReqDto.getDate();
    }
}
