package com.mycompany.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class ZawodnikStatystykiPK implements Serializable {

    @NotNull
    @Column(name = "id_zawodnik")
    private int idZawodnik;
    
    @NotNull
    @Column(name = "id_sezon")
    private int idSezon;

    public ZawodnikStatystykiPK() {
    }

    public ZawodnikStatystykiPK(int idZawodnik, int idSezon) {
        this.idZawodnik = idZawodnik;
        this.idSezon = idSezon;
    }

    public int getIdZawodnik() {
        return idZawodnik;
    }

    public void setIdZawodnik(int idZawodnik) {
        this.idZawodnik = idZawodnik;
    }

    public int getIdSezon() {
        return idSezon;
    }

    public void setIdSezon(int idSezon) {
        this.idSezon = idSezon;
    }
    
}
