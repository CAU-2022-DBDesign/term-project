package cau.dbd.entity.review;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
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
@IdClass(ReviewImgPK.class)
public class ReviewImg {

    @Id
    @ManyToOne
    @JoinColumn(name = "reviewId")
    private Review review;

    @Id
    private String fileName;

    @Builder
    public ReviewImg(Review review, String fileName) {
        this.review = review;
        this.fileName = fileName + ".jpg";
    }
}
