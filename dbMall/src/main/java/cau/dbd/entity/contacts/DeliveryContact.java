package cau.dbd.entity.contacts;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

public class DeliveryContact extends Contact{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
}
