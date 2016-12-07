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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "sezon")
public class Sezon implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sezon")
    private Integer idSezon;
    
    @Column(name = "data")
    @Temporal(TemporalType.DATE)
    private Date data;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sezon")
    private Collection<DruzynaStatystyki> druzynaStatystykiCollection;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sezon")
    private Collection<ZawodnikStatystyki> zawodnikStatystykiCollection;

    public Sezon() {
    }

    public Sezon(Integer idSezon) {
        this.idSezon = idSezon;
    }

    public Integer getIdSezon() {
        return idSezon;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public Collection<DruzynaStatystyki> getDruzynaStatystykiCollection() {
        return druzynaStatystykiCollection;
    }

    public void setDruzynaStatystykiCollection(Collection<DruzynaStatystyki> druzynaStatystykiCollection) {
        this.druzynaStatystykiCollection = druzynaStatystykiCollection;
    }

    public Collection<ZawodnikStatystyki> getZawodnikStatystykiCollection() {
        return zawodnikStatystykiCollection;
    }

    public void setZawodnikStatystykiCollection(Collection<ZawodnikStatystyki> zawodnikStatystykiCollection) {
        this.zawodnikStatystykiCollection = zawodnikStatystykiCollection;
    }

}
