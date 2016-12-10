/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.startup;

import com.mycompany.model.Budynek;
import com.mycompany.model.BudynekAdministracyjny;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import com.mycompany.model.Klub;
import com.mycompany.model.ObiektTreningowy;
import com.mycompany.model.Sekcja;
import com.mycompany.model.Stadion;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 *
 * @author Karol
 */
@SpringBootApplication
@ComponentScan(basePackages = {"com.mycompany.controllers", "com.mycompany.startup"})
public class Startup {

    static final Logger logger = Logger.getLogger(Startup.class);

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws FileNotFoundException, SQLException, IOException {
        SpringApplication.run(Startup.class, args);
        logger.info("Application started");

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();

        //creating transaction object  
        Transaction t = session.beginTransaction();

        Klub klub = new Klub();
        klub.setNazwa("Jaga");
        klub.setLogo("src/main/resources/static/images/jaga.jpg", session);
        session.persist(klub);

        Sekcja sekcja = new Sekcja();
        sekcja.setDyscyplina("Żużel");
        sekcja.setPlec("x");
        sekcja.setIdKlub(klub);
        session.persist(sekcja);

        Budynek b = new Budynek();
        b.setKodPocztowy("Kod");
        b.setUlicanumer("ulica");
        b.setMiejscowosc("DD");
        b.setIdKlub(klub);
        session.persist(b);

        Budynek b1 = new Budynek();
        b1.setKodPocztowy("Kodd");
        b1.setUlicanumer("ulicaa");
        b1.setMiejscowosc("DDD");
        b1.setIdKlub(klub);
        session.persist(b1);

        ObiektTreningowy bTrening = new ObiektTreningowy(b1.getIdbudynek());
        bTrening.setBudynek(b1);
        bTrening.setDyscyplina("pici poolo");
        session.persist(bTrening);

        Budynek b2 = new Budynek();
        b2.setKodPocztowy("Koddd");
        b2.setUlicanumer("ulicaaa");
        b2.setMiejscowosc("DDDD");
        b2.setIdKlub(klub);
        session.persist(b2);

        BudynekAdministracyjny badmin = new BudynekAdministracyjny(b2.getIdbudynek());
        badmin.setBudynek(b2);

        session.persist(badmin);

        Stadion stadion = new Stadion(b.getIdbudynek());
        stadion.setNazwa("Santiago");
        stadion.setIloscMiejsc(123);
        stadion.setDyscyplina("picipolo");
        stadion.setBudynek(b);
        //stadion.setBudynek(b);
        session.persist(stadion);
        t.commit();//transaction is committed  
        session.disconnect();

        session.close();

    }

}
