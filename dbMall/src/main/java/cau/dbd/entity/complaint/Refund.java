package cau.dbd.entity.complaint;

import cau.dbd.entity.BaseTimeEntity;
import cau.dbd.entity.Order;
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
    @JoinColumn(name = "orderId")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item;

    //private Delivery delivery;

    @Column(name = "refundReason")
    private RefundAndExchangeReason reason;


    private String refundReasonDetail;

    @Column(name = "refundStatus")
    private RefundAndExchangeStatus status;

    @Builder
    public Refund(Order order, Item item, RefundAndExchangeReason reason, String refundReasonDetail, RefundAndExchangeStatus status) {
        this.order = order;
        this.item = item;
        this.reason = reason;
        this.refundReasonDetail = refundReasonDetail;
        this.status = status;
    }
}
