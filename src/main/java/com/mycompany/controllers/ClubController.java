/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controllers;

import com.mycompany.model.Klub;
import com.mysql.jdbc.Blob;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author user
 */
@Controller
public class ClubController {

    @RequestMapping(value = "/clubShow/{id}", method = RequestMethod.GET)
    public String clubPage(@PathVariable("id") String id) {

        return "/clubView";
    }

    @RequestMapping(value = "/clubImage/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] clubPhoto(@PathVariable("id") String id) throws SQLException {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();

        Klub klub = session.find(Klub.class, Integer.parseInt(id));

        return klub.getByteLogo();
    }

    @RequestMapping(value = "/clubCreate")
    public String clubCreate() {
        return "/clubCreate";
    }

    @RequestMapping(value = "/createClub", method = RequestMethod.POST)
    @ResponseBody
    public ModelAndView createClub(@RequestParam("name") String name, @RequestParam("file") MultipartFile file) throws IOException {

        if (!file.isEmpty()) {
            byte[] bytes;
            bytes = file.getBytes();
            Configuration cfg = new Configuration();
            cfg.configure("hibernate.cfg.xml");
            SessionFactory factory = cfg.buildSessionFactory();

            Session session = factory.openSession();
            Transaction t = session.beginTransaction();
            Klub klub = new Klub();
            klub.setNazwa(name);
            LobCreator lcreator = Hibernate.getLobCreator(session);
            Blob blob = (Blob) lcreator.createBlob(bytes);
            klub.setLogo(blob);
            session.persist(klub);
            t.commit();
            session.close();
            return new ModelAndView("redirect:/clubView");
        }
        System.out.println(name);
        return new ModelAndView("redirect:/clubsView");
    }
}
