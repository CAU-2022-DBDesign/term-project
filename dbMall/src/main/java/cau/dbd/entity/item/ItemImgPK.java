package cau.dbd.entity.item;

import cau.dbd.entity.Consumer;
import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
public class ItemImgPK implements Serializable {
    private Long item;
    private String fileName;
}
