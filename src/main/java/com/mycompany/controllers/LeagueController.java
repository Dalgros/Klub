/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controllers;

import com.mycompany.forms.BoardMemberForm;
import com.mycompany.forms.LeagueForm;
import com.mycompany.model.Budynek;
import com.mycompany.model.CzlonekZarzadu;
import com.mycompany.model.Druzyna;
import com.mycompany.model.Klub;
import com.mycompany.model.Liga;
import com.mycompany.model.Sekcja;
import java.io.IOException;
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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author nowickik
 */
@Controller
@RequestMapping("/leagues")
public class LeagueController {

    Logger log = Logger.getLogger(BoardMemberController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String leaguePage( Model model) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();
        List<Liga> leagueList = session.createCriteria(Liga.class).list();
               
        model.addAttribute("leagueList", leagueList);

        session.close();

        return "/league/show_leagues_view";
    }
    
    @RequestMapping(value = "/show/{leagueId}", method = RequestMethod.GET)
    public String clubPage(@PathVariable("leagueId") String leagueId, Model model) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();

        Liga league = session.find(Liga.class, Integer.parseInt(leagueId));
        model.addAttribute("league", league);
        
        Query query = session.createQuery("from Druzyna where id_liga=:id");
        query.setParameter("id", league.getIdLiga());
        List<Druzyna> teamList = query.getResultList();
        model.addAttribute("teamList", teamList);

        session.close();
        factory.close();

        return "/league/show_concrete_league_view";
    }

    @GetMapping("/create")
    public String createLeague(LeagueForm leagueForm, Model model) {
        return "/league/create_league_view";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createleague(@Valid LeagueForm leagueForm, Model model) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();

        Transaction t = session.beginTransaction();
        
        Liga league = new Liga();
        league.setNazwa(leagueForm.getName());
        league.setKraj(leagueForm.getCountry());
        
        session.persist(league);
        t.commit();

        session.close();
        factory.close();

        return new ModelAndView("redirect:/leagues/");

    }
    
    @GetMapping("/edit/{idLeague}")
    public String editLeague(LeagueForm leagueForm, Model model, @PathVariable("idLeague") String idLeague) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();

        Liga league = session.find(Liga.class, Integer.parseInt(idLeague));

        model.addAttribute("league", league);
        
        session.close();
        factory.close();
        return "/league/edit_league_view";
    }

    @PostMapping("/edit/{idLeague}")
    @ResponseBody
    public ModelAndView editLeague(@Valid LeagueForm leagueForm, BindingResult result, Model model, @PathVariable("idLeague") String idLeague) throws IOException {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/league/edit/" + idLeague);
        }

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        Liga league = session.find(Liga.class, Integer.parseInt(idLeague));
        league.setNazwa(leagueForm.getName());
        league.setKraj(leagueForm.getCountry());

        session.update(league);
        t.commit();
        session.close();
        factory.close();
        
        return new ModelAndView("redirect:/leagues/");

    }
    
    @GetMapping("/remove/{idLeague}")
    public ModelAndView removeLeague(Model model, @PathVariable("idLeague") String idLeague) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();

        Transaction t = session.beginTransaction();
        
        Liga league = session.find(Liga.class, Integer.parseInt(idLeague));
        session.remove(league);
        t.commit();
        
        session.close();
        factory.close();
        
        return new ModelAndView("redirect:/leagues/");
    }
}
