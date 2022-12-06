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
import java.util.concurrent.atomic.AtomicInteger;


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
        AtomicInteger indexHolder = new AtomicInteger();
        indexHolder.set(1);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();


        List<OrderItem> exchangableOrderItemList = em.createQuery("select e from OrderItem e join e.order o left join Refund r on r.orderItem = e" +
                        " where o.consumer = :consumer and ((e.quantity -  r.quantity  > 0) or (r IS NULL))", OrderItem.class)
                .setParameter("consumer", consumer).getResultList();



        //Choosing target Item
        exchangableOrderItemList.stream().forEach(orderItem ->  {
            System.out.printf("[%d] orderId: %d, itemName: %18s , quantity: %d , orderDate : %s \n",indexHolder.getAndIncrement(),orderItem.getOrder().getId()
                    ,orderItem.getItem().getName(),orderItem.getQuantity(),orderItem.getOrder().getCreatedAt().toString());
        });



        System.out.println("[SYSTEM] Choose exchange item (0:quit):");
        int exchangeItemIdx = MyScanner.getIntInRange(0, exchangableOrderItemList.size()) - 1;

        //zero to quit
        if(exchangeItemIdx == -1)
            return;

        // exchange request quantity
        System.out.println("[SYSTEM] Enter the quantity to exchange (0:quit):");
        int exchangeRequestQuantity = MyScanner.getIntInRange(0, exchangableOrderItemList.size());

        //zero to quit
        if(exchangeRequestQuantity == 0)
            return;


        //Choosing reason
        Arrays.stream(RefundAndExchangeReason.values()).forEach(r -> {
            System.out.printf("[%d] %s\n",r.ordinal()+1,r.name());
        });
        System.out.println("[SYSTEM] Choose exchange reason (0:quit):");

        int exchangeReasonIdx = MyScanner.getIntInRange(0, RefundAndExchangeReason.values().length) - 1;

        //zero to quit
        if(exchangeReasonIdx == -1)
            return;

        RefundAndExchangeReason r = RefundAndExchangeReason.values()[exchangeReasonIdx];

        //get ReasonDetail
        System.out.println("[SYSTEM] Why do you want to exchange? (0:quit):");
        String exchangeReasonDetail = MyScanner.getStringInLength(0,100);
        if(exchangeReasonDetail.equals("0"))
            return;

        OrderItem targetOrderItem = exchangableOrderItemList.get(exchangeItemIdx);


        try {
            tx.begin();

            em.persist(Exchange.builder().orderItem(targetOrderItem).reason(r).quantity(exchangeRequestQuantity)
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
        AtomicInteger indexHolder = new AtomicInteger();
        indexHolder.set(1);
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();


        List<OrderItem> refundableOrderItemList = em.createQuery("select e from OrderItem e join e.order o left join Refund r on r.orderItem = e" +
                        " where o.consumer = :consumer and ((e.quantity -  r.quantity  > 0) or (r IS NULL))", OrderItem.class)
                .setParameter("consumer", consumer).getResultList();



        //Choosing target Item
        refundableOrderItemList.stream().forEach(orderItem ->  {
            System.out.printf("[%d] orderId: %d, itemName: %18s , quantity: %d , orderDate : %s \n",indexHolder.getAndIncrement(),orderItem.getOrder().getId()
                    ,orderItem.getItem().getName(),orderItem.getQuantity(),orderItem.getOrder().getCreatedAt().toString());
        });



        System.out.println("[SYSTEM] Choose refund item (0:quit):");
        int refundItemIdx = MyScanner.getIntInRange(0, refundableOrderItemList.size()) - 1;

        //zero to quit
        if(refundItemIdx == -1)
            return;

        // refund request quantity
        System.out.println("[SYSTEM] Enter the quantity to refund (0:quit):");
        int refundRequestQuantity = MyScanner.getIntInRange(0, refundableOrderItemList.size());

        //zero to quit
        if(refundRequestQuantity == 0)
            return;


        //Choosing reason
        Arrays.stream(RefundAndExchangeReason.values()).forEach(r -> {
            System.out.printf("[%d] %s\n",r.ordinal()+1,r.name());
        });
        System.out.println("[SYSTEM] Choose refund reason (0:quit):");

        int refundReasonIdx = MyScanner.getIntInRange(0, RefundAndExchangeReason.values().length) - 1;

        //zero to quit
        if(refundReasonIdx == -1)
            return;

        RefundAndExchangeReason r = RefundAndExchangeReason.values()[refundReasonIdx];

        //get ReasonDetail
        System.out.println("[SYSTEM] Why do you want to refund? (0:quit):");
        String refundReasonDetail = MyScanner.getStringInLength(0,100);
        if(refundReasonDetail.equals("0"))
            return;

        OrderItem targetOrderItem = refundableOrderItemList.get(refundItemIdx);


        try {
            tx.begin();

            em.persist(Refund.builder().orderItem(targetOrderItem).reason(r).quantity(refundRequestQuantity)
                    .status(RefundAndExchangeStatus.REQUEST).refundReasonDetail(refundReasonDetail).build());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
        } finally {
            em.close();
        }



        System.out.println("[SYSTEM] Refund Request Complete");
    }

    void showProgress(Consumer consumer) {
        EntityManager em = emf.createEntityManager();
        AtomicInteger indexHolder = new AtomicInteger();
        indexHolder.set(1);

        System.out.println("-- Exchange Progress --");
        TypedQuery<RefundAndExchangeDTO> exchangeList = em.createQuery("select new cau.dbd.entity.complaint.RefundAndExchangeDTO(o.id,oi.item.name,o.createdAt,e.createdAt,e.reason,e.exchangeReasonDetail,e.quantity,e.status) " +
                        "from Exchange e " +
                        "join e.orderItem oi " +
                        "join oi.order o " +
                        "join o.consumer c " +
                        "where c.id = :id",RefundAndExchangeDTO.class)
                .setParameter("id",consumer.getId());

        List<RefundAndExchangeDTO> exchangeDTOList = exchangeList.getResultList();


        exchangeDTOList.stream().forEach(m -> {
            System.out.printf("[%d]\n",indexHolder.getAndIncrement());
            System.out.printf("       OrderId: %-25d  itemName: %s\n",m.getOrderId(),m.getItemName());
            System.out.printf("     OrderDate: %-25s  ExchangeDate: %s\n",m.getOrderCreateAt(),m.getRneCreateAt());
            System.out.printf("ExchangeReason: %-25s  Detail: %s\n",m.getReason(),m.getReasonDetail());
            System.out.printf("      Quantity: %-25d  Status: %s\n\n",m.getQuantity(),m.getStatus());
        });
        indexHolder.set(1);
        //데이터가 없는 경우
        if(exchangeDTOList.isEmpty()) {
            System.out.println("No Corresponding Data\n");
        }

        System.out.println("-- Refund Progress --");
        TypedQuery<RefundAndExchangeDTO> refundList = em.createQuery("select new cau.dbd.entity.complaint.RefundAndExchangeDTO(o.id,oi.item.name,o.createdAt,e.createdAt,e.reason,e.refundReasonDetail,e.quantity,e.status) " +
                        "from Refund e " +
                        "join e.orderItem oi " +
                        "join oi.order o " +
                        "join o.consumer c " +
                        "where c.id = :id",RefundAndExchangeDTO.class)
                .setParameter("id",consumer.getId());

        List<RefundAndExchangeDTO> refundDTOList = refundList.getResultList();

        if(refundDTOList.isEmpty()) {
            System.out.println("No Corresponding Data\n");
        }

        refundDTOList.stream().forEach(m -> {
            System.out.printf("[%d]\n",indexHolder.getAndIncrement());
            System.out.printf("     OrderId: %-25d  itemName: %s\n",m.getOrderId(),m.getItemName());
            System.out.printf("   OrderDate: %-25s  RefundDate: %s\n",m.getOrderCreateAt(),m.getRneCreateAt());
            System.out.printf("RefundReason: %-25s  Detail: %s\n",m.getReason(),m.getReasonDetail());
            System.out.printf("    Quantity: %-25d  Status: %s\n\n",m.getQuantity(),m.getStatus());
        });


        //보여져야할 것 : 주문ID 상품명 주문일시 교환요청일시 교환이유 교환이유상세 교환개수 교환진행상황
    }
}
