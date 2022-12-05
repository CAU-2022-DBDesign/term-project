package cau.dbd.service;

import cau.dbd.entity.Consumer;
import cau.dbd.entity.OrderItem;
import cau.dbd.entity.complaint.Exchange;
import cau.dbd.entity.complaint.RefundAndExchangeReason;
import cau.dbd.entity.complaint.RefundAndExchangeStatus;
import cau.dbd.util.MyScanner;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;

@AllArgsConstructor
public class ExchangeRefundService {

    private EntityManagerFactory emf;

    public void mainMenu(Consumer consumer) {
        while (true) {
            System.out.printf("\n******Exchange and Refund Menu - %s *******\n\n", consumer.getName());
            System.out.print("[SYSTEM] 1: Exchange / 2: Refund / 3: See my progress / 4: quit :");
            switch (MyScanner.getIntInRange(1, 4)) {
                case 1:
                    exchange(consumer);
                    break;
                case 2:
                    refund(consumer);
                    break;
                case 3:
                    break;
                case 4:
                    return;
            }
        }
    }

    private void exchange(Consumer consumer) {
        System.out.println("-- Exchange Service --");
        EntityManager em = emf.createEntityManager();

        em.createQuery("select e from OrderItem e").getResultList();

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
        String exchangeReasonDetail = MyScanner.getStringInLength(100);


        OrderItem targetOrderItem = orderItemList.get(exchangeItemIdx);
        em.persist(Exchange.builder().item(targetOrderItem.getItem()).order(targetOrderItem.getOrder()).reason(r)
                .status(RefundAndExchangeStatus.REQUEST).exchangeReasonDetail(exchangeReasonDetail).build());


        System.out.println("[SYSTEM] Exchange Request Complete");
        em.close();
    }

    private void refund(Consumer consumer) {

    }
}
