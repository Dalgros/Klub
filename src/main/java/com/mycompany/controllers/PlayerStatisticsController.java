/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controllers;

import com.mycompany.forms.PlayerForm;
import com.mycompany.forms.PlayerStatisticsForm;
import com.mycompany.model.Liga;
import com.mycompany.model.Sezon;
import com.mycompany.model.Zawodnik;
import com.mycompany.model.ZawodnikStatystyki;
import com.mycompany.model.ZawodnikStatystykiPK;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author user
 */
@Controller
@RequestMapping("/club/{idClub}/sections/{idSection}/teams/{idTeam}/players/{idPlayer}")
public class PlayerStatisticsController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showPlayerStatistics(@PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idTeam") String idTeam, @PathVariable("idPlayer") String idPlayer, Model model) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Query query = session.createQuery("from ZawodnikStatystyki where id_zawodnik=:id");
        query.setParameter("id", idPlayer);
        List<ZawodnikStatystyki> statisticsList = query.getResultList();
        Zawodnik player = session.find(Zawodnik.class, Integer.parseInt(idPlayer));
        model.addAttribute("statisticsList", statisticsList);
        model.addAttribute("Club", idClub);
        model.addAttribute("Team", idTeam);
        model.addAttribute("Section", idSection);
        model.addAttribute("Player", player);
        session.close();
        factory.close();
        return "player/show_concreteplayer_view";
    }

    @GetMapping("/edit/{idSeason}")
    public String editStatistics(PlayerStatisticsForm playerStatisticsForm, Model model, @PathVariable("idTeam") String idTeam, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idPlayer") String idPlayer, @PathVariable("idSeason") String idSeason) {
        model.addAttribute("Section", idSection);
        model.addAttribute("Club", idClub);
        model.addAttribute("Team", idTeam);
        model.addAttribute("Player", idPlayer);
        model.addAttribute("Sezon", idSeason);

        return "/player/edit_playerstatistics_view";
    }

    @RequestMapping(value = "/edit/{idSeason}", method = RequestMethod.POST)
    public ModelAndView editPlayerStatistics(@Valid PlayerStatisticsForm playerStatisticsForm, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idTeam") String idTeam, Model model, @PathVariable("idPlayer") String idPlayer, @PathVariable("idSeason") String idSeason) throws ParseException {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Query query = session.createQuery("from ZawodnikStatystyki where id_zawodnik=:id and id_sezon=:ids");
        query.setParameter("id", idPlayer);
        query.setParameter("ids", idSeason);
        List<ZawodnikStatystyki> statistic = query.getResultList();
        statistic.get(0).setCzerwoneKartki(Integer.parseInt(playerStatisticsForm.getRedCards()));
        statistic.get(0).setZolteKartki(Integer.parseInt(playerStatisticsForm.getYellowCards()));
        statistic.get(0).setRozegraneMinuty(Integer.parseInt(playerStatisticsForm.getMinutesPlayed()));
        statistic.get(0).setFaule(Integer.parseInt(playerStatisticsForm.getFaulsCommited()));
        statistic.get(0).setStrzeloneBramki(Integer.parseInt(playerStatisticsForm.getScoredGoals()));
        statistic.get(0).setStraconeBramki(Integer.parseInt(playerStatisticsForm.getLostGoals()));

        session.persist(statistic.get(0));
        t.commit();
        session.close();
        factory.close();
        return new ModelAndView("redirect:/club/" + idClub + "/sections/" + idSection + "/teams/" + idTeam + "/players/" + idPlayer + "/");

    }

    @GetMapping("/remove/{idSeason}")
    public ModelAndView removeStatitics(Model model, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idTeam") String idTeam, @PathVariable("idPlayer") String idPlayer, @PathVariable("idSeason") String idSeason) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        Query query = session.createQuery("from ZawodnikStatystyki where id_zawodnik=:id and id_sezon=:ids");
        query.setParameter("id", idPlayer);
        query.setParameter("ids", idSeason);
        List<ZawodnikStatystyki> statistic = query.getResultList();

        session.remove(statistic.get(0));
        

        t.commit();
        session.close();
        factory.close();
        return new ModelAndView("redirect:/club/" + idClub + "/sections/" + idSection + "/teams/" + idTeam + "/players/" + idPlayer + "/");
    }

    @GetMapping("/create/")
    public String createStatistics(PlayerStatisticsForm playerStatisticsForm, Model model, @PathVariable("idTeam") String idTeam, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idPlayer") String idPlayer) {
        model.addAttribute("Section", idSection);
        model.addAttribute("Club", idClub);
        model.addAttribute("Team", idTeam);
        model.addAttribute("Player", idPlayer);
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        List<Sezon> seasonList = session.createCriteria(Sezon.class).list();
        System.out.println("" + seasonList.size());
        model.addAttribute("seasonList", seasonList);
        t.commit();
        session.close();
        factory.close();
        return "/player/create_playerstatistics_view";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createstatistics(PlayerStatisticsForm playerStatisticsForm, Model model, @PathVariable("idTeam") String idTeam, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idPlayer") String idPlayer) {
        model.addAttribute("Section", idSection);
        model.addAttribute("Club", idClub);
        model.addAttribute("Team", idTeam);
        model.addAttribute("Player", idPlayer);
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        
        ZawodnikStatystyki playerStatistics=new ZawodnikStatystyki();
        playerStatistics.setCzerwoneKartki(Integer.parseInt(playerStatisticsForm.getRedCards()));
        playerStatistics.setZolteKartki(Integer.parseInt(playerStatisticsForm.getYellowCards()));
        playerStatistics.setRozegraneMinuty(Integer.parseInt(playerStatisticsForm.getMinutesPlayed()));
        playerStatistics.setFaule(Integer.parseInt(playerStatisticsForm.getFaulsCommited()));
        playerStatistics.setStrzeloneBramki(Integer.parseInt(playerStatisticsForm.getScoredGoals()));
        playerStatistics.setStraconeBramki(Integer.parseInt(playerStatisticsForm.getLostGoals()));
        
        
        
        Query query = session.createQuery("from Sezon where rok=:rok");
        query.setParameter("rok", Integer.parseInt(playerStatisticsForm.getSeason()));
        List<Sezon> seasonList=query.getResultList();
        playerStatistics.setSezon(seasonList.get(0));
        
        ZawodnikStatystykiPK zspk =new ZawodnikStatystykiPK(Integer.parseInt(idPlayer),seasonList.get(0).getIdSezon());
        playerStatistics.setZawodnikStatystykiPK(zspk);
        session.persist(playerStatistics);
        t.commit();
        session.close();
        factory.close();

        return new ModelAndView("redirect:/club/" + idClub + "/sections/" + idSection + "/teams/" + idTeam + "/players/" + idPlayer + "/");
    }

}
