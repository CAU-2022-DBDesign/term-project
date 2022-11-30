package cau.dbd.entity.item;

import cau.dbd.entity.BaseTimeEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Promotion extends BaseTimeEntity {

    @Id
    private Long id;

    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item;

    //할인 금액
    private int discount;
}
