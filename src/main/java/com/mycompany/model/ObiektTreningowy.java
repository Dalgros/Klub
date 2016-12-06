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
import javax.validation.constraints.Size;

@Entity
@Table(name = "obiekt_treningowy")
public class ObiektTreningowy implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Budynek")
    private Integer idBudynek;
    
    @Size(max = 20)
    @Column(name = "dyscyplina")
    private String dyscyplina;
    
    @JoinColumn(name = "Id_Budynek", referencedColumnName = "Id_budynek", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Budynek budynek;

    public ObiektTreningowy() {
    }

    public ObiektTreningowy(Integer idBudynek) {
        this.idBudynek = idBudynek;
    }

    public Integer getIdBudynek() {
        return idBudynek;
    }

    public void setIdBudynek(Integer idBudynek) {
        this.idBudynek = idBudynek;
    }

    public String getDyscyplina() {
        return dyscyplina;
    }

    public void setDyscyplina(String dyscyplina) {
        this.dyscyplina = dyscyplina;
    }

    public Budynek getBudynek() {
        return budynek;
    }

    public void setBudynek(Budynek budynek) {
        this.budynek = budynek;
    }

}
