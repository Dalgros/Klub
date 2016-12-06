package com.mycompany.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "druzyna_statystyki")
public class DruzynaStatystyki implements Serializable {

    @EmbeddedId
    protected DruzynaStatystykiPK druzynaStatystykiPK;
    
    @Column(name = "punkty")
    private Integer punkty;
    
    @Column(name = "strzelone_bramki")
    private Integer strzeloneBramki;
    
    @Column(name = "stracone_bramki")
    private Integer straconeBramki;
    
    @Column(name = "rozegrane_mecze")
    private Integer rozegraneMecze;
    
    @Column(name = "wygrane_mecze")
    private Integer wygraneMecze;
    
    @Column(name = "przegrane_mecze")
    private Integer przegraneMecze;
    
    @Column(name = "remisy")
    private Integer remisy;
    
    @JoinColumn(name = "id_druzyna", referencedColumnName = "id_druzyna", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Druzyna druzyna;
    
    @JoinColumn(name = "id_sezon", referencedColumnName = "id_sezon", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sezon sezon;

    public DruzynaStatystyki() {
    }

    public DruzynaStatystyki(DruzynaStatystykiPK druzynaStatystykiPK) {
        this.druzynaStatystykiPK = druzynaStatystykiPK;
    }

    public DruzynaStatystyki(int idDruzyna, int idSezon) {
        this.druzynaStatystykiPK = new DruzynaStatystykiPK(idDruzyna, idSezon);
    }

    public DruzynaStatystykiPK getDruzynaStatystykiPK() {
        return druzynaStatystykiPK;
    }

    public void setDruzynaStatystykiPK(DruzynaStatystykiPK druzynaStatystykiPK) {
        this.druzynaStatystykiPK = druzynaStatystykiPK;
    }

    public Integer getPunkty() {
        return punkty;
    }

    public void setPunkty(Integer punkty) {
        this.punkty = punkty;
    }

    public Integer getStrzeloneBramki() {
        return strzeloneBramki;
    }

    public void setStrzeloneBramki(Integer strzeloneBramki) {
        this.strzeloneBramki = strzeloneBramki;
    }

    public Integer getStraconeBramki() {
        return straconeBramki;
    }

    public void setStraconeBramki(Integer straconeBramki) {
        this.straconeBramki = straconeBramki;
    }

    public Integer getRozegraneMecze() {
        return rozegraneMecze;
    }

    public void setRozegraneMecze(Integer rozegraneMecze) {
        this.rozegraneMecze = rozegraneMecze;
    }

    public Integer getWygraneMecze() {
        return wygraneMecze;
    }

    public void setWygraneMecze(Integer wygraneMecze) {
        this.wygraneMecze = wygraneMecze;
    }

    public Integer getPrzegraneMecze() {
        return przegraneMecze;
    }

    public void setPrzegraneMecze(Integer przegraneMecze) {
        this.przegraneMecze = przegraneMecze;
    }

    public Integer getRemisy() {
        return remisy;
    }

    public void setRemisy(Integer remisy) {
        this.remisy = remisy;
    }

    public Druzyna getDruzyna() {
        return druzyna;
    }

    public void setDruzyna(Druzyna druzyna) {
        this.druzyna = druzyna;
    }

    public Sezon getSezon() {
        return sezon;
    }

    public void setSezon(Sezon sezon) {
        this.sezon = sezon;
    }
    
}
