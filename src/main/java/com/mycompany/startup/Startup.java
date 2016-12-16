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
/*
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");

        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();

        //creating transaction object  
        Transaction t = session.beginTransaction();

        Klub klub = new Klub();
        klub.setNazwa("Jagiellonia");
        klub.setLogo("src/main/resources/static/images/jaga.jpg", session);
        session.persist(klub);
        
        
        Klub klub2 = new Klub();
        klub2.setNazwa("Legia");
        klub2.setLogo("src/main/resources/static/images/legia.jpg", session);
        session.persist(klub2);

        Sekcja sekcja = new Sekcja();
        sekcja.setDyscyplina("Football");
        sekcja.setPlec("M");
        sekcja.setIdKlub(klub);
        session.persist(sekcja);

        Sekcja sekcja1 = new Sekcja();
        sekcja1.setDyscyplina("Volleyball");
        sekcja1.setPlec("F");
        
        Sekcja sekcja2 = new Sekcja();
        sekcja2.setDyscyplina("Football");
        sekcja2.setPlec("M");
        sekcja2.setIdKlub(klub2);
        session.persist(sekcja);

        Sekcja sekcja3 = new Sekcja();
        sekcja3.setDyscyplina("Volleyball");
        sekcja3.setPlec("F");

        sekcja1.setIdKlub(klub);
        session.persist(sekcja1);
        
        
        
        
        Budynek b = new Budynek();
        b.setKodPocztowy("15-111");
        b.setUlicanumer("Słoneczna 1");
        b.setMiejscowosc("Białystok");
        b.setIdKlub(klub);
        session.persist(b);

        Budynek b1 = new Budynek();
        b1.setKodPocztowy("16-002");
        b1.setUlicanumer("Szkolna 15");
        b1.setMiejscowosc("Pogorzałki");
        b1.setIdKlub(klub);
        session.persist(b1);

        Stadion stadion=new Stadion(b.getIdbudynek());
        stadion.setDyscyplina("Piłka Nożna");
        stadion.setIloscMiejsc(25000);
        stadion.setNazwa("Słoneczna");
        ObiektTreningowy bTrening = new ObiektTreningowy(b1.getIdbudynek());
        bTrening.setBudynek(b1);
        bTrening.setDyscyplina("Piłka Nożna");
        session.persist(bTrening);

        Liga league = new Liga();
        league.setKraj("Poland");
        league.setNazwa("Ekstraklasa");

        session.persist(league);
        
        Liga league1 = new Liga();
        league1.setKraj("England");
        league1.setNazwa("Premier League");
        session.persist(league1);


        Druzyna team = new Druzyna();
        team.setNazwa("Jagiellonia");
        team.setIdSekcja(sekcja1);
        team.setIdLiga(league);

        session.persist(team);
        
        
        
        Druzyna team1 = new Druzyna();
        team1.setNazwa("Legia CWKS");
        team1.setIdSekcja(sekcja2);
        team1.setIdLiga(league);
        session.persist(team);

        Zawodnik z = new Zawodnik();

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date myDate = formatter.parse("1995-02-12");
        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
        z.setDataUrodzenia(sqlDate);
        z.setImie("Karol");
        z.setNazwisko("Nowicki");
        z.setWaga(70);
        z.setWzrost(180);
        z.setIdDruzyna(team);
        
        session.persist(z);
        
        
        
        
        
Zawodnik z1 = new Zawodnik();
        Date myDate1 = formatter.parse("1995-02-12");
        java.sql.Date sqlDate1 = new java.sql.Date(myDate1.getTime());
        z1.setDataUrodzenia(sqlDate1);
        z1.setImie("Konrad");
        z1.setNazwisko("Dunikowski");
        z1.setWaga(70);
        z1.setWzrost(180);
        z1.setIdDruzyna(team);
        
        session.persist(z1);

        Sezon season2016 = new Sezon(2016);
        session.persist(season2016);
        Sezon season2015 = new Sezon(2015);
        session.persist(season2015);
        Sezon season2014 = new Sezon(2014);
        session.persist(season2014);

        ZawodnikStatystykiPK zspk = new ZawodnikStatystykiPK(z.getIdZawodnik(), season2016.getIdSezon());

        ZawodnikStatystyki playerStatistics = new ZawodnikStatystyki(zspk);
        playerStatistics.setCzerwoneKartki(5);
        playerStatistics.setFaule(40);
        playerStatistics.setRozegraneMinuty(153);
        playerStatistics.setZawodnik(z);
        playerStatistics.setStrzeloneBramki(0);
        playerStatistics.setStraconeBramki(0);
        playerStatistics.setZolteKartki(12);
        playerStatistics.setSezon(season2016);
        session.persist(playerStatistics);
        
        
        
        
        ZawodnikStatystykiPK zspk1 = new ZawodnikStatystykiPK(z1.getIdZawodnik(), season2016.getIdSezon());

        ZawodnikStatystyki playerStatistics1 = new ZawodnikStatystyki(zspk1);
        playerStatistics1.setCzerwoneKartki(5);
        playerStatistics1.setFaule(40);
        playerStatistics1.setRozegraneMinuty(153);
        playerStatistics1.setZawodnik(z1);
        playerStatistics1.setStrzeloneBramki(0);
        playerStatistics1.setStraconeBramki(0);
        playerStatistics1.setZolteKartki(12);
        playerStatistics1.setSezon(season2016);
        session.persist(playerStatistics1);
        
        
        
        
        ZawodnikStatystykiPK zspk2 = new ZawodnikStatystykiPK(z1.getIdZawodnik(), season2015.getIdSezon());

        ZawodnikStatystyki playerStatistics2 = new ZawodnikStatystyki(zspk2);
        playerStatistics2.setCzerwoneKartki(0);
        playerStatistics2.setFaule(0);
        playerStatistics2.setRozegraneMinuty(1903);
        playerStatistics2.setZawodnik(z);
        playerStatistics2.setStrzeloneBramki(0);
        playerStatistics2.setStraconeBramki(0);
        playerStatistics2.setZolteKartki(12);
        playerStatistics2.setSezon(season2016);
        session.persist(playerStatistics2);

        
        t.commit();//transaction is committed  
        session.disconnect();

        session.close();*/

    }

}
