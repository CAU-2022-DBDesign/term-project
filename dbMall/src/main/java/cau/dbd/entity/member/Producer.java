package cau.dbd.entity.member;

import lombok.Builder;

import java.time.LocalDate;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString(callSuper = true)
@DiscriminatorValue("Producer")
public class Producer extends Member {

    @Builder
    public Producer(String name, LocalDate birth, Member.Gender gender, String test) {
        super(name, birth, gender);
    }
}


