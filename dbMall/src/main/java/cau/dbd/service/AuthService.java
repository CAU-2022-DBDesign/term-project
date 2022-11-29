package cau.dbd.service;

import cau.dbd.entity.Member;
import cau.dbd.util.MyScanner;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class AuthService {

    private EntityManagerFactory emf;

    /**
     * 회원가입 화면
     */
    public void signUp() {
        MemberService memberService = new MemberService(emf);
        System.out.println("-- Sign up --");
        memberService.insert();
    }

    /**
     * 로그인 화면
     */
    public void signIn() {
        ConsumerService consumerService = new ConsumerService(emf);
        System.out.println("-- Sign in --");

        EntityManager em = emf.createEntityManager();

        // 모든 멤버 리스트를 보여줌
        List<Member> members = em.createQuery("select m from Member m", Member.class).getResultList();
        members.forEach(member -> {
            System.out.printf("[%d] %s - %s%n", member.getId(), member.getName(), member.getClass().getSimpleName());
        });

        // 아이디 하나 선택하여 로그인 진행
        System.out.print("[SYSTEM] Enter your id : ");
        int memberId = MyScanner.getIntInRange(1, 3);
        Member member = members.stream().filter(mem -> memberId==mem.getId()).findFirst().get();

        // 권한에 따라 메뉴 다르게 보여줌
        switch(member.getClass().getSimpleName()) {
            case "Consumer" :
                consumerService.mainMenu(member);
                break;
            case "Producer" :
               // ...
        }

        em.close();
    }
}
