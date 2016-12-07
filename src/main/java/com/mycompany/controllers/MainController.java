/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controllers;

import com.mycompany.model.Klub;
import java.security.Principal;
import java.util.List;
import javax.ws.rs.core.HttpHeaders;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author nowickik
 */
@Controller
public class MainController {

    @RequestMapping({"/home", "/"})
    public String homePage(Model model) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();
        List<Klub> listaKlubow = session.createCriteria(Klub.class).list();
        model.addAttribute("listaKlubow", listaKlubow);
        return "/home";
    }

}
