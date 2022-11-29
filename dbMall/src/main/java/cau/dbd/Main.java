package cau.dbd;

import cau.dbd.service.AuthService;
import cau.dbd.util.Initializer;
import cau.dbd.util.MyScanner;
import java.util.logging.Level;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    /**
     * entry point
     */
    public static void main(String[] args) {
        // Hibernate 관련 로그 범위 변경
        java.util.logging.Logger.getLogger("org.hibernate").setLevel(Level.SEVERE);

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("dbMall");
        AuthService authService = new AuthService(emf);

        Initializer.initialize(emf);

        while (true) {
            System.out.println("\n******DB Mall*******\n");
            System.out.print("[SYSTEM] 1:Sign in / 2:Sign up / 3:Quit : ");
            switch (MyScanner.getIntInRange(1, 3)) {
                case 1:
                    authService.signIn();
                    break;
                case 2:
                    authService.signUp();
                    break;
                case 3:
                    emf.close();
                    return;
            }
        }
    }
}
