package cau.dbd.entity.review;

import cau.dbd.entity.BaseTimeEntity;
import cau.dbd.entity.member.Consumer;
import cau.dbd.entity.item.Item;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Review extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "itemId")
    private Item item;

    @ManyToOne
    @JoinColumn(name = "consumerId")
    private Consumer consumer;

    @Column
    private String content;

    @Column
    private int reviewStar;

    @Builder
    public Review(Item item, Consumer consumer, String content, int reviewStar) {
        this.item = item;
        this.consumer = consumer;
        this.content = content;
        this.reviewStar = reviewStar;
    }
}
