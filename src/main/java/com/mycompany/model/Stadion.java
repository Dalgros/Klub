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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "stadion")
public class Stadion implements Serializable {

    @Id
    @Column(name = "Id_Budynek")
    private Integer idBudynek;
    
    @NotNull
    @Column(name = "ilosc_miejsc")
    private int iloscMiejsc;
    
    @Size(max = 20)
    @Column(name = "nazwa")
    private String nazwa;
    
    @Size(max = 20)
    @Column(name = "dyscyplina")
    private String dyscyplina;
    
    @JoinColumn(name = "Id_Budynek", referencedColumnName = "Id_budynek", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Budynek budynek;

    public Stadion() {
    }

    public Stadion(Integer idBudynek) {
        this.idBudynek = idBudynek;
    }

    public Stadion(Integer idBudynek, int iloscMiejsc) {
        this.idBudynek = idBudynek;
        this.iloscMiejsc = iloscMiejsc;
    }

    public Integer getIdBudynek() {
        return idBudynek;
    }

    public int getIloscMiejsc() {
        return iloscMiejsc;
    }

    public void setIloscMiejsc(int iloscMiejsc) {
        this.iloscMiejsc = iloscMiejsc;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
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
