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
public class Refund extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;


    @ManyToOne
    @JoinColumn(name = "orderItem")
    private OrderItem orderItem;

    //private Delivery delivery;

    @Column(name = "refundReason")
    private RefundAndExchangeReason reason;


    private String refundReasonDetail;

    @Column(name = "refundStatus")
    private RefundAndExchangeStatus status;

    private int quantity;

    @Builder
    public Refund(OrderItem orderItem, RefundAndExchangeReason reason, String refundReasonDetail, RefundAndExchangeStatus status, int quantity) {
        this.orderItem = orderItem;
        this.reason = reason;
        this.refundReasonDetail = refundReasonDetail;
        this.status = status;
        this.quantity = quantity;
    }
}
