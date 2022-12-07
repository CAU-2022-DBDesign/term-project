package cau.dbd.entity.contact;

import cau.dbd.entity.BaseTimeEntity;
import lombok.Builder;

import javax.persistence.*;

public class Answer extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;


    private String content;

    @Builder
    public Answer(String questionContent) {
        this.content = questionContent;
    }
}
