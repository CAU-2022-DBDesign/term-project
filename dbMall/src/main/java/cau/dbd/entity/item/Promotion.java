package cau.dbd.entity.item;

import cau.dbd.entity.BaseTimeEntity;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "itemId")
    @NotNull
    private Item item;

    @Column
    @NotNull
    private int discount;

    @Column
    @NotNull
    private LocalDateTime startAt;
    private LocalDateTime endAt;

    @Builder
    public Promotion(Item item, int discount, LocalDateTime startAt, LocalDateTime endAt) {
        this.item = item;
        this.discount = discount;
        this.startAt = startAt;
        this.endAt = endAt;
    }
}
