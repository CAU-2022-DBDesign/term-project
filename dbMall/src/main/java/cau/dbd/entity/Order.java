package cau.dbd.entity;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import cau.dbd.entity.members.Consumer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 김명승
 * Order은 MYSQL 예약어라 사용 불가능 테이블 명만 Oorder로 수정
 */
@Entity
@Table(name = "Oorder")
@Getter
@Setter
@NoArgsConstructor
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consumerId")
    private Consumer consumer;

//    배송 테이블 추가 시 추가 요망
//    @ManyToOne
//    @JoinColumn
//    private Delivery delivery;

    @OneToMany(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "orderId", updatable = false, insertable = false)
    private List<OrderItem> orderItems = new ArrayList<>();

    @Builder
    public Order(Consumer consumer) {
        this.consumer = consumer;
    }
}


