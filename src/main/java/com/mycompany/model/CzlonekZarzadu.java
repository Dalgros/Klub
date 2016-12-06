package com.mycompany.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

@Entity
@Table(name = "czlonek_zarzadu")
public class CzlonekZarzadu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_czlonek")
    private Integer idCzlonek;
    
    @Size(max = 20)
    @Column(name = "imie")
    private String imie;
    
    @Size(max = 50)
    @Column(name = "nazwisko")
    private String nazwisko;
    
    @Size(max = 20)
    @Column(name = "stanowisko")
    private String stanowisko;
    
    @Column(name = "pensja")
    private Integer pensja;
    
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "procent_udzialow")
    private Float procentUdzialow;
    
    @JoinColumn(name = "id_klub", referencedColumnName = "Id_Klub")
    @ManyToOne(optional = false)
    private Klub idKlub;

    public CzlonekZarzadu() {
    }

    public CzlonekZarzadu(Integer idCzlonek) {
        this.idCzlonek = idCzlonek;
    }

    public Integer getIdCzlonek() {
        return idCzlonek;
    }

    public void setIdCzlonek(Integer idCzlonek) {
        this.idCzlonek = idCzlonek;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getStanowisko() {
        return stanowisko;
    }

    public void setStanowisko(String stanowisko) {
        this.stanowisko = stanowisko;
    }

    public Integer getPensja() {
        return pensja;
    }

    public void setPensja(Integer pensja) {
        this.pensja = pensja;
    }

    public Float getProcentUdzialow() {
        return procentUdzialow;
    }

    public void setProcentUdzialow(Float procentUdzialow) {
        this.procentUdzialow = procentUdzialow;
    }

    public Klub getIdKlub() {
        return idKlub;
    }

    public void setIdKlub(Klub idKlub) {
        this.idKlub = idKlub;
    }
    
}
