package cau.dbd.entity.contact;

import cau.dbd.entity.BaseTimeEntity;
import cau.dbd.entity.Consumer;
import lombok.Builder;

import javax.persistence.*;

public class Question extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "consumerId")
    private Consumer consumer;

    //have to add managerId

    private String content;


    @Builder
    public Question(String questionContent, Consumer consumer) {
        this.content = questionContent;
        this.consumer = consumer;
    }
}
