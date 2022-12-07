package cau.dbd.entity;

import java.time.LocalDate;
import javax.persistence.*;

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
@DiscriminatorValue("Manager")
public class Manager extends Member{

    @Enumerated(EnumType.STRING)
    @Column
    private Level level;

    @Builder
    public Manager(String name, LocalDate birth, Gender gender, Level level) {
        super(name, birth, gender);
        this.level = level;
    }

    public enum Level {
        JUNIOR, SENIOR, MASTER;
    }
}


