package com.cheeper.buildbulk;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.PersistenceUnit;

@Component
public class TessteUserJpa implements CommandLineRunner {

    @PersistenceUnit
    private EntityManagerFactory emf;

    @Override
    public void run(String... args) throws Exception {
        long inicio = System.currentTimeMillis();

        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();

        tx.begin();

        for (int i = 0; i < 10000; i++) {
            em.createNativeQuery(
                    "insert into user (name, email,profile_name,password, bio, verified_email) " +
                            "values ('Mario', 'mario@cheeper.com', 'mario" + i + "', '123', 'CTO', true);").executeUpdate();
        }
        tx.commit();

        long fim = System.currentTimeMillis();
        System.out.println(" ******** TEMPO: " + (fim - inicio) / 1000);
    }
}
