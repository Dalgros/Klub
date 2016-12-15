/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.startup;

import com.mycompany.model.Budynek;
import com.mycompany.model.BudynekAdministracyjny;
import com.mycompany.model.Druzyna;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import com.mycompany.model.Klub;
import com.mycompany.model.Liga;
import com.mycompany.model.ObiektTreningowy;
import com.mycompany.model.Sekcja;
import com.mycompany.model.Sezon;
import com.mycompany.model.Stadion;
import com.mycompany.model.Zawodnik;
import com.mycompany.model.ZawodnikStatystyki;
import com.mycompany.model.ZawodnikStatystykiPK;
import java.io.FileNotFoundException;
import java.io.IOException;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
    public static void main(String[] args) throws FileNotFoundException, SQLException, IOException, ParseException {
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

        Sekcja sekcja1 = new Sekcja();
        sekcja1.setDyscyplina("picipolo");
        sekcja1.setPlec("p");

        sekcja1.setIdKlub(klub);
        session.persist(sekcja1);
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

        Liga league = new Liga();
        league.setKraj("POLSKA");
        league.setNazwa("Ogorowa");

        session.persist(league);

        Druzyna team = new Druzyna();
        team.setNazwa("toMy");
        team.setIdSekcja(sekcja1);
        team.setIdLiga(league);

        session.persist(team);

        Zawodnik z = new Zawodnik();

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = formatter.parse("1995-02-12");
        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
        z.setDataUrodzenia(sqlDate);
        z.setImie("imie");
        z.setNazwisko("nazwisko");
        z.setWaga(70);
        z.setWzrost(180);
        z.setIdDruzyna(team);
        
        session.persist(z);

        Sezon season = new Sezon(12);
        session.persist(season);

        ZawodnikStatystykiPK zspk = new ZawodnikStatystykiPK(z.getIdZawodnik(), season.getIdSezon());

        ZawodnikStatystyki playerStatistics = new ZawodnikStatystyki(zspk);
        playerStatistics.setCzerwoneKartki(Integer.MIN_VALUE);
        playerStatistics.setFaule(4);
        playerStatistics.setRozegraneMinuty(153);
        playerStatistics.setZawodnik(z);
        playerStatistics.setStraconeBramki(0);
        playerStatistics.setStraconeBramki(0);
        playerStatistics.setZolteKartki(0);
        playerStatistics.setSezon(season);

        session.persist(playerStatistics);

        t.commit();//transaction is committed  
        session.disconnect();

        session.close();

    }

}
