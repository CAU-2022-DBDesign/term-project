package cau.dbd.service;

import cau.dbd.entity.members.Consumer;
import cau.dbd.entity.members.Member.Gender;
import cau.dbd.util.MyScanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class MemberService {

    private EntityManagerFactory emf;

    public void insert() {
        // Get user type
        System.out.println("[SYSTEM] Who are you?");
        System.out.print("[SYSTEM] 1:Producer / 2:Manager / 3:Consumer: ");
        int memberType = MyScanner.getIntInRange(1, 3);

        // Get name
        System.out.print("[SYSTEM] Name: ");
        String name = MyScanner.getStringInLength(2, 10);

        // Get birth
        LocalDate birth;
        while(true) {
            try {
                System.out.print("[SYSTEM] Birth(YYYYMMDD) [ex.19990123]: ");
                String birthStr = MyScanner.getStringInLength(8);
                birth = LocalDate.parse(birthStr, DateTimeFormatter.ofPattern("yyyyMMdd"));
                break;
            } catch (DateTimeParseException e) {
                System.out.println("[ERROR] Invalid Date format");
            }
        }

        // Get gender
        System.out.println("[SYSTEM] Gender?");
        System.out.print("[SYSTEM] 1:Male / 2:Female / 3:etc: ");
        Gender gender = Gender.values()[MyScanner.getIntInRange(1, 3) - 1];

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            switch (memberType){
                case 1:

                    break;
                case 3:
                    Consumer consumer = Consumer.builder().name(name).birth(birth).gender(gender).build();
                    em.persist(consumer);
                    break;
            }

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }

}
