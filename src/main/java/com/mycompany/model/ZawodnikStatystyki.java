package com.mycompany.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "zawodnik_statystyki")
public class ZawodnikStatystyki implements Serializable {

    @EmbeddedId
    protected ZawodnikStatystykiPK zawodnikStatystykiPK;
    
    @Column(name = "strzelone_bramki")
    private Integer strzeloneBramki;
    
    @Column(name = "stracone_bramki")
    private Integer straconeBramki;
    
    @Column(name = "zolte_kartki")
    private Integer zolteKartki;
    
    @Column(name = "czerwone_kartki")
    private Integer czerwoneKartki;
    
    @Column(name = "faule")
    private Integer faule;
    
    @Column(name = "rozegrane_minuty")
    private Integer rozegraneMinuty;
    
    @JoinColumn(name = "id_zawodnik", referencedColumnName = "id_zawodnik", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Zawodnik zawodnik;
    
    @JoinColumn(name = "id_sezon", referencedColumnName = "id_sezon", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Sezon sezon;

    public ZawodnikStatystyki() {
    }

    public ZawodnikStatystyki(ZawodnikStatystykiPK zawodnikStatystykiPK) {
        this.zawodnikStatystykiPK = zawodnikStatystykiPK;
    }

    public ZawodnikStatystyki(int idZawodnik, int idSezon) {
        this.zawodnikStatystykiPK = new ZawodnikStatystykiPK(idZawodnik, idSezon);
    }

    public ZawodnikStatystykiPK getZawodnikStatystykiPK() {
        return zawodnikStatystykiPK;
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

    public Integer getZolteKartki() {
        return zolteKartki;
    }

    public void setZolteKartki(Integer zolteKartki) {
        this.zolteKartki = zolteKartki;
    }

    public Integer getCzerwoneKartki() {
        return czerwoneKartki;
    }

    public void setCzerwoneKartki(Integer czerwoneKartki) {
        this.czerwoneKartki = czerwoneKartki;
    }

    public Integer getFaule() {
        return faule;
    }

    public void setFaule(Integer faule) {
        this.faule = faule;
    }

    public Integer getRozegraneMinuty() {
        return rozegraneMinuty;
    }

    public void setRozegraneMinuty(Integer rozegraneMinuty) {
        this.rozegraneMinuty = rozegraneMinuty;
    }

    public Zawodnik getZawodnik() {
        return zawodnik;
    }

    public void setZawodnik(Zawodnik zawodnik) {
        this.zawodnik = zawodnik;
    }

    public Sezon getSezon() {
        return sezon;
    }

    public void setSezon(Sezon sezon) {
        this.sezon = sezon;
    }

    public void setZawodnikStatystykiPK(ZawodnikStatystykiPK zawodnikStatystykiPK) {
        this.zawodnikStatystykiPK = zawodnikStatystykiPK;
    }
    
}
