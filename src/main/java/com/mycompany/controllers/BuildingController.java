/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.controllers;

import com.mycompany.model.Budynek;
import com.mycompany.model.Klub;
import java.util.ArrayList;
import java.util.List;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 *
 * @author user
 */
@Controller
@RequestMapping("/club/{id}/buildings")
public class BuildingController {

    Logger log = Logger.getLogger(BuildingController.class);

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String buildingPage(@PathVariable("id") String id, Model model) {

        Configuration cfg = new Configuration();
        cfg.configure("hibernate.cfg.xml");
        SessionFactory factory = cfg.buildSessionFactory();

        //creating session object  
        Session session = factory.openSession();
        Query query = session.createQuery("from Budynek where Id_Klub=:id");
        query.setParameter("id", id);
        List<Budynek> buildingsList = query.getResultList();
        List buildingAdminList=new ArrayList();
        List arenaList=new ArrayList();
        List trainingObjectList=new ArrayList();
        
        for (Budynek b : buildingsList) {
            
            if (b.getBudynekAdministracyjny() != null) {
                buildingAdminList.add(b.getBudynekAdministracyjny());
            }
            if (b.getStadion()!=null) {
                arenaList.add(b.getStadion());
                log.info(b.getStadion().getNazwa());
            }
             if (b.getObiektTreningowy()!=null) {
                trainingObjectList.add(b.getObiektTreningowy());
            }


        }
        session.close();
        model.addAttribute("buildingAdminList",buildingAdminList);
        model.addAttribute("arenaList",arenaList);
        model.addAttribute("buildings",buildingsList);
        model.addAttribute("trainingObjectList",trainingObjectList);
        return "/building/show_building_view";
    }
}
