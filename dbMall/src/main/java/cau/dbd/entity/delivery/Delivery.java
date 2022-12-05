package cau.dbd.entity.delivery;

import cau.dbd.entity.BaseTimeEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Delivery extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(columnDefinition = "LONGTEXT")
    private String deliveryTime;

    @Column(columnDefinition = "LONGTEXT")
    private String deliveryState;

    @Column(columnDefinition = "LONGTEXT")
    private String deliveryRecord;

    @Builder
    public Delivery(String deliveryTime, String deliveryState, String deliveryRecord) {
        this.deliveryTime = deliveryTime;
        this.deliveryState = deliveryState;
        this.deliveryRecord = deliveryRecord;
    }
}
