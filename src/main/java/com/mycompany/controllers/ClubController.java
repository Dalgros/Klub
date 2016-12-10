/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controllers;

import com.mycompany.forms.ClubForm;
import com.mycompany.model.Klub;
import com.mysql.jdbc.Blob;
import java.awt.Color;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.sql.SQLException;
import java.util.List;
import javax.ejb.ApplicationException;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.apache.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.engine.jdbc.LobCreator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
@RequestMapping("/club")
public class ClubController {

    Logger log = Logger.getLogger(ClubController.class);

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String clubPage(@PathVariable("id") String id, Model model) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();

        Klub club = session.find(Klub.class, Integer.parseInt(id));
        model.addAttribute("club", club);

        return "/club/show_club_view";
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET)
    @ResponseBody
    public byte[] clubPhoto(@PathVariable("id") String id) throws SQLException {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();

        Klub club = session.find(Klub.class, Integer.parseInt(id));

        return club.getByteLogo();
    }

    @GetMapping("/create")
    public String createClub(ClubForm clubForm) {
        return "/club/create_club_view";
    }

//    @RequestMapping(value = "/create", method = RequestMethod.POST)
//    @ResponseBody
//    public ModelAndView createClub(@RequestParam("name") String name, @RequestParam("file") MultipartFile file, Model model) throws IOException {
    @PostMapping("/create")
    @ResponseBody
    public ModelAndView createClub(@Valid ClubForm clubForm, BindingResult result, Model model) throws IOException {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/club/create");
        }

        byte[] bytes;
        bytes = clubForm.getLogo().getBytes();
        int width = 200;
        int height = 200;

        ByteArrayInputStream in = new ByteArrayInputStream(bytes);
        try {
            BufferedImage img = ImageIO.read(in);
            if (height == 0) {
                height = (width * img.getHeight()) / img.getWidth();
            }
            if (width == 0) {
                width = (height * img.getWidth()) / img.getHeight();
            }
            Image scaledImage = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage imageBuff = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            imageBuff.getGraphics().drawImage(scaledImage, 0, 0, new Color(0, 0, 0), null);

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            ImageIO.write(imageBuff, "jpg", buffer);

            bytes = buffer.toByteArray();
        } catch (IOException e) {
            log.error("File convertion error");
        }

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        Klub club = new Klub();
        club.setNazwa(clubForm.getName());

        LobCreator lcreator = Hibernate.getLobCreator(session);
        Blob blob = (Blob) lcreator.createBlob(bytes);
        club.setLogo(blob);

        session.persist(club);
        t.commit();
        session.close();

        model.addAttribute("club", club);
        return new ModelAndView("redirect:/club/" + club.getIdKlub());

    }

    private static class ApplicationExceptionImpl implements ApplicationException {

        public ApplicationExceptionImpl() {
        }

        @Override
        public boolean rollback() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public boolean inherited() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

        @Override
        public Class<? extends Annotation> annotationType() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
}
