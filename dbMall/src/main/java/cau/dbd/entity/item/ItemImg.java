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
public class ItemImg {
    @EmbeddedId
    private ItemImgPK itemImgPK;

}
