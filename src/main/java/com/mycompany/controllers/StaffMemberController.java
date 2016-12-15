/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controllers;

import com.mycompany.forms.BoardMemberForm;
import com.mycompany.forms.StaffMemberForm;
import com.mycompany.model.CzlonekSztabu;
import com.mycompany.model.CzlonekZarzadu;
import com.mycompany.model.Druzyna;
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
@RequestMapping("/club/{idClub}/sections/{idSection}/teams/{idTeam}/staffmembers")
public class StaffMemberController {

    Logger log = Logger.getLogger(BoardMemberController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String staffMemberPage(@PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idTeam") String idTeam, Model model) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();
        Query query = session.createQuery("from CzlonekSztabu where id_druzyna=:id");
        query.setParameter("id", idTeam);
        List<CzlonekSztabu> membersList = query.getResultList();

        session.close();
        model.addAttribute("club", idClub);
        model.addAttribute("Section", idSection);
        model.addAttribute("Team", idTeam);
        model.addAttribute("memberList", membersList);

        return "/staffmember/show_staffmember_view";
    }

    @GetMapping("/create")
    public String createMember(StaffMemberForm staffMemberForm, Model model, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idTeam") String idTeam) {
        model.addAttribute("club", idClub);
        model.addAttribute("Section", idSection);
        model.addAttribute("Team", idTeam);
        return "/staffmember/create_staffmember_view";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public ModelAndView createmember(@Valid StaffMemberForm staffMemberForm, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idTeam") String idTeam, Model model) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();

        Transaction t = session.beginTransaction();

        Druzyna druzyna = session.find(Druzyna.class, Integer.parseInt(idTeam));
        
        CzlonekSztabu member = new CzlonekSztabu();
        member.setImie(staffMemberForm.getFirstName());
        member.setNazwisko(staffMemberForm.getLastName());
        member.setStanowisko(staffMemberForm.getPosition());
        member.setPensja(staffMemberForm.getSalary());
        member.setIdDruzyna(druzyna);
        
        session.persist(member);
        t.commit();

        session.close();
        factory.close();
        
        model.addAttribute("club", idClub);
        model.addAttribute("Section", idSection);
        model.addAttribute("Team", idTeam);
        return new ModelAndView("redirect:/club/" + idClub + "/sections/" + idSection + "/teams/" + idTeam + "/staffmembers/");

    }
    
    @GetMapping("/edit/{idMember}")
    public String editStaffMember(StaffMemberForm staffMemberForm, Model model, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idTeam") String idTeam, @PathVariable("idMember") String idMember) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();

        CzlonekSztabu member = session.find(CzlonekSztabu.class, Integer.parseInt(idMember));

        model.addAttribute("club", idClub);
        model.addAttribute("Section", idSection);
        model.addAttribute("Team", idTeam);
        model.addAttribute("member", member);
        
        session.close();
        factory.close();
        return "/staffmember/edit_staffmember_view";
    }

    @PostMapping("/edit/{idMember}")
    @ResponseBody
    public ModelAndView editStaffMember(@Valid StaffMemberForm staffMemberForm, BindingResult result, Model model, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idTeam") String idTeam, @PathVariable("idMember") String idMember) throws IOException {
        if (result.hasErrors()) {
            return new ModelAndView("redirect:/club/" + idClub + "/sections/" + idSection + "/teams/" + idTeam + "/staffmembers/edit/" + idMember);
        }

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();
        Session session = factory.openSession();
        Transaction t = session.beginTransaction();

        CzlonekSztabu member = session.find(CzlonekSztabu.class, Integer.parseInt(idMember));
        member.setImie(staffMemberForm.getFirstName());
        member.setNazwisko(staffMemberForm.getLastName());
        member.setStanowisko(staffMemberForm.getPosition());
        member.setPensja(staffMemberForm.getSalary());

        session.update(member);
        t.commit();
        session.close();
        factory.close();
        
        return new ModelAndView("redirect:/club/" + idClub + "/sections/" + idSection + "/teams/" + idTeam + "/staffmembers/");

    }
    
    @GetMapping("/remove/{idMember}")
    public ModelAndView removeStaffMember(Model model, @PathVariable("idClub") String idClub, @PathVariable("idSection") String idSection, @PathVariable("idTeam") String idTeam,@PathVariable("idMember") String idMember) {
        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();

        Transaction t = session.beginTransaction();
        
        CzlonekSztabu member = session.find(CzlonekSztabu.class, Integer.parseInt(idMember) );
        session.remove(member);
        t.commit();
        
        session.close();
        factory.close();
        
        return new ModelAndView("redirect:/club/" + idClub + "/sections/" + idSection + "/teams/" + idTeam + "/staffmembers/");
    }
}
