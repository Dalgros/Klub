/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controllers;

import com.mycompany.forms.BuildingForm;
import com.mycompany.forms.SectionForm;
import com.mycompany.model.Budynek;
import com.mycompany.model.Klub;
import com.mycompany.model.Sekcja;
import java.util.ArrayList;
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
@RequestMapping("/club/{idClub}/sections")

public class SectionController {

    Logger log = Logger.getLogger(BuildingController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String sectionPage(@PathVariable("idClub") String idClub, Model model) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();
        Query query = session.createQuery("from Sekcja where Id_Klub=:id");
        query.setParameter("id", idClub);
        List<Sekcja> sectionList = query.getResultList();
        model.addAttribute("sectionList", sectionList);
        model.addAttribute("Club", idClub);
        session.close();
        return "/section/show_section_view";

    }

    @GetMapping("/remove/{idSection}")
    public ModelAndView removeSection(Model model, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        Sekcja section = session.find(Sekcja.class, Integer.parseInt(idSection));
        session.remove(section);
        t.commit();
        session.close();
        factory.close();
        return new ModelAndView("redirect:/club/" + idClub + "/sections/");
    }

    @GetMapping("/create")
    public String createSection(SectionForm sectionForm, Model model, @PathVariable("idClub") String idClub) {
        model.addAttribute("Club", idClub);
        return "/section/create_section_view";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createsection(@Valid SectionForm sectionForm, @PathVariable("idClub") String idClub, Model model) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Klub club = session.find(Klub.class, Integer.parseInt(idClub));
        Sekcja section = new Sekcja();
        section.setDyscyplina(sectionForm.getDiscipline());
        section.setPlec(sectionForm.getSex());
        section.setIdKlub(club);
        session.persist(section);
        t.commit();
        session.close();
        factory.close();
        return new ModelAndView("redirect:/club/" + idClub + "/sections/");
    }

    @RequestMapping(value = "/edit/{idSection}", method = RequestMethod.GET)
    public String sectionEdit(SectionForm sectionForm, @PathVariable("idSection") String idSection, Model model) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();
        Sekcja section = session.find(Sekcja.class, Integer.parseInt(idSection));
        model.addAttribute("section", section);
        session.close();
        factory.close();
        return "/section/edit_section_view";
    }

    @RequestMapping(value = "/edit/{idSection}", method = RequestMethod.POST)
    public ModelAndView sectionedit(@Valid SectionForm sectionForm, @PathVariable("idSection") String idSection, @PathVariable("idClub") String idClub, Model model) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();
        Sekcja section = session.find(Sekcja.class, Integer.parseInt(idSection));

        section.setDyscyplina(sectionForm.getDiscipline());
        section.setPlec(sectionForm.getSex());
        session.update(section);
        
        t.commit();

        session.close();
        factory.close();

        return new ModelAndView("redirect:/club/" + section.getIdKlub() + "/sections/");

    }
}
