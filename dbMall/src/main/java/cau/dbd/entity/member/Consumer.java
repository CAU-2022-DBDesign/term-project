package cau.dbd.entity.member;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;


@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@DiscriminatorValue("Consumer")
public class Consumer extends Member {

    @Column
    private String test;

    @Builder
    public Consumer(String name, LocalDate birth, Gender gender, String test) {
        super(name, birth, gender);
        this.test = test;
    }
}