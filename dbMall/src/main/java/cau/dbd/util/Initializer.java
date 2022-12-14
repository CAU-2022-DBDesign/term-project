package cau.dbd.util;

import cau.dbd.entity.Order;
import cau.dbd.entity.OrderItem;
import cau.dbd.entity.OrderStatus;
import cau.dbd.entity.OrderStatus.Status;
import cau.dbd.entity.Payment;
import cau.dbd.entity.Payment.Method;
import cau.dbd.entity.complaint.Exchange;
import cau.dbd.entity.complaint.Refund;
import cau.dbd.entity.complaint.RefundAndExchangeReason;
import cau.dbd.entity.complaint.RefundAndExchangeStatus;
import cau.dbd.entity.item.Basket;
import cau.dbd.entity.item.Category;
import cau.dbd.entity.item.Item;
import cau.dbd.entity.item.ItemImg;
import cau.dbd.entity.item.Promotion;
import cau.dbd.entity.members.Consumer;
import cau.dbd.entity.members.Member.Gender;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Collectors;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

public class Initializer {

    /**
     * db 초기화 함수 추가할 데이터는 여기에 작성 요망
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

            Consumer noah = em.createQuery("select e from Consumer e where e.name = 'Noah'", Consumer.class)
                .getSingleResult();

            Consumer oliver = em.createQuery("select e from Consumer e where e.name = 'Oliver'", Consumer.class)
                .getSingleResult();
            Consumer william = em.createQuery("select e from Consumer e where e.name = 'William'", Consumer.class)
                    .getSingleResult();

            Consumer elijah = em.createQuery("select e from Consumer e where e.name = 'Elijah'", Consumer.class)
                    .getSingleResult();

            /*--------------- Category ------------*/
            Object[][] categoryData = {
                {"Food"},
                {"Electronics"},
                {"Drugstore"},
                {"Clothing"},
                {"Sports"},
                {"Office Supplies"},
                {"Household Goods"},
                {"Beauty"},
                {"Kitchen Utensils"},
                {"Vehicle Supplies"}
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

            Category foods = em.createQuery("select c from Category c where c.name = 'Food'",
                Category.class).getSingleResult();

            Object[][] foodItemData = {
                {"Can Cooker Par-Tee Cracker", 120000, 399,
                    "Can Cooker Par-Tee Cracker with Seasoning Chili Lime"},
                {"Grain Crunch Cereal", 25000, 7, "Nutrient Survival Freeze Dried Chocolate Grain Crunch Cereal"},
                {"Jack Link's Beef Jerky", 80000, 35,
                    "Teriyaki - Flavorful Meat Snack for Lunches, Ready to Eat, Great Stocking Stuffers"},
                {"Macaroni and Cheese Cups", 250000, 188,
                    "Velveeta Shells & Cheese Original Microwavable Macaroni and Cheese Cups, Thanksgiving and Christmas Dinner"},
            };

            for (Object[] data : foodItemData) {
                em.persist(Item.builder().name((String) data[0]).category(foods).price((int) data[1])
                    .stock((int) data[2]).description((String) data[3]).build());
            }

            Category sports = em.createQuery("select c from Category c where c.name = 'Sports'", Category.class)
                .getSingleResult();

            Object[][] sportsItemData = {
                {"Franklin Football - Grip-Rite 100", 58000, 40,
                    "DURABLE KIDS FOOTBALL, AGES 3 YEARS +: These junior footballs are constructed from a durable, high-grip, deep-pebbled rubber that stands up to wear and tear on grass, concrete, or any other surface"},
                {"Nike Everyday Cushion Crew Socks", 22000, 99,
                    "CREW SOCKS: Nike crew socks have a crew silhouette providing a comfortable fit around the calf that won't slip during workouts."},
                {"Nike Swoosh Headband", 9900, 50,
                    "Embroidered SWOOSH logo for visible brand recognition"},
                {"Plastic Sport Water Bottle", 26000, 222, "Ounce and milliliter markings."},
            };

            for (Object[] data : sportsItemData) {
                em.persist(Item.builder().name((String) data[0]).category(sports).price((int) data[1])
                    .stock((int) data[2]).description((String) data[3]).build());
            }

            Item cracker = em.createQuery("select e from Item e where e.name = 'Can Cooker Par-Tee Cracker'", Item.class).getSingleResult();
            Item bottle = em.createQuery("select e from Item e where e.name = 'Plastic Sport Water Bottle'", Item.class).getSingleResult();
            Item cereal = em.createQuery("select e from Item e where e.name = 'Grain Crunch Cereal'", Item.class).getSingleResult();

            /*--------------- Orders ------------*/
            Consumer liam = em.createQuery("select c from Consumer c where c.name = 'Liam'",
                Consumer.class).getSingleResult();
            Item galaxy = em.createQuery("select i from Item i where i.name = 'Galaxy S22 Ultra'",
                Item.class).getSingleResult();
            Item iPhone = em.createQuery("select i from Item i where i.name = 'iPhone 14 Pro Max'",
                Item.class).getSingleResult();
            Item xm4 = em.createQuery("select i from Item i where i.name = 'Sony WH-1000XM4'",
                Item.class).getSingleResult();

            List<Item> items = em.createQuery("select i from Item i",
                Item.class).getResultList();
            for (int i = 1; i <= 15; i++) {
                Order odr = Order.builder().consumer(liam).build();
                em.persist(odr);
                List<Item> randItems = new Random().ints(new Random().nextInt(5) + 1, 0, items.size())
                    .mapToObj(items::get).collect(Collectors.toList());
                int totalPrice = 0;
                for (Item item : randItems) {
                    em.persist(OrderItem.builder().item(item).order(odr).price(item.getPrice())
                        .quantity(new Random().nextInt(4) + 1).build());
                    totalPrice += item.getPrice();
                }
                if (i % 6 == 0) {
                    em.persist(OrderStatus.builder().status(Status.CANCELED).order(odr).build());
                } else {
                    OrderStatus purchasedStatus = OrderStatus.builder().status(Status.PURCHASED)
                        .order(odr).build();
                    em.persist(purchasedStatus);

                    em.persist(Payment.builder().orderStatus(purchasedStatus)
                        .method(Method.values()[(int) (Math.random() * Method.values().length)]).price(totalPrice)
                        .build());

                    em.persist(OrderStatus.builder().status(Status.RECEIVED)
                        .order(odr).build());
                    switch (new Random().nextInt(3)) {
                        case 2:
                            em.persist(OrderStatus.builder().status(Status.REFUNDED)
                                .order(odr).build());
                            break;
                        case 3:
                            em.persist(OrderStatus.builder().status(Status.EXCHANGED)
                                .order(odr).build());
                            break;
                    }
                }
            }
            

            /*--------------- ItemImg ------------*/
            em.persist(ItemImg.builder().item(galaxy).fileName(UUID.randomUUID().toString()).build());
            em.persist(ItemImg.builder().item(galaxy).fileName(UUID.randomUUID().toString()).build());
            em.persist(ItemImg.builder().item(galaxy).fileName(UUID.randomUUID().toString()).build());
            em.persist(ItemImg.builder().item(galaxy).fileName(UUID.randomUUID().toString()).build());
            em.persist(ItemImg.builder().item(iPhone).fileName(UUID.randomUUID().toString()).build());
            em.persist(ItemImg.builder().item(iPhone).fileName(UUID.randomUUID().toString()).build());
            em.persist(ItemImg.builder().item(iPhone).fileName(UUID.randomUUID().toString()).build());
            em.persist(ItemImg.builder().item(iPhone).fileName(UUID.randomUUID().toString()).build());
            em.persist(ItemImg.builder().item(xm4).fileName(UUID.randomUUID().toString()).build());
            em.persist(ItemImg.builder().item(xm4).fileName(UUID.randomUUID().toString()).build());
            em.persist(ItemImg.builder().item(xm4).fileName(UUID.randomUUID().toString()).build());
            em.persist(ItemImg.builder().item(xm4).fileName(UUID.randomUUID().toString()).build());

            /*--------------- Basket ------------*/
            em.persist(Basket.builder().consumer(liam).item(iPhone).quantity(1).build());
            em.persist(Basket.builder().consumer(liam).item(cereal).quantity(5).build());
            em.persist(Basket.builder().consumer(noah).item(xm4).quantity(3).build());
            em.persist(Basket.builder().consumer(noah).item(galaxy).quantity(5).build());
            em.persist(Basket.builder().consumer(oliver).item(cereal).quantity(3).build());
            em.persist(Basket.builder().consumer(oliver).item(bottle).quantity(5).build());
            em.persist(Basket.builder().consumer(william).item(bottle).quantity(1).build());
            em.persist(Basket.builder().consumer(william).item(cracker).quantity(2).build());
            em.persist(Basket.builder().consumer(elijah).item(cracker).quantity(2).build());
            em.persist(Basket.builder().consumer(elijah).item(iPhone).quantity(1).build());


            /*--------------- Promotion ------------*/
            em.persist(Promotion.builder().item(galaxy).discount(10000)
                .startAt(LocalDateTime.of(2022, 12, 05, 00, 00))
                .endAt(LocalDateTime.of(2022, 12, 31, 00, 00))
                .build());
            em.persist(Promotion.builder().item(xm4).discount(100000)
                .startAt(LocalDateTime.of(2022, 10, 05, 00, 00))
                .endAt(LocalDateTime.of(2022, 10, 06, 00, 00))
                .build());

            /*--------------- Exchange ------------*/
            //1
            OrderItem orderItem3 = em.createQuery("select e from Order o join o.orderItems e where e.id = 3",
                OrderItem.class).getSingleResult();
            em.persist(Exchange.builder().orderItem(orderItem3).reason(RefundAndExchangeReason.BAD_PRODUCT)
                .exchangeReasonDetail("Item Broken").quantity(1).status(RefundAndExchangeStatus.APPROVE).build());

            //2
            OrderItem orderItem6 = em.createQuery("select e from Order o join o.orderItems e where e.id = 6",
                    OrderItem.class).getSingleResult();
            em.persist(Exchange.builder().orderItem(orderItem6).reason(RefundAndExchangeReason.BAD_PRODUCT)
                    .exchangeReasonDetail("It's too bad").quantity(orderItem6.getQuantity()).status(RefundAndExchangeStatus.REQUEST).build());

            //3
            OrderItem orderItem8 = em.createQuery("select e from Order o join o.orderItems e where e.id = 8",
                    OrderItem.class).getSingleResult();
            em.persist(Exchange.builder().orderItem(orderItem8).reason(RefundAndExchangeReason.SIMPLE_CHANGE_OF_HEART)
                    .exchangeReasonDetail("I don't need it.").quantity(orderItem8.getQuantity()).status(RefundAndExchangeStatus.COMPLETED).build());

            //4
            OrderItem orderItem10 = em.createQuery("select e from Order o join o.orderItems e where e.id = 10",
                    OrderItem.class).getSingleResult();
            em.persist(Exchange.builder().orderItem(orderItem10).reason(RefundAndExchangeReason.SIMPLE_CHANGE_OF_HEART)
                    .exchangeReasonDetail("I don't need it.").quantity(1).status(RefundAndExchangeStatus.REJECT).build());
            //5
            OrderItem orderItem12 = em.createQuery("select e from Order o join o.orderItems e where e.id = 12",
                    OrderItem.class).getSingleResult();
            em.persist(Exchange.builder().orderItem(orderItem12).reason(RefundAndExchangeReason.DELIVERY_DELAY)
                    .exchangeReasonDetail("Too late").quantity(1).status(RefundAndExchangeStatus.APPROVE).build());

            //6
            OrderItem orderItem14 = em.createQuery("select e from Order o join o.orderItems e where e.id = 14",
                    OrderItem.class).getSingleResult();
            em.persist(Exchange.builder().orderItem(orderItem14).reason(RefundAndExchangeReason.BAD_PRODUCT)
                    .exchangeReasonDetail("Item Broken").quantity(1).status(RefundAndExchangeStatus.APPROVE).build());

            //7
            OrderItem orderItem16 = em.createQuery("select e from Order o join o.orderItems e where e.id = 16",
                    OrderItem.class).getSingleResult();
            em.persist(Exchange.builder().orderItem(orderItem16).reason(RefundAndExchangeReason.BAD_PRODUCT)
                    .exchangeReasonDetail("It's too bad").quantity(orderItem16.getQuantity()).status(RefundAndExchangeStatus.REQUEST).build());

            //8
            OrderItem orderItem18 = em.createQuery("select e from Order o join o.orderItems e where e.id = 18",
                    OrderItem.class).getSingleResult();
            em.persist(Exchange.builder().orderItem(orderItem18).reason(RefundAndExchangeReason.SIMPLE_CHANGE_OF_HEART)
                    .exchangeReasonDetail("I don't need it.").quantity(orderItem18.getQuantity()).status(RefundAndExchangeStatus.COMPLETED).build());

            //9
            OrderItem orderItem20 = em.createQuery("select e from Order o join o.orderItems e where e.id = 20",
                    OrderItem.class).getSingleResult();
            em.persist(Exchange.builder().orderItem(orderItem20).reason(RefundAndExchangeReason.SIMPLE_CHANGE_OF_HEART)
                    .exchangeReasonDetail("I changed my mind").quantity(orderItem20.getQuantity()).status(RefundAndExchangeStatus.COMPLETED).build());


            //9
            OrderItem orderItem22 = em.createQuery("select e from Order o join o.orderItems e where e.id = 22",
                    OrderItem.class).getSingleResult();
            em.persist(Exchange.builder().orderItem(orderItem22).reason(RefundAndExchangeReason.DELIVERY_DELAY)
                    .exchangeReasonDetail("Bye").quantity(1).status(RefundAndExchangeStatus.COMPLETED).build());


            /*--------------- Refund ------------*/
            //1
            OrderItem orderItem5 = em.createQuery("select e from Order o join o.orderItems e where e.id = 5",
                OrderItem.class).getSingleResult();
            em.persist(Refund.builder().orderItem(orderItem5).reason(RefundAndExchangeReason.DELIVERY_DELAY)
                .refundReasonDetail("TOO LATE!!").quantity(1).status(RefundAndExchangeStatus.REQUEST).build());
            //2
            OrderItem orderItem7 = em.createQuery("select e from Order o join o.orderItems e where e.id = 7",
                    OrderItem.class).getSingleResult();
            em.persist(Refund.builder().orderItem(orderItem7).reason(RefundAndExchangeReason.BAD_PRODUCT)
                    .refundReasonDetail("Broken Item").quantity(1).status(RefundAndExchangeStatus.REQUEST).build());

            //3
            OrderItem orderItem9 = em.createQuery("select e from Order o join o.orderItems e where e.id = 9",
                    OrderItem.class).getSingleResult();
            em.persist(Refund.builder().orderItem(orderItem9).reason(RefundAndExchangeReason.BAD_PRODUCT)
                    .refundReasonDetail("Too Bad").quantity(orderItem9.getQuantity()).status(RefundAndExchangeStatus.REQUEST).build());

            //4
            OrderItem orderItem11 = em.createQuery("select e from Order o join o.orderItems e where e.id = 11",
                    OrderItem.class).getSingleResult();
            em.persist(Refund.builder().orderItem(orderItem11).reason(RefundAndExchangeReason.DELIVERY_DELAY)
                    .refundReasonDetail("Too Late to use").quantity(orderItem11.getQuantity()).status(RefundAndExchangeStatus.REJECT).build());

            //5
            OrderItem orderItem13 = em.createQuery("select e from Order o join o.orderItems e where e.id = 13",
                    OrderItem.class).getSingleResult();
            em.persist(Refund.builder().orderItem(orderItem13).reason(RefundAndExchangeReason.DELIVERY_DELAY)
                    .refundReasonDetail("Too Late to use").quantity(1).status(RefundAndExchangeStatus.COMPLETED).build());

            //6
            OrderItem orderItem15 = em.createQuery("select e from Order o join o.orderItems e where e.id = 15",
                    OrderItem.class).getSingleResult();
            em.persist(Refund.builder().orderItem(orderItem15).reason(RefundAndExchangeReason.SIMPLE_CHANGE_OF_HEART)
                    .refundReasonDetail("I don't need it anymore.").quantity(1).status(RefundAndExchangeStatus.COMPLETED).build());

            //7
            OrderItem orderItem17 = em.createQuery("select e from Order o join o.orderItems e where e.id = 17",
                    OrderItem.class).getSingleResult();
            em.persist(Refund.builder().orderItem(orderItem17).reason(RefundAndExchangeReason.SIMPLE_CHANGE_OF_HEART)
                    .refundReasonDetail("I don't need it anymore.").quantity(orderItem17.getQuantity()).status(RefundAndExchangeStatus.REJECT).build());

            //8
            OrderItem orderItem19 = em.createQuery("select e from Order o join o.orderItems e where e.id = 19",
                    OrderItem.class).getSingleResult();
            em.persist(Refund.builder().orderItem(orderItem19).reason(RefundAndExchangeReason.DIFFERENCE_WITH_PRODUCT_INFORMATION)
                    .refundReasonDetail("It's different!!").quantity(orderItem19.getQuantity()).status(RefundAndExchangeStatus.REQUEST).build());

            //9
            OrderItem orderItem21 = em.createQuery("select e from Order o join o.orderItems e where e.id = 21",
                    OrderItem.class).getSingleResult();
            em.persist(Refund.builder().orderItem(orderItem21).reason(RefundAndExchangeReason.DIFFERENCE_WITH_PRODUCT_INFORMATION)
                    .refundReasonDetail("It's different FROM what I see in website!").quantity(1).status(RefundAndExchangeStatus.REQUEST).build());


            //10
            OrderItem orderItem23 = em.createQuery("select e from Order o join o.orderItems e where e.id = 23",
                    OrderItem.class).getSingleResult();
            em.persist(Refund.builder().orderItem(orderItem23).reason(RefundAndExchangeReason.DELIVERY_DELAY)
                    .refundReasonDetail("it took 1 year.").quantity(1).status(RefundAndExchangeStatus.COMPLETED).build());

            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        } finally {
            em.close();
        }

    }
}
