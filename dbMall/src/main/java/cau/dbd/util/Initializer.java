package cau.dbd.util;

import cau.dbd.entity.Consumer;
import cau.dbd.entity.Member.Gender;
import cau.dbd.entity.Order;
import cau.dbd.entity.OrderItem;
import cau.dbd.entity.OrderStatus;
import cau.dbd.entity.OrderStatus.Status;
import cau.dbd.entity.item.Category;
import cau.dbd.entity.item.Item;

import java.time.LocalDate;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class Initializer {

    /**
     * db 초기화 함수
     * 추가할 데이터는 여기에 작성 요망
     */
    public static void initialize(EntityManagerFactory emf) {
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();

            /*--------------- Consumer ------------*/
            Object[][] consumerData = {
                {"Liam", LocalDate.of(1980, 3, 21), Gender.MALE},
                {"Noah", LocalDate.of(2001, 12, 5), Gender.FEMALE},
                {"Oliver", LocalDate.of(1999, 5, 10), Gender.FEMALE},
                {"William", LocalDate.of(1983, 7, 24), Gender.FEMALE},
                {"Elijah", LocalDate.of(1976, 10, 16), Gender.MALE},
                {"James", LocalDate.of(2002, 1, 22), Gender.MALE},
                {"Benjamin", LocalDate.of(1997, 4, 2), Gender.FEMALE},
                {"Lucas", LocalDate.of(1989, 12, 28), Gender.MALE},
                {"Mason", LocalDate.of(1991, 2, 4), Gender.FEMALE},
                {"Ethan", LocalDate.of(1993, 5, 17), Gender.MALE},
                {"Aaron", LocalDate.of(1967, 8, 30), Gender.MALE},
                {"Adam", LocalDate.of(2004, 9, 11), Gender.MALE},
                {"Colton", LocalDate.of(1999, 11, 25), Gender.ETC},
                {"Jackson", LocalDate.of(1986, 4, 2), Gender.MALE},
                {"Hunter", LocalDate.of(1997, 3, 29), Gender.FEMALE},
                {"Nathan", LocalDate.of(1994, 12, 31), Gender.MALE},
                {"Santana", LocalDate.of(1993, 1, 4), Gender.MALE},
            };
            for (Object[] data : consumerData) {
                em.persist(Consumer.builder().name((String) data[0]).birth((LocalDate) data[1])
                    .gender((Gender) data[2]).build());
            }


            /*--------------- Category ------------*/
            Object[][] categoryData = {
                {"Food"},
                {"Electronics"},
                {"Drugstore"},
                {"Clothing"},
                {"Sports"},
            };
            for (Object[] data : categoryData) {
                em.persist(Category.builder().name((String) data[0]).build());
            }


            /*--------------- Item ------------*/
            Category electronics = em.createQuery("select c from Category c where c.name = 'Electronics'",
                Category.class).getSingleResult();
            Object[][] electronicItemData = {
                {"Galaxy S22 Ultra", 1130000, 142,
                    "Samsung Galaxy S22 Ultra Black 512GB with free transparent silicon case"},
                {"iPhone 14 Pro Max", 1420000, 159, "Apple iPhone 14 Pro Max White 256GB"},
                {"MacBook Pro (14-inch, M1)", 2512000, 21,
                    "Apple MacBook Pro 14-inch, M1 Silicon chip, 1TB SSD, 256GB RAM, with Apple Care +"},
                {"Sony WH-1000XM4", 230000, 0, "Sony WH-1000XM4 Wireless Noise canceling Headset"},
            };
            for (Object[] data : electronicItemData) {
                em.persist(Item.builder().name((String) data[0]).category(electronics).price((int) data[1])
                    .stock((int) data[2]).description((String) data[3]).build());
            }


            /*--------------- Order1 ------------*/
            Consumer liam = em.createQuery("select c from Consumer c where c.name = 'Liam'",
                Consumer.class).getSingleResult();
            Item galaxy = em.createQuery("select i from Item i where i.name = 'Galaxy S22 Ultra'",
                Item.class).getSingleResult();
            Item iPhone = em.createQuery("select i from Item i where i.name = 'iPhone 14 Pro Max'",
                Item.class).getSingleResult();
            Order order = Order.builder().consumer(liam).build();
            em.persist(order);
            em.persist(OrderItem.builder().item(galaxy).order(order).price(galaxy.getPrice()).quantity(3).build());
            em.persist(OrderItem.builder().item(iPhone).order(order).price(iPhone.getPrice()).quantity(1).build());
            em.persist(OrderStatus.builder().status(Status.PURCHASED).order(order).build());
            em.persist(OrderStatus.builder().status(Status.SENT).order(order).build());
            em.persist(OrderStatus.builder().status(Status.RECEIVED).order(order).build());

            /*--------------- Order1 ------------*/
            Item xm4 = em.createQuery("select i from Item i where i.name = 'Sony WH-1000XM4'",
                Item.class).getSingleResult();
            Order order2 = Order.builder().consumer(liam).build();
            em.persist(order2);
            em.persist(OrderItem.builder().item(xm4).order(order2).price(xm4.getPrice()).quantity(1).build());
            em.persist(OrderStatus.builder().status(Status.PURCHASED).order(order2).build());
            em.persist(OrderStatus.builder().status(Status.SENT).order(order2).build());
            em.persist(OrderStatus.builder().status(Status.RECEIVED).order(order2).build());
            em.persist(OrderStatus.builder().status(Status.REFUNDED).order(order2).build());

            /*--------------- Order2 ------------*/
            Order order3 = Order.builder().consumer(liam).build();
            em.persist(order3);
            em.persist(OrderItem.builder().item(iPhone).order(order3).price(iPhone.getPrice()).quantity(2).build());
            em.persist(OrderItem.builder().item(xm4).order(order3).price(xm4.getPrice()).quantity(1).build());
            em.persist(OrderItem.builder().item(galaxy).order(order3).price(galaxy.getPrice()).quantity(3).build());
            em.persist(OrderStatus.builder().status(Status.PURCHASED).order(order3).build());

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

    }
}
