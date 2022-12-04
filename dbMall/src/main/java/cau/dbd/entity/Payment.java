package cau.dbd.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
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
public class Payment extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn
    private OrderStatus orderStatus;

    @Column
    private int price;

    @Enumerated(EnumType.STRING)
    @Column
    private Method method;

    @Builder
    public Payment(OrderStatus orderStatus, int price, Method method) {
        this.orderStatus = orderStatus;
        this.price = price;
        this.method = method;
    }

    public enum Method {
        NON_BANKBOOK, CREDIT_CARD, SIMPLE_PAY
    }
}


