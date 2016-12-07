package com.mycompany.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "budynek_administracyjny")
public class BudynekAdministracyjny implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Budynek")
    private Integer idBudynek;
    
    @JoinColumn(name = "Id_Budynek", referencedColumnName = "Id_budynek", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Budynek budynek;

    public BudynekAdministracyjny() {
    }

    public BudynekAdministracyjny(Integer idBudynek) {
        this.idBudynek = idBudynek;
    }

    public Integer getIdBudynek() {
        return idBudynek;
    }

    public Budynek getBudynek() {
        return budynek;
    }

    public void setBudynek(Budynek budynek) {
        this.budynek = budynek;
    }

}
