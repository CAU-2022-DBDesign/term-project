package cau.dbd.entity.complaint;

import cau.dbd.entity.BaseTimeEntity;
import cau.dbd.entity.Order;
import cau.dbd.entity.OrderItem;
import cau.dbd.entity.item.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Exchange extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "orderItem")
    private OrderItem orderItem;

    //private Delivery delivery;

    @Column(name = "exchangeReason")
    private RefundAndExchangeReason reason;


    private String exchangeReasonDetail;

    @Column(name = "exchangeStatus")
    private RefundAndExchangeStatus status;

    private int quantity;

    @Builder
    public Exchange(OrderItem orderItem, RefundAndExchangeReason reason, String exchangeReasonDetail, RefundAndExchangeStatus status) {
        this.orderItem = orderItem;
        this.reason = reason;
        this.exchangeReasonDetail = exchangeReasonDetail;
        this.status = status;
    }
}
