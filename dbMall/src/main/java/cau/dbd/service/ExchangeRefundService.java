package cau.dbd.service;

import cau.dbd.entity.Consumer;
import cau.dbd.entity.OrderItem;
import cau.dbd.util.MyScanner;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@AllArgsConstructor
public class ExchangeRefundService {

    private EntityManagerFactory emf;

    public void mainMenu(Consumer consumer) {
        while (true) {
            System.out.printf("\n******Exchange and Refund Menu - %s *******\n\n", consumer.getName());
            System.out.print("[SYSTEM] 1: Exchange / 2: Refund / 3:Sign out : See my progress :");
            switch (MyScanner.getIntInRange(1, 3)) {
                case 1:
                    exchange(consumer);
                    break;
                case 2:
                    refund(consumer);
                    break;
                case 3:
                    return;
            }
        }
    }

    private void exchange(Consumer consumer) {
        System.out.println("-- Exchange Service --");
        EntityManager em = emf.createEntityManager();

        List<OrderItem> orderItemList = em.createQuery("select e from Order o " +
                        "JOIN OrderItem e ON e.order = o " +
                        "WHERE o.consumer = :consumer", OrderItem.class)
                .setParameter("consumer", consumer)
                .getResultList();

        int idx = 1;
        for(OrderItem orderItem : orderItemList) {
            System.out.printf("[%d] orderId: %d, itemName: %s , quantity: %d , orderDate : %s \n",idx++,orderItem.getOrder().getId()
                    ,orderItem.getItem().getName(),orderItem.getQuantity(),orderItem.getOrder().getCreatedAt().toString());
        }

        System.out.println("[SYSTEM] Choose exchange item :");
        int exchangeItemNum = MyScanner.getIntInRange(1, orderItemList.size());




        em.close();
    }

    private void refund(Consumer consumer) {

    }
}
