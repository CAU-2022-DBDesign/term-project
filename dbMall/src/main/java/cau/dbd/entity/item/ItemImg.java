package cau.dbd.entity.item;


import cau.dbd.entity.Consumer;
import lombok.*;

import javax.persistence.*;

@Builder

@Entity
@Getter
@Setter
@NoArgsConstructor
@IdClass(ItemImgPK.class)
public class ItemImg {

    @Id
    @ManyToOne
    @JoinColumn(name="itemId")
    private Item item;

    @Id
    private String fileName;

    @Builder
    public ItemImg(Item item, String fileName) {
        this.item = item;
        this.fileName = fileName+".jpg";
    }
}
