package com.choitaek.meethere.meethere.entity.member;

import com.choitaek.meethere.meethere.dto.AddressObjectDto;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Getter
@Table(name = "member_address")
@NoArgsConstructor
@AllArgsConstructor
public class MemberAddressEntity {

    @Id
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "uuid2")
    @Column(length = 36, nullable = false, updatable = false, columnDefinition = "uuid", name = "uuid")
    private UUID uuid;

    @Column(name = "address_name", columnDefinition = "varchar(50)")
    private String addressName;

    @Column(name = "place_name", columnDefinition = "varchar(50)")
    private String placeName;

    @Column(name = "road_name", columnDefinition = "varchar(50)")
    private String roadName;

    @Column(name = "lat", columnDefinition = "double")
    private double lat;

    @Column(name = "lon", columnDefinition = "double")
    private double lon;

    @OneToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "member_uuid", nullable = false)
    private MemberEntity memberEntity;

    public void createMemberAddress(AddressObjectDto addressObjectDto, MemberEntity memberEntity) {
        this.addressName = addressObjectDto.getAddressName();
        this.placeName = addressObjectDto.getPlaceName();
        this.roadName = addressObjectDto.getRoadName();
        this.lat = addressObjectDto.getLat();
        this.lon = addressObjectDto.getLon();
        this.memberEntity = memberEntity;
    }
}
