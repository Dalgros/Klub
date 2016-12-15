/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controllers;

import com.mycompany.forms.SectionForm;
import com.mycompany.forms.TeamForm;
import com.mycompany.model.Druzyna;
import com.mycompany.model.Klub;
import com.mycompany.model.Liga;
import com.mycompany.model.Sekcja;
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
@RequestMapping("/club/{idClub}/sections/{idSection}/teams")

public class TeamController {

    Logger log = Logger.getLogger(TeamController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String showTeams(@PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, Model model) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Query query = session.createQuery("from Druzyna where id_sekcja=:id");
        query.setParameter("id", idSection);
        List<Druzyna> teamList = query.getResultList();
        model.addAttribute("Section", idSection);
        model.addAttribute("teamList", teamList);
        model.addAttribute("Club", idClub);
        session.close();
        return "/team/show_team_view";

    }

    @GetMapping("/remove/{idTeam}")
    public ModelAndView removeTeam(Model model, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idTeam") String idTeam) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        Druzyna team = session.find(Druzyna.class, Integer.parseInt(idTeam));
        session.remove(team);
        t.commit();
        session.close();
        factory.close();
        return new ModelAndView("redirect:/club/" + idClub + "/sections/" + idSection + "/teams/");
    }

    @GetMapping("/create")
    public String createTeam(TeamForm teamForm, Model model, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection) {
        model.addAttribute("Section", idSection);
        model.addAttribute("Club", idClub);
        
         Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        //creating session object  
        Session session = factory.openSession();
        List<Liga> leagueList = session.createCriteria(Liga.class).list();
               
        model.addAttribute("leagueList", leagueList);

        session.close();
        return "/team/create_team_view";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createteam(@Valid TeamForm teamForm, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, Model model) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Sekcja section = session.find(Sekcja.class, Integer.parseInt(idSection));

        Druzyna team = new Druzyna();
        team.setNazwa(teamForm.getName());
        team.setIdSekcja(section);
        
         
        Query query = session.createQuery("from Liga where nazwa=:name");
        query.setParameter("name", teamForm.getLeague());
        List<Liga> leagueList = query.getResultList();
        if(!leagueList.isEmpty())
            team.setIdLiga(leagueList.get(0));

        session.persist(team);
        t.commit();
        session.close();
        factory.close();
        return new ModelAndView("redirect:/club/" + idClub + "/sections/" + idSection + "/teams/");
    }
    
    @GetMapping("/edit/{idTeam}")
    public String editTeam(TeamForm teamForm, Model model, @PathVariable("idTeam") String idTeam, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection) {
        model.addAttribute("Section", idSection);
        model.addAttribute("Club", idClub);
        model.addAttribute("Team", idTeam);
        return "/team/edit_team_view";
    }

    @RequestMapping(value = "/edit/{idTeam}", method = RequestMethod.POST)
    public ModelAndView editteam(@Valid TeamForm teamForm, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idTeam") String idTeam, Model model) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        Druzyna team = session.find(Druzyna.class, Integer.parseInt(idTeam));
        team.setNazwa(teamForm.getName());
        session.update(team);

        t.commit();
        session.close();
        return new ModelAndView("redirect:/club/" + idClub + "/sections/" + idSection + "/teams/");
    }

}
