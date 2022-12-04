package cau.dbd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 김명승
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
public class OrderStatus extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Order order;

    @Enumerated(EnumType.STRING)
    @Column
    private Status status;

    @Builder
    public OrderStatus(Order order, Status status) {
        this.order = order;
        this.status = status;
    }

    public enum Status {
        CANCELED, PURCHASED, SENT, RECEIVED, REFUNDED, EXCHANGED;
    }
}


