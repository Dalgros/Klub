/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.startup;

import com.mycompany.model.Building;
import com.mycompany.model.AdministrationBuilding;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import com.mycompany.model.Club;
import com.mycompany.model.TrainingObject;
import com.mycompany.model.Subdivision;
import com.mycompany.model.Arena;
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

        Club klub = new Club();
        klub.setNazwa("Jaga");
        klub.setLogo("src/main/resources/static/images/jaga.jpg", session);
        session.persist(klub);

        Subdivision sekcja = new Subdivision();
        sekcja.setDyscyplina("Żużel");
        sekcja.setPlec("x");
        sekcja.setIdKlub(klub);
        session.persist(sekcja);

        Building b = new Building();
        b.setKodPocztowy("Kod");
        b.setUlicanumer("ulica");
        b.setMiejscowosc("DD");
        b.setIdKlub(klub);
        session.persist(b);

        Building b1 = new Building();
        b1.setKodPocztowy("Kodd");
        b1.setUlicanumer("ulicaa");
        b1.setMiejscowosc("DDD");
        b1.setIdKlub(klub);
        session.persist(b1);

        TrainingObject bTrening = new TrainingObject(b1.getIdbudynek());
        bTrening.setBudynek(b1);
        bTrening.setDyscyplina("pici poolo");
        session.persist(bTrening);

        Building b2 = new Building();
        b2.setKodPocztowy("Koddd");
        b2.setUlicanumer("ulicaaa");
        b2.setMiejscowosc("DDDD");
        b2.setIdKlub(klub);
        session.persist(b2);

        AdministrationBuilding badmin = new AdministrationBuilding(b2.getIdbudynek());
        badmin.setBudynek(b2);

        session.persist(badmin);

        Arena stadion = new Arena(b.getIdbudynek());
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
