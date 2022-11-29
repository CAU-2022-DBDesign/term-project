package cau.dbd.service;

import cau.dbd.entity.Member;
import cau.dbd.entity.Order;
import cau.dbd.util.MyScanner;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ConsumerService {

    private EntityManagerFactory emf;

    /**
     * 소비자 메인 메뉴
     * 상품 조회, 장바구니 추가, 주문, 주문내역, 결제내역, 컴플레인 등의 기능이 추가되어야 함
     * @param member
     */
    public void mainMenu(Member member) {
        while (true) {
            System.out.printf("\n******Consumer Menu - %s *******\n\n", member.getName());
            System.out.print("[SYSTEM] 1: See all orders / 3:Sign out : ");
            switch (MyScanner.getIntInRange(1, 3)) {
                case 1:
                    selectAllOrder(member.getId());
                    break;
                case 3:
                    return;
            }
        }
    }

    /**
     * 주문 내역 조회
     * @param id
     */
    public void selectAllOrder(Long id) {
        System.out.println("-- My Orders --");

        EntityManager em = emf.createEntityManager();

        List<Order> orders = em.createQuery("select e from Order e where e.consumer.id = :id ", Order.class).setParameter("id", id).getResultList();
        orders.forEach(order -> {
            System.out.printf("[%d] %s%n", order.getId(), order.getCreatedAt());
        });
        em.close();
    }

}
