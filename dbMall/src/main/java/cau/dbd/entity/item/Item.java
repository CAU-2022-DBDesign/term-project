package cau.dbd.entity.item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import cau.dbd.entity.BaseTimeEntity;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Entity
@Getter
@Setter
@NoArgsConstructor
public class Item extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="categoryId")
    @NotNull
    private Category category;

    @NotNull
    @Column
    private String name;

    @Column
    private int price = 0;

    @Column
    private int stock = 0;

    @Column(columnDefinition = "LONGTEXT")
    private String description;

    @Builder
    public Item(String name, Category category, int price, int stock, String description) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.description = description;
    }
}


