package com.mycompany.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;

@Entity
@Table(name = "zawodnik")
public class Zawodnik implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_zawodnik")
    private Integer idZawodnik;
    
    @Size(max = 20)
    @Column(name = "imie")
    private String imie;
    
    @Size(max = 50)
    @Column(name = "nazwisko")
    private String nazwisko;
    
    @Column(name = "data_urodzenia")
    @Temporal(TemporalType.DATE)
    private Date dataUrodzenia;
    
    @Column(name = "wzrost")
    private Integer wzrost;
    
    @Column(name = "waga")
    private Integer waga;
    
    @JoinColumn(name = "id_druzyna", referencedColumnName = "id_druzyna")
    @ManyToOne(optional = false)
    private Druzyna idDruzyna;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "zawodnik")
    private Collection<ZawodnikStatystyki> zawodnikStatystykiCollection;
    

    public Zawodnik() {
    }

    public Zawodnik(Integer idZawodnik) {
        this.idZawodnik = idZawodnik;
    }

    public Integer getIdZawodnik() {
        return idZawodnik;
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

    public Date getDataUrodzenia() {
        return dataUrodzenia;
    }

    public void setDataUrodzenia(Date dataUrodzenia) {
        this.dataUrodzenia = dataUrodzenia;
    }

    public Integer getWzrost() {
        return wzrost;
    }

    public void setWzrost(Integer wzrost) {
        this.wzrost = wzrost;
    }

    public Integer getWaga() {
        return waga;
    }

    public void setWaga(Integer waga) {
        this.waga = waga;
    }

    public Druzyna getIdDruzyna() {
        return idDruzyna;
    }

    public void setIdDruzyna(Druzyna idDruzyna) {
        this.idDruzyna = idDruzyna;
    }

    public Collection<ZawodnikStatystyki> getZawodnikStatystykiCollection() {
        return zawodnikStatystykiCollection;
    }

    public void setZawodnikStatystykiCollection(Collection<ZawodnikStatystyki> zawodnikStatystykiCollection) {
        this.zawodnikStatystykiCollection = zawodnikStatystykiCollection;
    }


}
