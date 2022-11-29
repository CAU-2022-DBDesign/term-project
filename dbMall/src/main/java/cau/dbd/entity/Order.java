package cau.dbd.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * 김명승
 * Order은 MYSQL 예약어라 사용 불가능
 * 테이블 명만 Oor
 */
@Entity
@Table(name="Oorder")
@Getter
@Setter
@NoArgsConstructor
public class Order extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="consumerId")
    private Consumer consumer;

    @Builder
    public Order(Consumer consumer) {
        this.consumer = consumer;
    }
}


