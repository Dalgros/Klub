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

@Entity
@Table(name = "sekcja")
public class Sekcja implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_sekcja")
    private Integer idSekcja;
    
    @Size(max = 20)
    @Column(name = "dyscyplina")
    private String dyscyplina;
    
    @Size(max = 1)
    @Column(name = "plec")
    private String plec;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idSekcja")
    private Collection<Druzyna> druzynaCollection;
    
    @JoinColumn(name = "id_klub", referencedColumnName = "Id_Klub")
    @ManyToOne(optional = false)
    private Klub idKlub;

    public Sekcja() {
    }

    public Sekcja(Integer idSekcja) {
        this.idSekcja = idSekcja;
    }

    public Integer getIdSekcja() {
        return idSekcja;
    }

    public void setIdSekcja(Integer idSekcja) {
        this.idSekcja = idSekcja;
    }

    public String getDyscyplina() {
        return dyscyplina;
    }

    public void setDyscyplina(String dyscyplina) {
        this.dyscyplina = dyscyplina;
    }

    public String getPlec() {
        return plec;
    }

    public void setPlec(String plec) {
        this.plec = plec;
    }

    public Collection<Druzyna> getDruzynaCollection() {
        return druzynaCollection;
    }

    public void setDruzynaCollection(Collection<Druzyna> druzynaCollection) {
        this.druzynaCollection = druzynaCollection;
    }

    public Klub getIdKlub() {
        return idKlub;
    }

    public void setIdKlub(Klub idKlub) {
        this.idKlub = idKlub;
    }
    
}
