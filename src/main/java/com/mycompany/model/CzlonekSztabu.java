package com.mycompany.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "czlonek_sztabu")
public class CzlonekSztabu implements Serializable {

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
    
    @JoinColumn(name = "id_druzyna", referencedColumnName = "id_druzyna")
    @ManyToOne
    private Druzyna idDruzyna;

    public CzlonekSztabu() {
    }

    public CzlonekSztabu(Integer idCzlonek) {
        this.idCzlonek = idCzlonek;
    }

    public Integer getIdCzlonek() {
        return idCzlonek;
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

    public Druzyna getIdDruzyna() {
        return idDruzyna;
    }

    public void setIdDruzyna(Druzyna idDruzyna) {
        this.idDruzyna = idDruzyna;
    }
    
}
