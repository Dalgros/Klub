package com.mycompany.model;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name = "klub")
public class Klub implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Klub")
    private Integer idKlub;
	
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nazwa")
    private String nazwa;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKlub")
    private Collection<CzlonekZarzadu> czlonekZarzaduCollection;
	
    @OneToMany(mappedBy = "idKlub")
    private Collection<Budynek> budynekCollection;
	
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idKlub")
    private Collection<Sekcja> sekcjaCollection;

    public Klub() {
    }

    public Klub(Integer idKlub) {
        this.idKlub = idKlub;
    }

    public Klub(Integer idKlub, String nazwa) {
        this.idKlub = idKlub;
        this.nazwa = nazwa;
    }

    public Integer getIdKlub() {
        return idKlub;
    }

    public void setIdKlub(Integer idKlub) {
        this.idKlub = idKlub;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Collection<CzlonekZarzadu> getCzlonekZarzaduCollection() {
        return czlonekZarzaduCollection;
    }

    public void setCzlonekZarzaduCollection(Collection<CzlonekZarzadu> czlonekZarzaduCollection) {
        this.czlonekZarzaduCollection = czlonekZarzaduCollection;
    }

    public Collection<Budynek> getBudynekCollection() {
        return budynekCollection;
    }

    public void setBudynekCollection(Collection<Budynek> budynekCollection) {
        this.budynekCollection = budynekCollection;
    }

    public Collection<Sekcja> getSekcjaCollection() {
        return sekcjaCollection;
    }

    public void setSekcjaCollection(Collection<Sekcja> sekcjaCollection) {
        this.sekcjaCollection = sekcjaCollection;
    }
    
}
