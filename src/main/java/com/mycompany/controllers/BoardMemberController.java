/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controllers;

import com.mycompany.forms.BoardMemberForm;
import com.mycompany.model.CzlonekZarzadu;
import com.mycompany.model.Klub;
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
@RequestMapping("/club/{idClub}/boardmembers")
public class BoardMemberController {

    Logger log = Logger.getLogger(BoardMemberController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String boardMemberPage(@PathVariable("idClub") String idClub, Model model) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();
        Query query = session.createQuery("from CzlonekZarzadu where Id_Klub=:id");
        query.setParameter("id", idClub);
        List<CzlonekZarzadu> membersList = query.getResultList();

        session.close();
        model.addAttribute("club", idClub);
        model.addAttribute("memberList", membersList);

        return "/boardmember/show_boardmember_view";
    }

    @GetMapping("/create")
    public String createMember(BoardMemberForm boardMemberForm, Model model, @PathVariable("idClub") String idClub) {
        model.addAttribute("club", idClub);
        return "/boardmember/create_boardmember_view";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createmember(@Valid BoardMemberForm boardMember, @PathVariable("idClub") String idClub, Model model) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();

        Transaction t = session.beginTransaction();

        Klub klub = session.find(Klub.class, Integer.parseInt(idClub));
        
        CzlonekZarzadu member = new CzlonekZarzadu();
        member.setImie(boardMember.getFirstName());
        member.setNazwisko(boardMember.getLastName());
        member.setStanowisko(boardMember.getPosition());
        member.setPensja(boardMember.getSalary());
        member.setProcentUdzialow(Float.parseFloat(String.valueOf(boardMember.getPercent())));
        member.setIdKlub(klub);
        
        session.persist(member);
        t.commit();

        session.close();
        factory.close();
        
        model.addAttribute("club", idClub);

        return new ModelAndView("redirect:/club/" + idClub + "/boardmembers/");

    }
    
    @GetMapping("/edit/{idMember}")
    public String editBoardMember(BoardMemberForm boardMember, Model model, @PathVariable("idClub") String idClub, @PathVariable("idMember") String idMember) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();

        CzlonekZarzadu member = session.find(CzlonekZarzadu.class, Integer.parseInt(idMember));

        model.addAttribute("club", idClub);
        model.addAttribute("member", member);
        
        session.close();
        factory.close();
        return "/boardmember/edit_boardmember_view";
    }

    @PostMapping("/edit/{idMember}")
    @ResponseBody
    public ModelAndView editBoardMember(@Valid BoardMemberForm boardMember, BindingResult result, Model model, @PathVariable("idClub") String idClub, @PathVariable("idMember") String idMember) throws IOException {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/club/" + idClub + "/boardmembers/edit/" + idMember);
        }

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        CzlonekZarzadu member = session.find(CzlonekZarzadu.class, Integer.parseInt(idMember));
        member.setImie(boardMember.getFirstName());
        member.setNazwisko(boardMember.getLastName());
        member.setStanowisko(boardMember.getPosition());
        member.setPensja(boardMember.getSalary());
        member.setProcentUdzialow(Float.parseFloat(String.valueOf(boardMember.getPercent())));

        session.update(member);
        t.commit();
        session.close();
        factory.close();
        
        return new ModelAndView("redirect:/club/" + idClub + "/boardmembers/");

    }
    
    @GetMapping("/remove/{idMember}")
    public ModelAndView removeBoardMember(Model model, @PathVariable("idClub") String idClub,@PathVariable("idMember") String idMember) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();

        Transaction t = session.beginTransaction();
        
        CzlonekZarzadu member = session.find(CzlonekZarzadu.class, Integer.parseInt(idMember) );
        session.remove(member);
        t.commit();
        
        session.close();
        factory.close();
        
        return new ModelAndView("redirect:/club/" + idClub + "/boardmembers/");
    }
}
