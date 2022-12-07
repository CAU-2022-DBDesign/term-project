package cau.dbd.service;

import cau.dbd.entity.members.Consumer;
import cau.dbd.entity.Order;
import cau.dbd.entity.OrderItem;
import cau.dbd.entity.OrderStatus;
import cau.dbd.entity.OrderStatus.Status;
import cau.dbd.entity.Payment;
import cau.dbd.entity.Payment.Method;
import cau.dbd.entity.item.Basket;
import cau.dbd.entity.item.Item;
import cau.dbd.entity.item.ItemImg;
import cau.dbd.util.MyScanner;
import java.util.Arrays;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConsumerService {

    private EntityManagerFactory emf;

    /**
     * 소비자 메인 메뉴 상품 조회, 장바구니 추가, 주문, 주문내역, 결제내역, 컴플레인 등의 기능이 추가되어야 함
     *
     * @param consumer
     */
    public void mainMenu(Consumer consumer) {
        while (true) {
            System.out.printf("\n******Consumer Menu - %s *******\n\n", consumer.getName());
            System.out.print("[SYSTEM] 1: See all orders / 2: Make order / 3: Exchange&Refund Service / 4:Sign out : ");
            switch (MyScanner.getIntInRange(1, 3)) {
                case 1:
                    selectAllOrder(consumer);
                    break;
                case 2:
                    makeOrder(consumer);
                    break;
                case 3:
                    ExchangeRefundService exchangeRefundService = new ExchangeRefundService(emf);
                    exchangeRefundService.mainMenu(consumer);
                    break;
                case 4:
                    return;
            }
        }
    }

    /**
     * 주문 내역 조회
     *
     * @param consumer
     */
    public void selectAllOrder(Consumer consumer) {
        System.out.println("-- My Orders --");

        EntityManager em = emf.createEntityManager();

        List<Order> orders = em.createQuery("select e from Order e where e.consumer.id = :id ", Order.class)
            .setParameter("id", consumer.getId()).getResultList();
        orders.forEach(order -> {
            System.out.printf("\n[%d] %s%n", order.getId(), order.getCreatedAt());
            order.getOrderItems().forEach(orderItem -> {
                System.out.printf("\t %s (%s)%n", orderItem.getItem().getName(), orderItem.getQuantity());
            });
        });

        System.out.print("\n[SYSTEM] Pick order to see detail (0:Quit) : ");
        long orderId = MyScanner.getIntInRange(0, 1000);
        List<OrderStatus> orderStatuses = em.createQuery("select e from OrderStatus e where e.order.id = :id ",
                OrderStatus.class)
            .setParameter("id", orderId).getResultList();

        orderStatuses.forEach(orderStatus -> {
            System.out.printf("\t%s (%s) %n", orderStatus.getStatus().name(), orderStatus.getCreatedAt());
        });

        em.close();
    }


    /**
     * 주문
     *
     * @param consumer
     */
    public void makeOrder(Consumer consumer) {
        System.out.println("-- Order items --");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        try {
            tx.begin();
            Order order = Order.builder().consumer(consumer).build();
            em.persist(order);

            List<Item> items = em.createQuery("select e from Item e", Item.class).getResultList();
            items.forEach(item -> {
                System.out.printf("[%d] %s %d %n", item.getId(), item.getName(), item.getPrice());
            });

            int totalPrice = 0;

            while (true) {
                System.out.print("\n[SYSTEM] Pick item to order(0:Finish) : ");
                int itemId = MyScanner.getIntInRange(0, 1000);
                if (itemId == 0) {
                    break;
                }
                System.out.print("[SYSTEM] How many items you want to order? :");
                int quantity = MyScanner.getIntInRange(1, 100);
                Item item = items.stream().filter(it -> itemId == it.getId()).findFirst().get();
                totalPrice += item.getPrice() * quantity;

                em.persist(
                    OrderItem.builder().order(order).quantity(quantity).price(item.getPrice()).item(item).build());
            }

            System.out.print("[SYSTEM] How to pay?");
            Arrays.stream(Method.values()).forEach(method -> {
                System.out.printf(" %d: %s /", method.ordinal() + 1, method);
            });
            OrderStatus orderStatus = OrderStatus.builder().status(Status.PURCHASED).order(order).build();
            em.persist(orderStatus);
            Method paymentMethod = Method.values()[MyScanner.getIntInRange(1, Method.values().length) - 1];
            em.persist(Payment.builder().orderStatus(orderStatus).method(paymentMethod).price(totalPrice).build());

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }
    }

    /**
       * 장바구니 추가
     * @param consumer
     */
    private void insertBasket(Consumer consumer) {

        System.out.println("--- add Basket ---");
        EntityManager em = emf.createEntityManager();

        Integer itemSize = em.createQuery("select count(e) from Item e", int.class).getSingleResult();
        System.out.println("[SYSTEM] Select Item :");
        int itemId = MyScanner.getIntInRange(1, itemSize);
        System.out.println("[SYSTEM] How many items do you want to add? :");
        int quantity = MyScanner.getIntInRange(1, 100);
        Item item = em.createQuery("select e from Item e where e.id = :itemId", Item.class)
                .setParameter("itemId", itemId).getSingleResult();
        Basket basket = Basket.builder().consumer(consumer).item(item).quantity(quantity).build();
        em.persist(basket);
        em.close();
    }

    private void showItemInfo(int itemId) {

        System.out.println("-- Show Item Information --");
        EntityManager em = emf.createEntityManager();
        Item item = em.createQuery("select e from Item e where e.id = :itemId", Item.class)
                .setParameter("itemId", itemId).getSingleResult();

        List<ItemImg> itemImg = em.createQuery("select e from ItemImg e  where e.item = :item", ItemImg.class)
                .setParameter("item", item).getResultList();

        System.out.println(">> Item Name : "+item.getName());
        System.out.println(">> Item Category : "+item.getCategory());
        System.out.println(">> Item Price : "+item.getPrice());
        System.out.println(">> Item Stock : "+item.getStock());
        System.out.println(">> Item Description : "+item.getDescription());

        for (ItemImg img : itemImg) {
            System.out.println(">> Item Img Filename : "+img.getFileName());
        }


    }
}
