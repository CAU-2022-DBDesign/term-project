package cau.dbd.entity.item;

import cau.dbd.entity.Consumer;
import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class ItemImgPK implements Serializable {
    private Long itemId;
    private Long consumerId;
}
