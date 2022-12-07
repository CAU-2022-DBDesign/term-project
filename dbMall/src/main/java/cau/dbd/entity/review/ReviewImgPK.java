package cau.dbd.entity.review;

import java.io.Serializable;
import javax.persistence.Embeddable;
import lombok.Data;

@Data
@Embeddable
public class ReviewImgPK implements Serializable {
    private Long review;
    private String fileName;
}
