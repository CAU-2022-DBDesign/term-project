package cau.dbd.service;

import cau.dbd.entity.Consumer;
import cau.dbd.entity.OrderItem;
import cau.dbd.entity.complaint.Exchange;
import cau.dbd.entity.complaint.Refund;
import cau.dbd.entity.complaint.RefundAndExchangeReason;
import cau.dbd.entity.complaint.RefundAndExchangeStatus;
import cau.dbd.util.MyScanner;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;


/**
 *
 * mainMenu 호출해서 제어 넘겨주시면 서비스 이용 마치
 */
@AllArgsConstructor
public class ExchangeRefundService {

    private EntityManagerFactory emf;

    public void mainMenu(Consumer consumer) {
        while (true) {
            System.out.printf("\n******Exchange and Refund Menu - %s *******\n\n", consumer.getName());
            System.out.print("[SYSTEM] 1: Exchange / 2: Refund / 3: See my progress / 4: quit :");
            switch (MyScanner.getIntInRange(1, 4)) {
                case 1:
                    exchangeRequest(consumer);
                    break;
                case 2:
                    refundRequest(consumer);
                    break;
                case 3:
                    System.out.println("미구현");
                    break;
                case 4:
                    return;
            }
        }
    }

    private void exchangeRequest(Consumer consumer) {
        System.out.println("-- Exchange Service --");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        List<OrderItem> orderItemList = em.createQuery("select e from OrderItem e inner join e.order o " +
                        "where o.consumer = :consumer", OrderItem.class)
                .setParameter("consumer", consumer).getResultList();



        int idx = 1;
        for(OrderItem orderItem : orderItemList) {
            System.out.printf("[%d] orderId: %d, itemName: %s , quantity: %d , orderDate : %s \n",idx++,orderItem.getOrder().getId()
                    ,orderItem.getItem().getName(),orderItem.getQuantity(),orderItem.getOrder().getCreatedAt().toString());
        }

        System.out.println("[SYSTEM] Choose exchange item :");
        int exchangeItemIdx = MyScanner.getIntInRange(1, orderItemList.size()) - 1;


        Arrays.stream(RefundAndExchangeReason.values()).forEach(r -> {
            System.out.printf("[%d] %s\n",r.ordinal()+1,r.name());
        });
        System.out.println("[SYSTEM] Choose exchange reason :");
        int exchangeReasonIdx = MyScanner.getIntInRange(1, RefundAndExchangeReason.values().length) - 1;
        RefundAndExchangeReason r = RefundAndExchangeReason.values()[exchangeReasonIdx];


        System.out.println("[SYSTEM] Why do you want to exchange? :");
        String exchangeReasonDetail = MyScanner.getStringInLength(0,100);


        OrderItem targetOrderItem = orderItemList.get(exchangeItemIdx);


        try {
            tx.begin();

            em.persist(Exchange.builder().orderItem(targetOrderItem).reason(r)
                    .status(RefundAndExchangeStatus.REQUEST).exchangeReasonDetail(exchangeReasonDetail).build());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }



        System.out.println("[SYSTEM] Exchange Request Complete");
    }

    private void refundRequest(Consumer consumer) {
        System.out.println("-- Refund Service --");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        List<OrderItem> orderItemList = em.createQuery("select e from OrderItem e inner join e.order o " +
                        "where o.consumer = :consumer", OrderItem.class)
                .setParameter("consumer", consumer).getResultList();

        int idx = 1;
        for(OrderItem orderItem : orderItemList) {
            System.out.printf("[%d] orderId: %d, itemName: %s , quantity: %d , orderDate : %s \n",idx++,orderItem.getOrder().getId()
                    ,orderItem.getItem().getName(),orderItem.getQuantity(),orderItem.getOrder().getCreatedAt().toString());
        }

        System.out.println("[SYSTEM] Choose refund item :");
        int exchangeItemIdx = MyScanner.getIntInRange(1, orderItemList.size()) - 1;


        Arrays.stream(RefundAndExchangeReason.values()).forEach(r -> {
            System.out.printf("[%d] %s\n",r.ordinal()+1,r.name());
        });

        System.out.println("[SYSTEM] Choose refund reason :");
        int refundReasonIdx = MyScanner.getIntInRange(1, RefundAndExchangeReason.values().length) - 1;
        RefundAndExchangeReason r = RefundAndExchangeReason.values()[refundReasonIdx];


        System.out.println("[SYSTEM] Why do you want to refund? :");
        String exchangeReasonDetail = MyScanner.getStringInLength(0,100);


        OrderItem targetOrderItem = orderItemList.get(exchangeItemIdx);

        try {
            tx.begin();

            em.persist(Refund.builder().orderItem(targetOrderItem).reason(r)
                    .status(RefundAndExchangeStatus.REQUEST).refundReasonDetail(exchangeReasonDetail).build());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }



        System.out.println("[SYSTEM] Refund Request Complete");
    }
}
