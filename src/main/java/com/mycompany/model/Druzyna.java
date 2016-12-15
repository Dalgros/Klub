package com.mycompany.model;

import java.io.Serializable;
import java.util.Collection;
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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlTransient;

@Entity
@Table(name = "druzyna")
public class Druzyna implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_druzyna")
    private Integer idDruzyna;

    @Size(max = 20)
    @Column(name = "nazwa")
    private String nazwa;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idDruzyna")
    private Collection<Zawodnik> zawodnikCollection;

    @OneToMany(mappedBy = "idDruzyna")
    private Collection<CzlonekSztabu> czlonekSztabuCollection;

    @JoinColumn(name = "id_sekcja", referencedColumnName = "id_sekcja")
    @ManyToOne(optional = false)
    private Sekcja idSekcja;

    @JoinColumn(name = "id_liga", referencedColumnName = "Id_Liga")
    @ManyToOne(optional = true)
    private Liga idLiga;

    public Druzyna() {
    }

    public Druzyna(Integer idDruzyna) {
        this.idDruzyna = idDruzyna;
    }

    public Integer getIdDruzyna() {
        return idDruzyna;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    @XmlTransient
    public Collection<Zawodnik> getZawodnikCollection() {
        return zawodnikCollection;
    }

    public void setZawodnikCollection(Collection<Zawodnik> zawodnikCollection) {
        this.zawodnikCollection = zawodnikCollection;
    }

    @XmlTransient
    public Collection<CzlonekSztabu> getCzlonekSztabuCollection() {
        return czlonekSztabuCollection;
    }

    public void setCzlonekSztabuCollection(Collection<CzlonekSztabu> czlonekSztabuCollection) {
        this.czlonekSztabuCollection = czlonekSztabuCollection;
    }

    public Sekcja getIdSekcja() {
        return idSekcja;
    }

    public void setIdSekcja(Sekcja idSekcja) {
        this.idSekcja = idSekcja;
    }

    public Liga getIdLiga() {
        return idLiga;
    }

    public void setIdLiga(Liga idLiga) {
        this.idLiga = idLiga;
    }

}