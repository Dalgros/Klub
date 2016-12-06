package com.mycompany.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "budynek")
public class Budynek implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_budynek")
    private Integer idbudynek;
    
    @Size(max = 6)
    @Column(name = "kod_pocztowy")
    private String kodPocztowy;
    
    @Size(max = 20)
    @Column(name = "miejscowosc")
    private String miejscowosc;
    
    @Size(max = 20)
    @Column(name = "ulicanumer")
    private String ulicanumer;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "budynek")
    private Stadion stadion;
    
    @JoinColumn(name = "id_klub", referencedColumnName = "Id_Klub")
    @ManyToOne
    private Klub idKlub;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "budynek")
    private BudynekAdministracyjny budynekAdministracyjny;
    
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "budynek")
    private ObiektTreningowy obiektTreningowy;

    public Budynek() {
    }

    public Budynek(Integer idbudynek) {
        this.idbudynek = idbudynek;
    }

    public Integer getIdbudynek() {
        return idbudynek;
    }

    public void setIdbudynek(Integer idbudynek) {
        this.idbudynek = idbudynek;
    }

    public String getKodPocztowy() {
        return kodPocztowy;
    }

    public void setKodPocztowy(String kodPocztowy) {
        this.kodPocztowy = kodPocztowy;
    }

    public String getMiejscowosc() {
        return miejscowosc;
    }

    public void setMiejscowosc(String miejscowosc) {
        this.miejscowosc = miejscowosc;
    }

    public String getUlicanumer() {
        return ulicanumer;
    }

    public void setUlicanumer(String ulicanumer) {
        this.ulicanumer = ulicanumer;
    }

    public Stadion getStadion() {
        return stadion;
    }

    public void setStadion(Stadion stadion) {
        this.stadion = stadion;
    }

    public Klub getIdKlub() {
        return idKlub;
    }

    public void setIdKlub(Klub idKlub) {
        this.idKlub = idKlub;
    }

    public BudynekAdministracyjny getBudynekAdministracyjny() {
        return budynekAdministracyjny;
    }

    public void setBudynekAdministracyjny(BudynekAdministracyjny budynekAdministracyjny) {
        this.budynekAdministracyjny = budynekAdministracyjny;
    }

    public ObiektTreningowy getObiektTreningowy() {
        return obiektTreningowy;
    }

    public void setObiektTreningowy(ObiektTreningowy obiektTreningowy) {
        this.obiektTreningowy = obiektTreningowy;
    }

}
