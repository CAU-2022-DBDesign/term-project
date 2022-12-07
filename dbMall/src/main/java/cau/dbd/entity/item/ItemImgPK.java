package cau.dbd.entity.item;

import lombok.Data;

import java.io.Serializable;

@Data
public class ItemImgPK implements Serializable {
    private Long item;
    private String fileName;
}
