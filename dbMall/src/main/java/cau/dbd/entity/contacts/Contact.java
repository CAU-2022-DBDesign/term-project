package cau.dbd.entity.contacts;

import javax.persistence.*;

public class Contact {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn
    private Question question;

    @OneToOne
    @JoinColumn
    private Answer answer;

}
