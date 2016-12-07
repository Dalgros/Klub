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
@Table(name = "liga")
public class Liga implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id_Liga")
    private Integer idLiga;
    
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "nazwa")
    private String nazwa;
    
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "kraj")
    private String kraj;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idLiga")
    private Collection<Druzyna> druzynaCollection;

    public Liga() {
    }

    public Liga(Integer idLiga) {
        this.idLiga = idLiga;
    }

    public Liga(Integer idLiga, String nazwa, String kraj) {
        this.idLiga = idLiga;
        this.nazwa = nazwa;
        this.kraj = kraj;
    }

    public Integer getIdLiga() {
        return idLiga;
    }
    
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getKraj() {
        return kraj;
    }

    public void setKraj(String kraj) {
        this.kraj = kraj;
    }

    public Collection<Druzyna> getDruzynaCollection() {
        return druzynaCollection;
    }

    public void setDruzynaCollection(Collection<Druzyna> druzynaCollection) {
        this.druzynaCollection = druzynaCollection;
    }

}
