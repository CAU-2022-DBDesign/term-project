package cau.dbd.entity.item;


import cau.dbd.entity.Consumer;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

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

}
