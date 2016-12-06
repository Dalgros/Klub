package com.mycompany.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

@Embeddable
public class DruzynaStatystykiPK implements Serializable {

    @NotNull
    @Column(name = "id_druzyna")
    private int idDruzyna;
    
    @NotNull
    @Column(name = "id_sezon")
    private int idSezon;

    public DruzynaStatystykiPK() {
    }

    public DruzynaStatystykiPK(int idDruzyna, int idSezon) {
        this.idDruzyna = idDruzyna;
        this.idSezon = idSezon;
    }

    public int getIdDruzyna() {
        return idDruzyna;
    }

    public void setIdDruzyna(int idDruzyna) {
        this.idDruzyna = idDruzyna;
    }

    public int getIdSezon() {
        return idSezon;
    }

    public void setIdSezon(int idSezon) {
        this.idSezon = idSezon;
    }
    
}
