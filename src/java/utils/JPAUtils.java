package utils;

import java.io.Serializable;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtils implements Serializable {
    private static final EntityManagerFactory emf;
    
    static {
        try {
            emf = Persistence.createEntityManagerFactory("SP26_SE1919_SE200019_NhanNKL_Workshop2PU");
        } catch (Exception ex) {
            throw new RuntimeException();
        }
    }
    
    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }
    
    public static void shutdown() {
        emf.close();
    }
}
