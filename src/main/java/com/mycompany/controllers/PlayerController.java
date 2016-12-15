/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controllers;

import com.mycompany.forms.PlayerForm;
import com.mycompany.forms.TeamForm;
import com.mycompany.model.Druzyna;
import com.mycompany.model.Liga;
import com.mycompany.model.Sekcja;
import com.mycompany.model.Zawodnik;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.validation.Valid;
import org.apache.log4j.Logger;
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
@RequestMapping("/club/{idClub}/sections/{idSection}/teams/{idTeam}/players")

public class PlayerController {

    Logger log = Logger.getLogger(TeamController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showPlayers(@PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idTeam") String idTeam, Model model) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Query query = session.createQuery("from Zawodnik where id_druzyna=:id");
        query.setParameter("id", idTeam);
        List<Zawodnik> playerList = query.getResultList();
        model.addAttribute("playersList", playerList);
        model.addAttribute("Club", idClub);
        model.addAttribute("Team", idTeam);
        model.addAttribute("Section", idSection);
        session.close();
        return "player/show_player_view";
    }

    @GetMapping("/remove/{idPlayer}")
    public ModelAndView removePlayer(Model model, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idTeam") String idTeam, @PathVariable("idPlayer") String idPlayer) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Zawodnik player = session.find(Zawodnik.class, Integer.parseInt(idPlayer));
        session.remove(player);
        t.commit();
        session.close();
        factory.close();
        return new ModelAndView("redirect:/club/" + idClub + "/sections/" + idSection + "/teams/" + idTeam + "/players/");
    }

    @GetMapping("/create")
    public String createPlayer(PlayerForm playerForm, Model model, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idTeam") String idTeam) {
        model.addAttribute("Section", idSection);
        model.addAttribute("Club", idClub);
        model.addAttribute("Team", idTeam);

        return "/player/create_player_view";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createplayer(@Valid PlayerForm playerForm, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idTeam") String idTeam, Model model) throws ParseException {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Druzyna team = session.find(Druzyna.class, Integer.parseInt(idTeam));

        Zawodnik player = new Zawodnik();
        player.setImie(playerForm.getName());

        String year = playerForm.getYear();
        String day = playerForm.getDay();
        String month = playerForm.getMonth();

        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder s = new StringBuilder(year);
        s.append("-");
        s.append(month);
        s.append("-");
        s.append(day);
        Date myDate = formatter.parse(s.toString());
        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
        player.setDataUrodzenia(sqlDate);
        player.setNazwisko(playerForm.getLastname());
        player.setWaga(Integer.parseInt(playerForm.getWeight()));
        player.setWzrost(Integer.parseInt(playerForm.getHeight()));
        player.setIdDruzyna(team);
        session.persist(player);
        t.commit();
        session.close();
        factory.close();
        return new ModelAndView("redirect:/club/" + idClub + "/sections/" + idSection + "/teams/" + idTeam + "/players/");
    }

    @GetMapping("/edit/{idPlayer}")
    public String editPlayer(PlayerForm playerForm, Model model, @PathVariable("idTeam") String idTeam, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idPlayer") String idPlayer) {
        model.addAttribute("Section", idSection);
        model.addAttribute("Club", idClub);
        model.addAttribute("Team", idTeam);
        model.addAttribute("Player", idPlayer);
        return "/player/edit_player_view";
    }

    @RequestMapping(value = "/edit/{idPlayer}", method = RequestMethod.POST)
    public ModelAndView editteam(@Valid PlayerForm playerForm, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idTeam") String idTeam, Model model, @PathVariable("idPlayer") String idPlayer) throws ParseException {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        Zawodnik player = session.find(Zawodnik.class, Integer.parseInt(idPlayer));
        player.setImie(playerForm.getName());
        player.setNazwisko(playerForm.getLastname());
        String year = playerForm.getYear();
        String day = playerForm.getDay();
        String month = playerForm.getMonth();
        player.setWaga(Integer.parseInt(playerForm.getWeight()));
        player.setWzrost(Integer.parseInt(playerForm.getHeight()));
        DateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        StringBuilder s = new StringBuilder(year);
        s.append("-");
        s.append(month);
        s.append("-");
        s.append(day);
        Date myDate = formatter.parse(s.toString());
        java.sql.Date sqlDate = new java.sql.Date(myDate.getTime());
        player.setDataUrodzenia(sqlDate);
        t.commit();
        session.close();
        return new ModelAndView("redirect:/club/" + idClub + "/sections/" + idSection + "/teams/"+idTeam+"/players/");
    }

}
