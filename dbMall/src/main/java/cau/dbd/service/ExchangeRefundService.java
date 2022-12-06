package cau.dbd.service;

import cau.dbd.entity.Consumer;
import cau.dbd.entity.OrderItem;
import cau.dbd.entity.complaint.*;
import cau.dbd.util.MyScanner;
import lombok.AllArgsConstructor;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.sql.SQLOutput;
import java.util.Arrays;
import java.util.List;


/**
 *
 * mainMenu 호출해서 제어 넘겨주시면 서비스 이용 마치
 */
public class ExchangeRefundService {

    public ExchangeRefundService(EntityManagerFactory emf) {
        this.emf = emf;
    }

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
                    showProgress(consumer);
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


        List<OrderItem> exchangableOrderItemList = em.createQuery("select e from OrderItem e join e.order o left join Refund r on r.orderItem = e" +
                        " where o.consumer = :consumer and ((e.quantity -  r.quantity  > 0) or (r IS NULL))", OrderItem.class)
                .setParameter("consumer", consumer).getResultList();

        int idx = 1;
        for(OrderItem orderItem : exchangableOrderItemList) {
            System.out.printf("[%d] orderId: %d, itemName: %18s , quantity: %d , orderDate : %s \n",idx++,orderItem.getOrder().getId()
                    ,orderItem.getItem().getName(),orderItem.getQuantity(),orderItem.getOrder().getCreatedAt().toString());
        }

        System.out.println("[SYSTEM] Choose exchange item (0:quit):");
        int exchangeItemIdx = MyScanner.getIntInRange(1, exchangableOrderItemList.size()) - 1;

        //zero to quit
        if(exchangeItemIdx == -1)
            return;

        Arrays.stream(RefundAndExchangeReason.values()).forEach(r -> {
            System.out.printf("[%d] %s\n",r.ordinal()+1,r.name());
        });
        System.out.println("[SYSTEM] Choose exchange reason (0:quit):");

        int exchangeReasonIdx = MyScanner.getIntInRange(1, RefundAndExchangeReason.values().length) - 1;

        //zero to quit
        if(exchangeReasonIdx == -1)
            return;

        RefundAndExchangeReason r = RefundAndExchangeReason.values()[exchangeReasonIdx];


        System.out.println("[SYSTEM] Why do you want to exchange? (0:quit):");
        String exchangeReasonDetail = MyScanner.getStringInLength(0,100);
        if(exchangeReasonDetail.equals("0"))
            return;

        OrderItem targetOrderItem = exchangableOrderItemList.get(exchangeItemIdx);


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

    void showProgress(Consumer consumer) {

        System.out.println("-- Exchange Progress --");
        EntityManager em = emf.createEntityManager();

        TypedQuery<RefundAndExchangeDTO> exchangeList = em.createQuery("select new cau.dbd.entity.complaint.RefundAndExchangeDTO(o.id,oi.item.name,o.createdAt,e.createdAt,e.reason,e.exchangeReasonDetail,e.quantity,e.status) " +
                        "from Exchange e " +
                        "join e.orderItem oi " +
                        "join oi.order o " +
                        "join o.consumer c " +
                        "where c.id = :id",RefundAndExchangeDTO.class)
                .setParameter("id",consumer.getId());

        List<RefundAndExchangeDTO> refundAndExchangeDTOList = exchangeList.getResultList();


        refundAndExchangeDTOList.stream().forEach(m -> {
            System.out.printf("       OrderId: %-25d  itemName: %s\n",m.getOrderId(),m.getItemName());
            System.out.printf("     OrderDate: %-25s  ExchangeDate: %s\n",m.getOrderCreateAt(),m.getRneCreateAt());
            System.out.printf("ExchangeReason: %-25s  Detail: %s\n",m.getReason(),m.getReasonDetail());
            System.out.printf("      Quantity: %-25d  Status: %s\n\n",m.getQuantity(),m.getStatus());
        });


        //보여져야할 것 : 주문ID 상품명 주문일시 교환요청일시 교환이유 교환이유상세 교환개수 교환진행상황
    }
}
