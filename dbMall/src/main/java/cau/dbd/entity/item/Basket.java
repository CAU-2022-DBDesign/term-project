package cau.dbd.entity.item;

import cau.dbd.entity.Consumer;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Basket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consumerId")
    private Consumer consumer;

    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item;

    private int quantity;

    @Builder
    public Basket(Consumer consumer, Item item, int quantity) {
        this.consumer = consumer;
        this.item = item;
        this.quantity = quantity;
    }
}
