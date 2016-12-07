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

@Entity
@Table(name = "kontrakt")
public class Kontrakt implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_kontrakt")
    private Integer idKontrakt;
    
    @Column(name = "poczatek_kontraktu")
    @Temporal(TemporalType.DATE)
    private Date poczatekKontraktu;
    
    @Column(name = "koniec_kontraktu")
    @Temporal(TemporalType.DATE)
    private Date koniecKontraktu;
    
    @Column(name = "pensja")
    private Integer pensja;
    
    @Column(name = "wartosc_rynkowa")
    private Integer wartoscRynkowa;
    
    @JoinColumn(name = "id_zawodnik", referencedColumnName = "id_zawodnik")
    @ManyToOne(optional = false)
    private Zawodnik idZawodnik;

    public Kontrakt() {
    }

    public Kontrakt(Integer idKontrakt) {
        this.idKontrakt = idKontrakt;
    }

    public Integer getIdKontrakt() {
        return idKontrakt;
    }

    public Date getPoczatekKontraktu() {
        return poczatekKontraktu;
    }

    public void setPoczatekKontraktu(Date poczatekKontraktu) {
        this.poczatekKontraktu = poczatekKontraktu;
    }

    public Date getKoniecKontraktu() {
        return koniecKontraktu;
    }

    public void setKoniecKontraktu(Date koniecKontraktu) {
        this.koniecKontraktu = koniecKontraktu;
    }

    public Integer getPensja() {
        return pensja;
    }

    public void setPensja(Integer pensja) {
        this.pensja = pensja;
    }

    public Integer getWartoscRynkowa() {
        return wartoscRynkowa;
    }

    public void setWartoscRynkowa(Integer wartoscRynkowa) {
        this.wartoscRynkowa = wartoscRynkowa;
    }

    public Zawodnik getIdZawodnik() {
        return idZawodnik;
    }

    public void setIdZawodnik(Zawodnik idZawodnik) {
        this.idZawodnik = idZawodnik;
    }
    
}
